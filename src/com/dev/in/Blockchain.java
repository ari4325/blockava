package com.dev.in;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    List<Block> chain = new ArrayList<>();

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
        if(block.index < prev.index)
            return false;
        if(!prev.generateHash().equals(block.previousHash))
            return false;
        if(block.timestamp < prev.timestamp)
            return false;
        return true;
    }

    boolean validateChain() throws NoSuchAlgorithmException {
        for (int i = 1; i<chain.size(); i++){
            Block prev = chain.get(i-1);
            Block curr = chain.get(i);
            if(!curr.previousHash.equals(prev.generateHash())){
                System.out.println(curr.previousHash);
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

}
