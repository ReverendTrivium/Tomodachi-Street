package org.tomodachi.authetication;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import static java.lang.System.getenv;

public class AuthenticationService {


    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");

    public static Authentication getAuthentication(HttpServletRequest request) {
        // See what Auth_Token is
        System.out.println("Auth_Token Value: " + AUTH_TOKEN);

        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        System.out.println("Header API Key: " + apiKey);
        if (StringUtils.isBlank(apiKey) || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}