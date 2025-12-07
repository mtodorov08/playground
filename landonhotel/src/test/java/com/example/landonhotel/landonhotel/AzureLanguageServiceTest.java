package com.example.landonhotel.landonhotel;


import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.SentimentConfidenceScores;
import com.example.landonhotel.landonhotel.service.AzureLanguageService;


@SpringBootTest
public class AzureLanguageServiceTest
{
    @Value("${azure.api.key}")
    private String azureApiKey;

    @Autowired
    private AzureLanguageService azureLanguageService;

    @Test
    public void testSentimentAnalysis()
    {
        // The document that needs be analyzed.
        String document = "The food and service were unacceptable. The concierge was nice, however.";

        final DocumentSentiment documentSentiment = azureLanguageService.analyzeSentiment(document);

        assertEquals(com.azure.ai.textanalytics.models.TextSentiment.NEGATIVE, documentSentiment.getSentiment(),
                     "Expected overall sentiment to be NEGATIVE");

        SentimentConfidenceScores scores = documentSentiment.getConfidenceScores();
        double sum = scores.getPositive() + scores.getNeutral() + scores.getNegative();
        assertTrue(sum > 0.99 && sum < 1.01, "Confidence scores should sum to ~1, was: " + sum);
    }


    @Test
    public void testKeyPhraseExtraction()
    {
        // The text to be analyzed
        String text = "Dr. Smith has a very modern medical office, and she has great staff.";

        assertEquals("modern medical office, Dr. Smith, great staff", azureLanguageService.extractKeyPhrases(text).stream().collect(Collectors.joining(", ")),
                     "Key phrases do not match expected values.");
    }

}
