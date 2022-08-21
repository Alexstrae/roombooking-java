package com.virtualpairprogrammers.roombooking.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTService {

	private static RSAPrivateKey privateKey;
	private static RSAPublicKey publicKey;
	private long expirationDate = 1800000;

	@PostConstruct
	private void initKeys() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("rsa");
		generator.initialize(2048);
		KeyPair keyPair = generator.generateKeyPair();
		privateKey = (RSAPrivateKey) keyPair.getPrivate();
		publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	public String generateToken(String name, String role) {
		return JWT.create().withClaim("user", name).withClaim("role", role)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationDate))
				.sign(Algorithm.RSA256(publicKey, privateKey));
	}

	public String validateToken(String token) throws JWTVerificationException {
		String encodedPayload = JWT.require(Algorithm.RSA256(publicKey, privateKey)).build().verify(token).getPayload();
		return new String(Base64.getDecoder().decode(encodedPayload));

	}
}
