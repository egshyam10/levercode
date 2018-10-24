package com.accenture.dcsc.adp.portal.api.tracking.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class OAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Value("${rest.base-path}")
    private String baseUrl;

    @Value("#{'${security.oauth2.resource.scopes.required}'.split(',')}")
    private List<String> scopeRestrictions;

    @Autowired
    private OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        
		http
			.csrf().disable()
		
			.authorizeRequests()
		
			.antMatchers("/**").access(getOAuth2ScopeExpression(this.scopeRestrictions))

			// Explicitly expose health endpoint to the world.
			.antMatchers("/health").permitAll()
			
			.and().headers().cacheControl();

			http.headers().frameOptions().sameOrigin();
			
			http.headers().contentSecurityPolicy("script-src 'self' 'unsafe-eval' 'unsafe-inline';");
			
			http.exceptionHandling().authenticationEntryPoint(oAuth2AuthenticationEntryPoint);
		// @formatter:on
    }

    /**
     * @param scopes
     *            scope restrictions.
     * @return a string representation of the OAuth2 scope expression.
     */
    private static String getOAuth2ScopeExpression(List<String> scopes) {

        StringBuilder expression = new StringBuilder();

        for (String scope : scopes) {

            if ("".equals(expression.toString())) {
                expression.append("#oauth2.hasScope('" + scope + "')");
            } else {
                expression.append(" and #oauth2.hasScope('" + scope + "')");
            }
        }
        return expression.toString();
    }

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.resourceId(resourceId);
        resources.stateless(Boolean.TRUE);
        resources.eventPublisher(new DefaultAuthenticationEventPublisher(eventPublisher));
    }
}