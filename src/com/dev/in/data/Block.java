package com.dev.in.data;

import com.dev.in.Hash;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private String previousHash;
    private long timestamp;
    private int index;
    private int nonce;
    private JSONObject data;

    public Block(int index, int nonce, String previousHash, long timestamp, JSONObject data){
        this.index = index;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.data = data;
    }

    public String generateHash() throws NoSuchAlgorithmException {
        String block = "{"+nonce+"}"+"{"+data+"}";
        return Hash.generateHash(block);
    }

    public int getIndex() {
        return index;
    }

    public int getNonce() {
        return nonce;
    }

    public JSONObject getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }
}


