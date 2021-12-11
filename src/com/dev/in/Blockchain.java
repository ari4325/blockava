package com.dev.in;

import com.dev.in.data.Account;
import com.dev.in.data.Block;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONObject;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    List<Block> chain = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    List<String> addresses = new ArrayList<>();

    void createGenesis() throws NoSuchAlgorithmException {
        if(chain.size() == 0) {
            JSONObject dataObject = new JSONObject();
            boolean created = createBlock(0, 0 + "", dataObject, System.currentTimeMillis(), true);
            if(created)
                System.out.println("Genesis block initialized\n");
        }else{
            System.out.println("Genesis block has already been initialized\n");
        }
    }

    boolean createBlock(int nonce, String previousHash, JSONObject data, long timestamp, boolean isGenesis) throws NoSuchAlgorithmException {
        Block block = new Block(chain.size()+1, nonce, previousHash, timestamp, data);
        if(isGenesis) {
            chain.add(block);
            return true;
        }
        else {
            if (chain.size() != 0) {
                if (validateBlock(block, getLastBlock())) {
                    System.out.println("Your transaction has been added successfully...\n");
                    chain.add(block);
                    if(validateChain())
                        return true;
                    else
                        return false;
                }
            } else {
                System.out.println("Please initialize genesis block before starting!!!\n");
                return false;
            }
        }

        System.out.println("Your block could not be validated...");
        return false;
    }

    int proof_of_work(JSONObject dataObject) throws NoSuchAlgorithmException, InterruptedException {
        int pow = 0;
        while(true){
            String data = pow+dataObject.toString();
            System.out.println(data);
            String hash = Hash.generateHash(data+"");
            System.out.println(hash);
            if(hash.substring(0,6).equals("000000"))
                return pow;
            pow++;
        }
    }



    boolean validateBlock(Block block, Block prev) throws NoSuchAlgorithmException {
        if(block.getIndex() < prev.getIndex())
            return false;
        if(!prev.generateHash().equals(block.getPreviousHash()))
            return false;
        if(block.getTimestamp() < prev.getTimestamp())
            return false;
        return true;
    }

    boolean validateChain() throws NoSuchAlgorithmException {
        for (int i = 1; i<chain.size(); i++){
            Block prev = chain.get(i-1);
            Block curr = chain.get(i);
            if(!curr.getPreviousHash().equals(prev.generateHash())){
                System.out.println(curr.getPreviousHash());
                System.out.println(prev.generateHash());
                System.out.println("There is some issue with the chain. Invalidated Blockchain");
                //Make logic here for validating with the other nodes
                return false;
            }
        }

        return true;
    }

    Block getLastBlock(){
        if(chain.size() == 0){
            System.out.println("Initialize the genesis block before beginning...\n");
            return null;
        }
        int last_index = chain.size()-1;
        return chain.get(last_index);
    }

    PrivateKey generatePrivateKey()  throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
        g.initialize(ecSpec, new SecureRandom());
        KeyPair keypair = g.generateKeyPair();
        PublicKey publicKey = keypair.getPublic();
        PrivateKey privateKey = keypair.getPrivate();

        return privateKey;
    }

     String generateKey(String password) throws Throwable {
        final byte[] salt = new byte[] { -30, -91, -71, 33, 41, 115, -89, 34, 115, 30, -42, -5, 18, -72,
                -106, -30 };
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128); // AES-128
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return bytesToText(f.generateSecret(spec).getEncoded());
    }

    private static String bytesToText(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int num = bytes[i];
            if (num < 0)
                num = 127 + (num * -1); // fix negative back to positive
            String hex = Integer.toHexString(num);
            if (hex.length() == 1) {
                hex = "0" + hex; // ensure 2 digits
            }
            sb.append(hex);
        }

        return sb.toString();
    }

    void generateAddress(String private_key) {

    }

}
