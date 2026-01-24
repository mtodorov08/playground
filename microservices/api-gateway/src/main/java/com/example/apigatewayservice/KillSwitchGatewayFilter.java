package com.example.apigatewayservice;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.getunleash.Unleash;

@Component
public class KillSwitchGatewayFilter
    extends AbstractGatewayFilterFactory<KillSwitchGatewayFilterConfig>
{

    private final Unleash unleash;

    public KillSwitchGatewayFilter(Unleash unleash)
    {
        this.unleash = unleash;
    }


    @Override
    public GatewayFilter apply(KillSwitchGatewayFilterConfig config)
    {
        return (exchange, chain) ->
        {
            if (!unleash.isEnabled(config.getFlagName()))
            {
                exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }
}
