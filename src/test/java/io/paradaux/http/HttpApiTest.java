package io.paradaux.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class HttpApiTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpApiTest.class);
    private static HttpApi api;

    @BeforeAll
    public static void setup() {
        api = new HttpApi(logger);
    }

    @Test
    void JsonRequestTest() {
        // TODO
    }

    @Test
    void plainRequestTest() throws IOException {
        HttpRequest request = api.plainRequest("https://paste.csfriendlycorner.com/aTEXY3P");
        HttpResponse<String> response = api.sendSync(request, HttpResponse.BodyHandlers.ofString());

        if (response == null) {
            throw new IOException();
        }

        assertEquals("HELLO WORLD", response.body());
    }

    @Test
    void postBytesTest() {
        // TODO
    }

    @Test
    void sendSyncTest() throws IOException {
        HttpRequest request = api.plainRequest("https://paste.csfriendlycorner.com/aTEXY3P");
        HttpResponse<String> response = api.sendSync(request, HttpResponse.BodyHandlers.ofString());

        if (response == null) {
            throw new IOException();
        }

        assertEquals("HELLO WORLD", response.body());
    }

}