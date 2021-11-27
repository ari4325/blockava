package com.dev.in;

import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String generateHash(String data) throws NoSuchAlgorithmException {
        String sha256hex = Hashing.sha256()
                .hashString(data, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
