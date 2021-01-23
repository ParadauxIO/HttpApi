package io.paradaux.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

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

    /**
     * Create an HTTP Api Instance provided the client.
     * */
    public HttpApi(HttpClient client) {
        this.logger = LoggerFactory.getLogger(HttpApi.class);
        this.client = client;
    }

    /**
     * Create an HTTP Api Instance provided the client and a logger.
     * */
    public HttpApi(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    /**
     * Create an HTTP Api Instance provided a logger.
     * This is the preferred constructor.
     * */
    public HttpApi(Logger logger) {
        this.logger = logger;

        client = HttpClient.newBuilder()
                .version(DEFAULT_HTTP_VERSION)
                .followRedirects(DEFAULT_REDIRECT_POLICY)
                .build();
    }

    /**
     * Creates a default JSON request from a String URL
     * */
    @CheckReturnValue
    @Nullable
    public HttpRequest jsonRequest(String url) {
        return jsonRequest(URI.create(url));
    }

    /**
     * Creates a default JSON request from a URI
     * */
    @CheckReturnValue
    @Nullable
    public HttpRequest jsonRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(JSON_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .GET()
                .build();
    }

    /**
     * Creates a default plain-text request from a String URL
     * */
    @CheckReturnValue
    @Nullable
    public HttpRequest plainRequest(String url) {
        return plainRequest(URI.create(url));
    }

    /**
     * Creates a default plain-text request from a URI
     * */
    @CheckReturnValue
    @Nonnull
    public HttpRequest plainRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(PLAINTEXT_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .GET()
                .build();
    }

    @CheckReturnValue
    @Nullable
    public HttpRequest postBytes(String url, byte[] data) {
        return postBytes(URI.create(url), data);
    }

    @CheckReturnValue
    @Nullable
    public HttpRequest postBytes(URI uri, byte[] data) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .timeout(DEFAULT_TIMEOUT)
                .headers(PLAINTEXT_HEADER)
                .headers(DEFAULT_USERAGENT)
                .headers(LANGUAGE_HEADER)
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .build();
    }

    /**
     * Sends the specified HTTP request synchronously. (THIS IS THREAD-BLOCKING!)
     * Only use this when concurrency is being upheld elsewhere, and is not needed at the httpclient level.
     * @param request The HTTP request you wish to send
     * @param handler The {@link java.net.http.HttpResponse.BodyHandler} pertaining the the response datatype.
     * @return The HTTP response from the webserver.
     * */
    @CheckReturnValue
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
     * @return A completable future containing the HTTP Response once it has been received.
     * */
    @CheckReturnValue
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
     * */
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(new JPanel(), "This is an API. You cannot execute this JAR directly.", "Illegal Action",
                JOptionPane.WARNING_MESSAGE);
    }



}
