# HTTP API
![Nexus](https://img.shields.io/nexus/r/io.paradaux.http/HttpApi?color=66b3b3&label=api-version&nexusVersion=3&server=https%3A%2F%2Frepo.paradaux.io)
![Discord](https://img.shields.io/discord/583254829279739905?label=Support%20Discord%21)
![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.paradaux.io%2Fjob%2FHttpApi%2F&label=jenkins%20build)

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
        <url>https://repo.paradaux.io/repository/maven-releases/</url>
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

# License
HttpApi © Rían Errity (Paradaux) 2021
HttpApi is licensed under the MIT License, go wild. 
