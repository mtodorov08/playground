package com.example.apigatewayservice;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.getunleash.Unleash;

@Component
public class KillSwitchGatewayFilter
    extends AbstractGatewayFilterFactory<KillSwitchGatewayFilter.Config>
{

    private final Unleash unleash;

    public KillSwitchGatewayFilter(Unleash unleash)
    {
        this.unleash = unleash;
    }


    @Override
    public GatewayFilter apply(Config config)
    {
        return (exchange, chain) ->
        {
            if (!unleash.isEnabled(config.flagName))
            {
                exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config
    {
        private String flagName;

        public String getFlagName() {
            return flagName;
        }

        public void setFlagName(String flagName) {
            this.flagName = flagName;
        }
    }
}
