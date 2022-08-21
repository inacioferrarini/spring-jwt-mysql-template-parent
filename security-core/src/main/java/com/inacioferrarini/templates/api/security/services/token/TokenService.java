package com.inacioferrarini.templates.api.security.services.token;

import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;

import java.util.Map;

public interface TokenService {

    TokenDataRecord newToken(final Map<String, String> attributes);
    Map<String, String> verify(String token);

}

