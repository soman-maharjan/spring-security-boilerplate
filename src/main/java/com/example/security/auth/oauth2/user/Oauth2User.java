package com.example.security.auth.oauth2.user;

import java.util.Map;

public class Oauth2User {
    protected Map<String, Object> attributes;

    public Oauth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public String getId() {
        return (String) attributes.get("sub");
    }

    public String getFirstName() {
        return (String) attributes.get("given_name");
    }

    public String getMiddleName() {
        return null;
    }

    public String getLastName() {
        return (String) attributes.get("family_name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
