package com.accenture.dcsc.adp.portal.api.tracking.config.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class OAuth2JwtAccessTokenConfigurer implements JwtAccessTokenConverterConfigurer {

    @Autowired
    private DefaultUserAuthenticationConverter customTokenConverter;

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(customTokenConverter);
        converter.setAccessTokenConverter(tokenConverter);
    }
}
