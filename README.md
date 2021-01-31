# HTTP API
[![Nexus](https://img.shields.io/badge/api--version-1.1.1-66b3b3)](https://repo.paradaux.io/releases/io/paradaux/http/HttpApi/1.1.1)
[![Discord](https://img.shields.io/discord/583254829279739905?label=Support%20Discord%21)](https://paradaux.io/discord)
[![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.paradaux.io%2Fjob%2FHttpApi%2F&label=jenkins%20build)](https://ci.paradaux.io/job/HttpApi/)

## What is HttpApi?

HTTP Api is a simple `java.net.http` wrapper for performing simple get/post requests, using templated HttpRequest objects. Created for use in ParadauxIo/FriendlyBot and ParadauxIo/HiberniaDiscord 

## Currently supported (templated) actions 
- JSON GET
- PLAIN GET
- BYTE PUSH

## Maven

HttpApi isn't currently available in central, however you can find it in my repository. 
```xml
<repositories>
    <repository>
        <id>paradaux</id>
        <url>https://repo.paradaux.io/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>io.paradaux.http</groupId>
        <artifactId>HttpApi</artifactId>
        <version>%VERSION% REPLACE THIS WITH THE NUMBER IN THE FIRST BADGE</version>
    </dependency>
</dependencies>
```

## Usage

This API is written to be used for performing basic, ritualistic HTTP Requests, intended for use within Discord bots or 
Bukkit Plugins. As such, the large majority of the API is focussed on HTTP `GET`. More features will be added over time as needed, and as requested. 

### Getting an instance of the API

The API is designed to be a singleton, as such you need to create an instance of the api, which you can do one of several ways.

#### Providing a logger

The API expects a logger, if there isn't one provided it will create its own internally for debugging purposes.

```java
HttpApi api = new Logger(); // Logger created internally


Logger logger = LoggerFactory.getLogger(getClass());
HttpApi api = new HttpApi(logger) // Logger provided
```

#### Providing a HttpClient

If you don't like the default settings of the HttpClient you are to provide your own by passing it in to the constructor.

```java
HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_2)
        .followRedirects(Redirect.SAME_PROTOCOL)
        .build();

HttpApi api = new HttpApi(client);
```

There are also constructors for other variations of those two parameters, 
i.e providing both a client and a logger, or no parameters.


### Creating a simple HTTP Get request

In order to `GET` you have to create a plainRequest

```java
HttpApi api = new HttpApi(logger);

HttpRequest request = api.plainRequest("https://paste.csfriendlycorner.com/aTEXY3P");
```

HttpRequests are immutable, meaning they cannot be modified once they are built. You can build requests outside of the API and send them internally, if you so which, however that largely defeats the purpose of the API -- to give templated HTTP Requests

### Sending Requests

You have two options for sending requests, asynchronously and synchronously. These options are provided by the HttpApi#sendSync(HttpRequest, BodyHandler) and HttpApi#sendASync(HttpRequest, BodyHandler) the latter of which returns a Completeable Future containing the HttpResponse. 

```java
// Create the instance
HttpApi api = new HttpApi(); 

// Create the request
HttpRequest request = api.plainRequest("https://paste.csfriendlycorner.com/aTEXY3P");

// Send the request, and get the response (synchronously)
HttpResponse<String> response = api.sendSync(request, HttpResponse.BodyHandlers.ofString());

// Do something with the response body. 
logger.info(response.body());
```

// TODO: Concurrency, JSON tutorials. 

## License
HttpApi © Rían Errity (Paradaux) 2021
HttpApi is licensed under the MIT License, go wild. 
