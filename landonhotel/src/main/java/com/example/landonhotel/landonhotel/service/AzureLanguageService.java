package com.example.landonhotel.landonhotel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.AnalyzeSentimentOptions;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.KeyPhrasesCollection;
import com.azure.core.credential.AzureKeyCredential;

@Service
public class AzureLanguageService
{
    private String azureEndpoint = "https://landon-hotel-feedback-mm.cognitiveservices.azure.com/";

    private TextAnalyticsClient client;

    public AzureLanguageService(@Value("${azure.api.key}") String azureApiKey)
    {
        client = authenticateClient(azureApiKey, azureEndpoint);
    }

    private static TextAnalyticsClient authenticateClient(String key, String endpoint)
    {
        return new TextAnalyticsClientBuilder().credential(new AzureKeyCredential(key))
                                               .endpoint(endpoint)
                                               .buildClient();
    }

    public DocumentSentiment analyzeSentiment(String document)
    {
        AnalyzeSentimentOptions options = new AnalyzeSentimentOptions().setIncludeOpinionMining(true);
        return client.analyzeSentiment(document, "en", options);
    }


    public KeyPhrasesCollection extractKeyPhrases(String document)
    {
        return client.extractKeyPhrases(document);
    }
}



