package com.dev.in.data;

public class Account {
    private String address;
    private int nonce;
    private String codeHash;
    private String storageHash;

    Account(){}

    Account(String address, int nonce, String codeHash, String storageHash){
        this.address = address;
        this.nonce = nonce;
        this.codeHash = codeHash;
        this.storageHash = storageHash;
    }

    public int getNonce() {
        return nonce;
    }

    public String getAddress() {
        return address;
    }

    public String getCodeHash() {
        return codeHash;
    }

    public String getStorageHash() {
        return storageHash;
    }
}
