/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.http.HttpApiTest :  31/01/2021, 05:14
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
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

    @Test
    void getAndPostToImgur() {
        HttpApi http = new HttpApi(logger);
        HttpRequest request = http.plainRequest("https://api.wolframalpha.com/v1/simple?appid=DEMO&i=What+airplanes+are+flying+overhead%3F");
        http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept((response) -> {
            byte[] bytes = response.body();
            System.out.println(bytes.length);
            try (FileOutputStream fos = new FileOutputStream("src/test/resources/img.png")) {
                fos.write(bytes);
            } catch (IOException exception) {
                logger.error("An error occurred.");
            }

            String[] headers = {"Authorization", "Client-ID "};
            HttpRequest request2 = http.postBytes("https://api.imgur.com/3/upload/", bytes, headers);

            HttpResponse<String> response2 = http.sendSync(request2, HttpResponse.BodyHandlers.ofString());
            System.out.println(response2.body());
        }).join();
    }
}