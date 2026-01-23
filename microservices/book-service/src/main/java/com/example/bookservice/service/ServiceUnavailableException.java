package com.example.bookservice.service;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException
{
    private static final long serialVersionUID = -4449007976712423550L;

    public ServiceUnavailableException(String msg)
    {
        super(msg);
    }
}
