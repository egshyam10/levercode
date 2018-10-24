package com.accenture.dcsc.adp.portal.api.tracking.domain.users;

import java.util.HashSet;
import java.util.Set;

import com.accenture.dcsc.adp.portal.api.tracking.domain.core.EmailAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = false)
public class User {

    public static final String GLOBAL_ADMIN = "ROLE_GLOBAL_ADMIN";

    public static final String REGULAR_USER = "ROLE_REGULAR_USER";
    
    public static final String GLOBAL_READONLY_ADMIN = "ROLE_GLOBAL_READONLY_ADMIN";

    @JsonIgnore
    private String peoplekey;

    private String username;

    @JsonUnwrapped
    private EmailAddress email;

    @JsonIgnore
    private String givenName;

    @JsonIgnore
    private String surname;

    private String avatar;

    private Set<String> groups = new HashSet<>();

    public boolean hasGroup(String groupName) {
        return this.groups.contains(groupName);
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return this.givenName + " " + this.surname;
    }

    public boolean hasOneOfGroups(Set<String> groups) {

        for (String group : groups) {
            if (this.groups.contains(group)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return a string representation of a URL to the user's avatar.
     */
    public String getAvatar() {

        if (this.avatar == null) {
            return null;
        }

        if (this.peoplekey == null) {
            return null;
        }

        if (this.avatar.contains("<peoplekey>")) {
            this.avatar = this.avatar.replaceAll("<peoplekey>", this.peoplekey);
        }

        return avatar;
    }
}
