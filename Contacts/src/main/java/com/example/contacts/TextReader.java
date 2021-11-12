package com.example.contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

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

                Contact newContact = new Contact(
                        Integer.parseInt(contact[0]),
                        contact[1],
                        contact[2],
                        contact[3],
                        contact[4],
                        contact[5]);

                allContacts.add(newContact);

            }

            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return allContacts;
    }

    public boolean editContact(Contact c){
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
                    }catch(Exception e){
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

    public boolean addContact(ArrayList<Contact> contacts){
        boolean success = false;
        String currentLine;
        int lastKnownId = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));

            String contactsToData = "";

            for (Contact c : contacts) {
                contactsToData += lastKnownId + ";" + c.getFornavn() + ";" + c.getEfternavn() + ";" + c.getEmail() + ";" + c.getNummer() + ";" + c.getNummer2() + "\n";
                lastKnownId++;
            }
            bw.write(contactsToData);


            br.close();
            bw.close();
            success = true;
        }catch (Exception e){
          e.printStackTrace();
        }


        return success;
    }
}
