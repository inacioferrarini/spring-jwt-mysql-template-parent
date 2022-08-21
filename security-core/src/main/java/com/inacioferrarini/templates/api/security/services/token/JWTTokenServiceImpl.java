package com.inacioferrarini.templates.api.security.services.token;

import com.google.common.collect.ImmutableMap;
import com.inacioferrarini.templates.api.security.models.records.TokenDataRecord;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@Service
final class JWTTokenServiceImpl implements Clock, TokenService {

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenServiceImpl.class);
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();
    private static final ResourceBundle resource = ResourceBundle.getBundle("security");
    private static final int TOKEN_VALID_DAYS = 30;

    private final String issuer;
    private final String secretKey;

    JWTTokenServiceImpl() {
        this.issuer = requireNonNull(
                resource.getString("jwt.issuer")
        );
        logger.debug("JWT Token Issuer: {}", this.issuer);
        this.secretKey = TextCodec.BASE64.encode(
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

    public TokenDataRecord newToken(final Map<String, String> attributes) {
        final DateTime now = DateTime.now();
        final Claims claims = Jwts.claims()
                                  .setIssuer(issuer)
                                  .setIssuedAt(now.toDate());

        claims.putAll(attributes);

        final String token = Jwts.builder()
                                 .setClaims(claims)
                                 .signWith(
                                         SignatureAlgorithm.HS256,
                                         secretKey
                                 )
                                 .compressWith(COMPRESSION_CODEC)
                                 .compact();

        return new TokenDataRecord(
                token,
                createValidDate(TOKEN_VALID_DAYS)
        );
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

    private LocalDateTime createValidDate(final int days) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        calendar.add(Calendar.DATE, days);
        return LocalDateTime.ofInstant(
                calendar.toInstant(),
                calendar.getTimeZone().toZoneId()
        );
    }

}
