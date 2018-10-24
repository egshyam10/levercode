package com.accenture.dcsc.adp.portal.api.tracking;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.net.URI;
import java.util.Arrays;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs("target/generated-snippets")
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application.yml")
public abstract class AbstractFunctionalTest {

    protected static final String HTTP_JSON_SCHEMA_ORG_DRAFT_04_SCHEMA = "http://json-schema.org/draft-04/schema#";
    protected static final String HTTP_adp_EXTENDED_DRAFT_04_SCHEMA = "http://adp.accenture.com/extended/draft-04/schema#";
    protected static final String REL = "rel";

    @Value("${rest.base-path}")
    protected String basePath;

    @Autowired
    private WebApplicationContext context;

    @LocalServerPort
    protected int port;

    protected MockMvc mvc;

    protected Traverson traverson;

    @Autowired
    protected ObjectMapper objectMapper;

    @Rule
    public final JUnitRestDocumentation documentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private Filter springSecurityFilterChain;
    
    @BeforeClass
    public static void beforeAllTests() {
        RestAssured.filters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()));
    }
    
    // @formatter:off

    @Before
    public void beforeEachTest() throws Exception {
        RestAssured.basePath = basePath;
        RestAssured.port = port;

        traverson = new Traverson(URI.create(String.format("http://localhost:%s%s", port, basePath)), MediaTypes.HAL_JSON);

        mvc = MockMvcBuilders.webAppContextSetup(context).
                addFilter(springSecurityFilterChain).//
                apply(springSecurity()).//
                apply(
                        documentationConfiguration(this.documentation).
                        uris().
                        withPort(port)
                        ).
                alwaysDo(print()).
                alwaysDo(
                        document("{method-name}/{step}/",
                                preprocessResponse(
                                        prettyPrint()
                                )
                        )
                ).
                build();
    }
    // @formatter:on

}