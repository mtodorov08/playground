package com.example.landonhotel.landonhotel.client;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;


@ActiveProfiles("test")
@SpringBootTest
class TwilioClientTest
{
    @Value("${twilio.account.sid}")
    private String twilioSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.to.phone.number}")
    private String toPhoneNumber;

    private String fromPhoneNumber = "+16203373603";

    private static final String TWILIIO_API_DOMAIN = "https://api.twilio.com";

    @Test
    void testSendVoiceMessage()
    {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);
        TwilioClient client = Feign.builder()
                                   .requestInterceptor(interceptor)
                                   .encoder(new FormEncoder())
                                   .target(TwilioClient.class, TWILIIO_API_DOMAIN);

        client.sendVoiceMessage(twilioSid, toPhoneNumber, fromPhoneNumber,
                                "<Response><Say>popara</Say></Response>");
    }

    @Test
    void testSendSmsMessage()
    {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twilioAuthToken);
        TwilioClient client = Feign.builder()
                                   .requestInterceptor(interceptor)
                                   .encoder(new FormEncoder())
                                   .target(TwilioClient.class, TWILIIO_API_DOMAIN);

        client.sendSmsMessage(twilioSid, toPhoneNumber, fromPhoneNumber,
                                "This is a test message from Landon Hotel application.");
    }

}
