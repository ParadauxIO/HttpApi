/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.http.HttpApi :  31/01/2021, 05:15
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.http;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * HttpApi is a Java 11 java.net.http wrapper for dealing with RESTful APIs with relative ease.
 *
 * @author Rían Errity
 * @since 21-01-2021
 * @see HttpClient
 * */
public class HttpApi {

    private static final HttpClient.Version DEFAULT_HTTP_VERSION = HttpClient.Version.HTTP_2;
    private static final HttpClient.Redirect DEFAULT_REDIRECT_POLICY = HttpClient.Redirect.NORMAL;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(1);
    private static final String[] JSON_HEADER = {"Content-Type", "application/json", "Accept", "application/json"};
    private static final String[] DEFAULT_USERAGENT = {"User-Agent", "Paradaux/FriendlyBot"};
    private static final String[] PLAINTEXT_HEADER = {"Accept", "text/plain"};
    private static final String[] LANGUAGE_HEADER = {"Accept-Language", "en-US,en;q=0.8"};

    private final HttpClient client;
    private final Logger logger;

    public HttpApi() {
        this(LoggerFactory.getLogger(HttpApi.class));
    }

    // CONSTRUCTORS
    
    /**
     * Create an HTTP Api Instance provided the client.
     * @param client a client to override the default generated one with.
     * */
    public HttpApi(HttpClient client) {
        this.logger = LoggerFactory.getLogger(HttpApi.class);
        this.client = client;
    }

    /**
     * Create an HTTP Api Instance provided the client and a logger.
     * @param client a client to ovveride the default generated one with.
     * @param logger An instance of an SLF4J logger
     * */
    public HttpApi(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    /**
     * Create an HTTP Api Instance provided a logger.
     * This is the preferred constructor.
     * @param logger An instance of an SLF4J logger
     * */
    public HttpApi(Logger logger) {
        this.logger = logger;

        client = HttpClient.newBuilder()
                .version(DEFAULT_HTTP_VERSION)
                .followRedirects(DEFAULT_REDIRECT_POLICY)
                .build();

    }

    // JSON REQUESTS
    
    /**
     * Creates a default JSON request from a String URL
     * @param URL the url to send the request to.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest jsonRequest(String URL) {
        return jsonRequest(URI.create(URL), null);
    }

    /**
     * Creates a default JSON request from a String URL and any additional, non-default headers.
     * @param URL the url to send the request to.
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest jsonRequest(String URL, String[] additionalHeaders) {
        return jsonRequest(URI.create(URL), additionalHeaders);
    }

    /**
     * Creates a default JSON request from a URI and any additional, non-default headers.
     * @param uri the URI to send the request to
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest jsonRequest(URI uri, String[] additionalHeaders) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(JSON_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .GET();

        if (additionalHeaders != null) {
            builder.headers(additionalHeaders);
        }

        return builder.build();
    }

    // PLAIN REQUESTS
    
    /**
     * Creates a default plain-text request from a String URL
     * @param URL the URI to send the request to.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest plainRequest(String URL) {
        return plainRequest(URI.create(URL), null);
    }

    /**
     * Creates a default plain-text request from a String URL and any additional, non-default headers.
     * @param URL the URL to send the request to
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest plainRequest(String URL, String[] additionalHeaders) {
        return plainRequest(URI.create(URL), additionalHeaders);
    }

    /**
     * Creates a default plain-text request from a URI and any additional, non-default headers.
     * @param uri the URI to send the request to
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @NonNull
    public HttpRequest plainRequest(URI uri, String[] additionalHeaders) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(PLAINTEXT_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .GET();

        if (additionalHeaders != null) {
            builder.headers(additionalHeaders);
        }

        return builder.build();
    }

    // POST REQUESTS (NO BODY)

    /**
     * Posts to the given URL, returns the response.
     * @param URL the URL to send the request to
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest postPlain(String URL) {
        return postPlain(URI.create(URL), null);
    }

    /**
     * Posts to the given URL and any additional, non-default headers, returns the response.
     * @param URL the URL to send the request to
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest postPlain(String URL, String[] additionalHeaders) {
        return postPlain(URI.create(URL), additionalHeaders);
    }

    /**
     * Posts to the given URI and any additional, non-default headers, returns the response.
     * @param uri The URI to send the request to
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest postPlain(URI uri, String[] additionalHeaders) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(PLAINTEXT_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .POST(HttpRequest.BodyPublishers.noBody());

        if (additionalHeaders != null) {
            builder.headers(additionalHeaders);
        }

        return builder.build();
    }

    // POST REQUESTS (BINARY)

    /**
     * Posts binary to the given URL, returns the response.
     * @param URL the URL to send the request to
     * @param data A binary array containing the data you wish to POST
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest postBytes(String URL, byte[] data) {
        return postBytes(URI.create(URL), data, null);
    }

    /**
     * Posts binary to the given URL and any additional, non-default headers, returns the response.
     * @param URL the URL to send the request to
     * @param data A binary array containing the data you wish to POST
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest postBytes(String URL, byte[] data, String[] additionalHeaders) {
        return postBytes(URI.create(URL), data, additionalHeaders);
    }

    /**
     * Posts binary to the given URI and any additional, non-default headers, returns the response.
     * @param uri the URL to send the request to
     * @param data A binary array containing the data you wish to POST
     * @param additionalHeaders Any additional headers you would like attached.
     * @return The HTTP Request.
     * */
    @Nullable
    public HttpRequest postBytes(URI uri, byte[] data, String[] additionalHeaders) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(PLAINTEXT_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .POST(HttpRequest.BodyPublishers.ofByteArray(data));

        if (additionalHeaders != null) {
            builder.headers(additionalHeaders);
        }

        return builder.build();
    }

    /**
     * Sends the specified HTTP request synchronously. (THIS IS THREAD-BLOCKING!)
     * Only use this when concurrency is being upheld elsewhere, and is not needed at the httpclient level.
     * @param request The HTTP request you wish to send
     * @param handler The {@link java.net.http.HttpResponse.BodyHandler} pertaining the the response datatype.
     * @param <T> Type of Response.
     * @return The HTTP response from the webserver.
     * */
    @Nullable
    public <T> HttpResponse<T> sendSync(HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        try {
            return client.send(request, handler);
        } catch (IOException | InterruptedException | IllegalArgumentException | SecurityException e) {
            logger.error("An error occurred while interacting with {}. Exception: ", request.uri().toString(), e);
            return null;
        }
    }

    /**
     * Sends the specified HTTP request asynchronously. (THIS RETURNS A COMPLETABLE FUTURE!)
     * @param request The HTTP request you wish to send
     * @param handler The {@link java.net.http.HttpResponse.BodyHandler} pertaining the the response datatype.
     * @param <T> Type of Response.
     * @return A completable future containing the HTTP Response once it has been received.
     * */
    @Nullable
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        try {
            return client.sendAsync(request, handler);
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred while interacting with " + request.uri().toString() + " Exception: ", e);
            return null;
        }

    }

    /**
     * For idiots who try and execute this jar directly.
     * @param args Unused.
     * */
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(new JPanel(), "This is an API. You cannot execute this JAR directly.", "Illegal Action",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Converts a String, String Map into a url-encoded form string.
     * @param map A Key:Value pairing for all the encoded parameters you wish to produce.
     * @return A url-encoded string.
     * */
    @NonNull
    public static String mapToUrlEncodedParameters(Map<String, String> map) {
        return map.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(map.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}
