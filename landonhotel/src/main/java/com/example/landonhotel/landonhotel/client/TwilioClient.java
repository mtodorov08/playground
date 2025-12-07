package com.example.landonhotel.landonhotel.client;


import feign.Headers;
import feign.Param;
import feign.RequestLine;


public interface TwilioClient
{
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Calls.json")
    public void sendVoiceMessage(@Param("AccountSid") String accountSid,
                                 @Param("To") String toPhoneNumber,
                                 @Param("From") String fromPhoneNumber,
                                 @Param("Twiml") String twiml);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSid}/Messages.json")
    public void sendSmsMessage(@Param("AccountSid") String accountSid,
                                 @Param("To") String toPhoneNumber,
                                 @Param("From") String fromPhoneNumber,
                                 @Param("Body") String body);
}
