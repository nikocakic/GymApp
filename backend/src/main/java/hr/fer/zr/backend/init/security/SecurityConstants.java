package hr.fer.zr.backend.init.security;

public abstract class SecurityConstants {
    public static final String SECRET = "404SecretNotFound";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final Long EXPIRATION_TIME = 10 * 24 * 60 * 60 * 1000L;
}
