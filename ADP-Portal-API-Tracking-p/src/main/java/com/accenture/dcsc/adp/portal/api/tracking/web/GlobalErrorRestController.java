package com.accenture.dcsc.adp.portal.api.tracking.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Controller to handler the "/error" mapping.
 *
 * @author robert.a.northard
 */
@RestController
public class GlobalErrorRestController implements ErrorController {

    private static final String PATH = "error";

    private final ErrorAttributes errorAttributes;

    @Autowired
    public GlobalErrorRestController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "errorAttributes cannot be null.");

        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Map<String, Object> error(HttpServletRequest request, HttpServletResponse response) {

        return getErrorAttributes(request);
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {

        return errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), false);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}