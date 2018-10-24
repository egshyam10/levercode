package com.accenture.dcsc.adp.portal.api.tracking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.hateoas.Link;
import org.springframework.util.StringUtils;

//TODO - raise a ticket with spring about the Link class not being able to parse a link in string form when it has a namespace
public class LinkUtils {

    private static final String URI_PATTERN = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static Link parse(String element) {

        if (!StringUtils.hasText(element)) {
            return null;
        }

        Pattern uriAndAttributes = Pattern.compile("<(.*)>;(.*)");
        Matcher matcher = uriAndAttributes.matcher(element);

        if (matcher.find()) {

            Map<String, String> attributes = getAttributeMap(matcher.group(2));

            if (!attributes.containsKey("rel")) {
                throw new IllegalArgumentException("Link does not provide a rel attribute!");
            }

            return new Link(matcher.group(1), attributes.get("rel"));

        } else {
            throw new IllegalArgumentException(String.format("Given link header %s is not RFC5988 compliant!", element));
        }
    }

    private static Map<String, String> getAttributeMap(String source) {

        if (!StringUtils.hasText(source)) {
            return Collections.emptyMap();
        }

        Map<String, String> attributes = new HashMap<String, String>();
        Pattern keyAndValue = Pattern.compile("(\\w+)=\"(\\p{Lower}[\\p{Lower}\\p{Digit}\\.\\-\\:\\s]*|" + URI_PATTERN + ")\"");
        Matcher matcher = keyAndValue.matcher(source);

        while (matcher.find()) {
            attributes.put(matcher.group(1), matcher.group(2));
        }

        return attributes;
    }
}
