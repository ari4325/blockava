package com.dev.in;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    String previousHash;
    long timestamp;
    int index, nonce;
    JSONObject data;

    Block(int index, int nonce, String previousHash, long timestamp, JSONObject data){
        this.index = index;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.data = data;
    }

    String generateHash() throws NoSuchAlgorithmException {
        String block = "{"+nonce+"}"+"{"+data+"}";
        return Hash.generateHash(block);
    }
}


