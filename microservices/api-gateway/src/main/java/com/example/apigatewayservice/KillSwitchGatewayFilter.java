package com.example.apigatewayservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.getunleash.Unleash;


@Component
public class KillSwitchGatewayFilter
    extends AbstractGatewayFilterFactory<KillSwitchGatewayFilterConfig>
{
    private static final Logger LOG = LoggerFactory.getLogger(KillSwitchGatewayFilter.class);
    private final Unleash unleash;

    public KillSwitchGatewayFilter(Unleash unleash)
    {
        super(KillSwitchGatewayFilterConfig.class);
        this.unleash = unleash;
    }


    @Override
    public GatewayFilter apply(KillSwitchGatewayFilterConfig config)
    {
        {
            return (exchange, chain) ->
            {
                LOG.info("KillSwitchGatewayFilter checking feature flag '{}'", config.getFlagName());

                if (!unleash.isEnabled(config.getFlagName()))
                {
                    LOG.info("BookService feature flag '{}' is disabled", config.getFlagName());
                    exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                    return exchange.getResponse().setComplete();
                }
                LOG.info("BookService feature flag '{}' is enabled", config.getFlagName());
                return chain.filter(exchange);
            };
        }
    }
}
