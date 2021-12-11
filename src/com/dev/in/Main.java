package com.dev.in;

import com.dev.in.data.Block;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
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
                            JSONObject data = block1.getData();
                            System.out.println("Sender: "+data.get("sender")+"\nReceiver: "+data.get("receiver")+"\nAmount: "+data.get("amount")+"\nPreviousHash: "+block1.getPreviousHash());
                        }
                        break;
                    case 4:
                        System.exit(0);
                    case 5:
                        System.out.println("Enter a password for your account: ");
                        String password = br.readLine();
                        //PrivateKey privateKey = blockchain.generatePrivateKey();
                        System.out.println(blockchain.generateKey(password));
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
