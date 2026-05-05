package com.celzo_aveiro.fipe_api.interfaces.rest.controller;

import com.celzo_aveiro.fipe_api.interfaces.rest.dto.TokenRequest;
import com.celzo_aveiro.fipe_api.interfaces.rest.dto.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String EMISSOR = "fipe-api";

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final Duration tempoDeVida;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            @Value("${fipe.security.jwt.ttl-seconds:3600}") long ttlSeconds
    ) {
        this.authenticationManager = Objects.requireNonNull(authenticationManager);
        this.jwtEncoder = Objects.requireNonNull(jwtEncoder);
        this.tempoDeVida = Duration.ofSeconds(ttlSeconds);
    }

    @PostMapping("/token")
    public TokenResponse token(@RequestBody @Valid TokenRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        var agora = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer(EMISSOR)
                .issuedAt(agora)
                .expiresAt(agora.plus(tempoDeVida))
                .subject(authentication.getName())
                .build();
        var headers = JwsHeader.with(MacAlgorithm.HS256).build();
        var token = jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();

        return new TokenResponse(token, "Bearer", tempoDeVida.toSeconds());
    }
}
