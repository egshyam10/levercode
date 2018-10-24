package com.accenture.dcsc.adp.portal.api.tracking;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.accenture.dcsc.adp.portal.api.tracking.Application;

@Configuration
@Import({ Application.class })
public class TestApplication {

}