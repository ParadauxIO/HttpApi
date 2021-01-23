package io.paradaux.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    void plainRequestTest() {
        // TODO
    }

    @Test
    void postBytesTest() {
        // TODO
    }

    @Test
    void sendSyncTest() {
        // TODO
    }

    @Test
    void sendAsyncTest() {
        // TODO
    }

}