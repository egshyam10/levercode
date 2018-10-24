package com.accenture.dcsc.adp.portal.api.tracking.domain.core;

import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Value;

/*
 * See https://github.com/spring-projects/spring-gemfire-examples/blob/master/spring-gemfire-examples-common/src/main/java/org/springframework/data/gemfire/examples/domain/EmailAddress.java
 */
@Value
public final class EmailAddress {

    public static final String EMAIL_ADDRESS_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9\\-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX);

    private final String value;

    private EmailAddress(String value) {
        Assert.isTrue(isValid(value), "value is not valid.");

        this.value = value;
    }
    
    /**
     * @return a string representation of the username portion of the email address.
     */
    @JsonIgnore
    public String getUsername() {        
        
        int usernameEndIndex = this.value.indexOf('@');
        
        // This should never be true assuming the class has been constructed correctly.
        if(usernameEndIndex == -1)
            throw new IllegalArgumentException("Email address is invalid.");
        
        return value.substring(0, usernameEndIndex);
    }

    @JsonCreator
    public static EmailAddress valueOf(String value) {
        return new EmailAddress(value);
    }

    public static boolean isValid(String value) {
        Assert.hasText(value, "value cannot be null or empty");

        return PATTERN.matcher(value).matches();
    }

    @JsonValue
    public String getValue() {
        return value.toLowerCase();
    }

    @Override
    public String toString() {
        return value;
    }

    //frontend
    public static class EmailAddressDeserializer extends JsonDeserializer<EmailAddress> {

        @Override
        public EmailAddress deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            return EmailAddress.valueOf(node.asText());
        }
    }

    //backend
    @WritingConverter
    public static class EmailAddressToStringConverter implements Converter<EmailAddress, String> {

        @Override
        public String convert(EmailAddress source) {
            return source == null ? null : source.value;
        }
    }
}