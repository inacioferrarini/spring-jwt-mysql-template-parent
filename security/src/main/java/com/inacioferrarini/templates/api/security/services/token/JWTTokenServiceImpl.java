package com.inacioferrarini.templates.api.security.services.token;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.impl.TextCodec.BASE64;
import static java.util.Objects.requireNonNull;

@Service
final class JWTTokenServiceImpl implements Clock, TokenService {

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenServiceImpl.class);
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();
    private static final ResourceBundle resource = ResourceBundle.getBundle("security");

    private String issuer;
    private String secretKey;

    JWTTokenServiceImpl() {
        this.issuer = requireNonNull(
                resource.getString("jwt.issuer")
        );
        logger.debug("JWT Token Issuer: {}", this.issuer);
        this.secretKey = BASE64.encode(
                requireNonNull(resource.getString("jwt.secretKeySeed"))
        );
    }

    private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
        try {
            final Claims claims = toClaims.get();
            final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            for (final Map.Entry<String, Object> e : claims.entrySet()) {
                builder.put(
                        e.getKey(),
                        String.valueOf(e.getValue())
                );
            }
            return builder.build();
        } catch (final IllegalArgumentException | JwtException e) {
            return ImmutableMap.of();
        }
    }

    public String newToken(final Map<String, String> attributes) {
        final DateTime now = DateTime.now();
        final Claims claims = Jwts.claims()
                                  .setIssuer(issuer)
                                  .setIssuedAt(now.toDate());

        claims.putAll(attributes);

        // TODO: Store JWT Token in repository

        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(
                           HS256,
                           secretKey
                   )
                   .compressWith(COMPRESSION_CODEC)
                   .compact();
    }

    @Override
    public Map<String, String> verify(final String token) {
        final JwtParser parser = Jwts.parser()
                                     .requireIssuer(issuer)
                                     .setClock(this)
                                     .setSigningKey(secretKey);
        return parseClaims(
                () -> parser.parseClaimsJws(token)
                            .getBody()
        );
    }

    @Override
    public Date now() {
        final DateTime now = DateTime.now();
        return now.toDate();
    }

}
