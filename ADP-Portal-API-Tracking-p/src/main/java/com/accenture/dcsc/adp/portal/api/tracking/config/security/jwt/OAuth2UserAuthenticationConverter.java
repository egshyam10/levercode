package com.accenture.dcsc.adp.portal.api.tracking.config.security.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import com.accenture.dcsc.adp.portal.api.tracking.domain.core.EmailAddress;
import com.accenture.dcsc.adp.portal.api.tracking.domain.users.User;
import com.google.common.collect.Sets;

@Component
public class OAuth2UserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    public static final SimpleGrantedAuthority REGULAR_USER_GRANTED_AUTHORITY = new SimpleGrantedAuthority(User.REGULAR_USER);

    public static final SimpleGrantedAuthority GLOBAL_ADMIN_GRANTED_AUTHORITY = new SimpleGrantedAuthority(User.GLOBAL_ADMIN);
    
    public static final SimpleGrantedAuthority GLOBAL_READONLY_ADMIN_GRANTED_AUTHORITY = new SimpleGrantedAuthority(User.GLOBAL_READONLY_ADMIN);

    @Value("#{'${security.oauth2.resource.claims.email}'.split(',')}")
    private String[] email;

    @Value("#{'${security.oauth2.resource.claims.givenName}'.split(',')}")
    private String[] givenName;

    @Value("#{'${security.oauth2.resource.claims.surname}'.split(',')}")
    private String[] surname;

    @Value("#{'${security.oauth2.resource.claims.username}'.split(',')}")
    private String[] username;

    @Value("#{'${security.oauth2.resource.claims.peoplekey}'.split(',')}")
    private String[] peoplekey;

    @Value("${security.oauth2.resource.claims.avatar}")
    private String avatar;

    @Value("${security.oauth2.resource.claims.group}")
    private String group;

    @Value("#{'${portal.permission.admin.groups:}'.split(',')}")
    private Set<String> globalAdminGroups;

    @Value("#{'${portal.permission.admin.users:}'.split(',')}")
    private Set<String> globalAdminUsers;
    
    @Value("#{'${portal.permission.admin.readOnly.groups:}'.split(',')}")
    private Set<String> globalReadonlyAdminGroups;
    
    @Value("#{'${portal.permission.admin.readOnly.users:}'.split(',')}")
    private Set<String> globalReadonlyAdminUsers;

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {

        User user = new User();

        user.setGivenName(getClaimMapping(map, givenName));
        final String emailClaim = getClaimMapping(map, email);
        user.setEmail(emailClaim == null ? null : EmailAddress.valueOf(emailClaim));
        user.setSurname(getClaimMapping(map, surname));
        user.setUsername(getClaimMapping(map, username));
        user.setPeoplekey(getClaimMapping(map, peoplekey));
        user.setAvatar(this.avatar);

        user.setGroups(new HashSet<>(this.getGroupClaim(map, group)));

        GrantedAuthority authority = REGULAR_USER_GRANTED_AUTHORITY;

        if (!Sets.intersection(user.getGroups(), globalAdminGroups).isEmpty() || globalAdminUsers.contains(user.getUsername())) {
            authority = GLOBAL_ADMIN_GRANTED_AUTHORITY;
        } else if (!Sets.intersection(user.getGroups(), globalReadonlyAdminGroups).isEmpty() || globalReadonlyAdminUsers.contains(user.getUsername())) {
            authority = GLOBAL_READONLY_ADMIN_GRANTED_AUTHORITY;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), "N/A", Arrays.asList(authority));

        authentication.setDetails(user);

        return authentication;
    }

    /**
     * Extract claim, with the associated key from the claim map.
     *
     * @param claimMap
     *            map of claims.
     * @param claimKey
     *            unique key for the claim to extract.
     * @return a string representation of the claim. Null is returned if the claim cannot be found in the map.
     */
    private String getClaimMapping(Map<String, ?> claimMap, String... claimKeys) {

        for (String claimKey : claimKeys) {
            Object claimName = claimMap.get(claimKey);

            if (claimName != null && claimName instanceof String) {
                return (String) claimName;
            }
        }

        return null;
    }

    /**
     * Extract group claims from key.
     *
     * @param claimMap
     *            map of claims
     * @param claimKey
     *            claim key
     * @return array of the users groups extracted from the claim map. If the user has no groups an empty list is returned.
     */
    @SuppressWarnings("unchecked")
    private List<String> getGroupClaim(Map<String, ?> claimMap, String claimKey) {

        List<String> groups = new ArrayList<>();

        Object claimName = claimMap.get(claimKey);

        if (claimName != null) {
            if (claimName instanceof List<?>) {
                return (List<String>) claimName;
            }

            if (claimName instanceof String) {
                groups.add((String) claimName);
            }
        }

        return groups;
    }
}