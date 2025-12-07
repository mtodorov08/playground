package com.example.landonhotel.landonhotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class TwitterStreamingService
{
    private static final String STREAM_URL = "https://api.twitter.com/2/tweets/search/stream";

    @Value("${twitter.bearer.token}")
    private String bearerToken;

    @Autowired
    private WebClient.Builder builder;

    /**
     * Using PostMan create a stream rule to filter tweets to only those that mention "Landon Hotel". Needs to be done once.
     * @return
     */
    public Flux<String> stream()
    {
        WebClient webClient = builder.baseUrl(STREAM_URL)
                                     .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                                     .build();

        return webClient.get()
                        .retrieve()
                        .bodyToFlux(String.class)
                        .filter(body -> !body.isBlank());
    }
}



