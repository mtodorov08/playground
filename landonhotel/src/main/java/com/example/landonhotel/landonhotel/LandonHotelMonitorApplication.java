package com.example.landonhotel.landonhotel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.TextSentiment;
import com.example.landonhotel.landonhotel.client.TwilioClient;
import com.example.landonhotel.landonhotel.service.AzureLanguageService;
import com.example.landonhotel.landonhotel.service.TwitterStreamingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;


@Profile("!test")
@SpringBootApplication
public class LandonHotelMonitorApplication implements CommandLineRunner
{
    @Autowired
    private TwitterStreamingService twitterStreamingService;
    @Autowired
    private AzureLanguageService azureLanguageService;

    @Value("${twilio.account.sid}")
    private String twilioSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.to.phone.number}")
    private String toPhoneNumber;

    private String fromPhoneNumber = "+16203373603";

    private static final String TWILIIO_API_DOMAIN = "https://api.twilio.com";

    public static void main(String[] args)
    {
        SpringApplication.run(LandonHotelMonitorApplication.class, args);
    }


    @Bean
    public ObjectMapper mapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }


    @Bean
    public TwilioClient twilioClient()
    {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);
        return Feign.builder()
                    .requestInterceptor(interceptor)
                    .encoder(new FormEncoder())
                    .target(TwilioClient.class, TWILIIO_API_DOMAIN);
    }


    @Override
    public void run(String... args)
        throws Exception
    {
        twitterStreamingService.stream()
                               .subscribe(tweet ->
                               {
                                   System.out.println("Received tweet: " + tweet);
                                   try
                                   {
                                       String text = mapper().readTree(tweet).at("/data/text").asText();
                                       DocumentSentiment sentiment = azureLanguageService.analyzeSentiment(text);
                                       String message = sentiment.getSentiment().equals(TextSentiment.POSITIVE)
                                                                                            ? "Positive tweet about Landon Hotel: " + text
                                                                                            : "Negative tweet about Landon Hotel: " + text;

                                       if (sentiment.getSentiment().equals(TextSentiment.NEGATIVE))
                                       {
                                           twilioClient().sendSmsMessage(twilioSid, toPhoneNumber, fromPhoneNumber, message);
                                       }
                                   }
                                   catch (JsonProcessingException e)
                                   {
                                       throw new RuntimeException(e);
                                   }
                               });
    }

}
