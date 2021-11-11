package com.example.contacts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextReader {

    private final String filepath = "Data/Contacts.csv";
    private String splitBy = ";";

    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> allContacts = new ArrayList<>();
        String line;


        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            while((line = br.readLine()) != null){
                String[] contact = line.split(splitBy);

                allContacts.add(new Contact(Integer.parseInt(contact[0]),contact[1],contact[2],contact[3],Integer.parseInt(contact[4]),Integer.parseInt(contact[5])));

            }

            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        return allContacts;
    }

    public boolean saveContact(Contact c){
        boolean success = false;

        try {
            String currentLine;
            FileWriter fw = new FileWriter(filepath);
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String lineToWrite = c.getFornavn() + ";" + c.getEfternavn() + ";" + c.getEmail() + ";" + c.getNummer() + ";" + c.getNummer2();

            while((currentLine = br.readLine()) != null){
                String[] lineS = currentLine.split(splitBy);

                if(Integer.parseInt(lineS[0]) == c.getId()){
                    boolean success2 = false;
                    try {
                        fw.write(lineToWrite);
                        success2 = true;
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    if(success2){
                        break;
                    }
                }
            }

            success = true;
            fw.close();
            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        return success;
    }
}
