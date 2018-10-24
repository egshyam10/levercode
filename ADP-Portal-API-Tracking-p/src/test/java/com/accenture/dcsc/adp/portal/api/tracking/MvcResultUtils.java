package com.accenture.dcsc.adp.portal.api.tracking;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

public class MvcResultUtils {

    public static List<Link> getLinkHeaders(MvcResult result) {
        return getHeaderList(result, HttpHeaders.LINK).stream().map(LinkUtils::parse).collect(Collectors.toList());
    }

    public static List<HttpMethod> getAllowHeaders(MvcResult result) {
        return getHeaderList(result, HttpHeaders.ALLOW).stream().map(HttpMethod::resolve).collect(Collectors.toList());
    }

    public static List<Object> getAcceptPatchHeaders(MvcResult result) {
        return result.getResponse().getHeaderValues("Accept-Patch");
    }

    public static String getLocationHeader(MvcResult result) {
        return result.getResponse().getHeader(HttpHeaders.LOCATION);
    }

    private static Set<String> getHeaderList(MvcResult result, String header) {
        return StringUtils.commaDelimitedListToSet(result.getResponse().getHeader(header));
    }
}
