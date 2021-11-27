package com.dev.in;

import com.google.common.hash.Hashing;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("!!!!!!!!!!WELCOME TO BLOCKAVA!!!!!!!!!!");

        try {
            Blockchain blockchain = new Blockchain();
            while (true){
                System.out.println("Enter the following to continue:\n1. Create genesis\n2. Add a transaction\n3. Get last block info\n4. Exit");
                int choice = Integer.parseInt(br.readLine());
                switch (choice){
                    case 1:
                        blockchain.createGenesis();
                        break;
                    case 2:
                        JSONObject dataObject = new JSONObject();
                        System.out.println("\nEnter sender: ");
                        String sender = br.readLine();
                        System.out.println("\nEnter receiver: ");
                        String receiver = br.readLine();
                        System.out.println("\nEnter amount: ");
                        int amount = Integer.parseInt(br.readLine());

                        dataObject.append("sender", sender);
                        dataObject.append("receiver", receiver);
                        dataObject.append("amount", amount);

                        Block block = blockchain.getLastBlock();
                        if(block != null){
                            int nonce = blockchain.proof_of_work(dataObject);
                            blockchain.createBlock(nonce, block.generateHash(), dataObject, System.currentTimeMillis(), false);
                        }
                        break;
                    case 3:
                        Block block1 = blockchain.getLastBlock();
                        if(block1 == null){
                            System.out.println("Initialize the genesis block before continuing\n");
                        }else{
                            System.out.println("Sender: "+block1.data.get("sender")+"\nReceiver: "+block1.data.get("receiver")+"\nAmount: "+block1.data.get("amount")+"\nPreviousHash: "+block1.previousHash);
                        }
                        break;
                    case 4:
                        System.exit(0);
                }
            }
        } catch (NoSuchAlgorithmException | IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
