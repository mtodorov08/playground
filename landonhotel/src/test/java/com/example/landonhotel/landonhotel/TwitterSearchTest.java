/*
 * TwitterSearchTest.java
 *
 * created at 2025-12-06 by m.todorov <m.todorov@seeburger.com>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.example.landonhotel.landonhotel;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;


@SpringBootTest
public class TwitterSearchTest
{
    private static final String SEARCH_URL = "https://api.twitter.com/2/tweets/search/recent";
    private static final String STREAM_URL = "https://api.twitter.com/2/tweets/search/stream";
    private static final String RULES_PATH = "/rules";

    @Value("${twitter.bearer.token}")
    private String bearerToken;

    @Autowired
    private WebClient.Builder builder;


    @Test
    void restTemplateTest()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + bearerToken);

        String uri = UriComponentsBuilder.fromUriString(SEARCH_URL)
                                         .queryParam("query", "LinkedIn Learning")
                                         .build()
                                         .toUriString();

        HttpEntity< ? > entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        assertEquals(200, response.getStatusCode());
    }


    @Test
    void webClientTest() throws InterruptedException
    {
        WebClient client = WebClient.create(SEARCH_URL);
        Mono<ResponseEntity<String>> response = client.get()
                                                  .uri(uriBuilder -> uriBuilder.queryParam("query", "LinkedIn Learning")
                                                                               .build())
                                                  .header("Authorization", "Bearer " + bearerToken)
                                                  .retrieve()
                                                  .toEntity(String.class);

        response.subscribe(responseEntity ->
        {
            System.out.println(responseEntity.getBody());
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        });
        Thread.sleep(3000); // Wait for async call to complete
    }

    @Test
    void webClientStreamTest() throws IOException
    {
        WebClient client = builder
                        .baseUrl(STREAM_URL)
                        .defaultHeaders(headers -> headers.setBearerAuth(bearerToken))
                        .build();

        StreamRuleRequest ruleRequest = new StreamRuleRequest();
        ruleRequest.addRule("Landon Hotel", "Landon Hotel Tag");

        client.post()
                .uri(RULES_PATH)
                .bodyValue(ruleRequest)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response ->
                {
                    client.get()
//                        .uri(RULES_PATH)
                        .retrieve()
                        .bodyToFlux(String.class)
                        .filter(body -> !body.isBlank())
                        .subscribe(json ->
                        {
                                System.out.println(json);
                        });
                });

        System.in.read();
    }

    class StreamRuleRequest
    {
        private List<StreamRule> add = new ArrayList<>();

        public List<StreamRule> getAdd()
        {
            return add;
        }

        public void setAdd(List<StreamRule> add)
        {
            this.add = add;
        }

        public void addRule(String value, String tag)
        {
            this.add.add(new StreamRule(value, tag));
        }
    }

    class StreamRule
    {
        private String value;
        private String tag;

        public StreamRule(String value, String tag)
        {
            this.value = value;
            this.tag = tag;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        public String getTag()
        {
            return tag;
        }

        public void setTag(String tag)
        {
            this.tag = tag;
        }
    }
}
