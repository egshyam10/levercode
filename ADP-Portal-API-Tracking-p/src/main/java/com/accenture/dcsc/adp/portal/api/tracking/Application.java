
package com.accenture.dcsc.adp.portal.api.tracking;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application {

    public static final String BASE_PATH = "/api/v1";

    public static final String CURIE_NAMESPACE = "adp";

    private final Environment env;

    public Application(Environment env) {
        this.env = env;
    }

    @SuppressWarnings("squid:S2095")
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void information() {
        log.info("Base Path: {}", env.getProperty("rest.base-path"));
    }

    @Bean
    public CurieProvider curieProvider() {
        return new DefaultCurieProvider(CURIE_NAMESPACE, new UriTemplate("/docs/index.html#resources-{rel}"));
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return null;
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
