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

    public boolean addContact(ArrayList<Contact> contacts){
        boolean success = false;
        String currentLine;
        int lastKnownId = 0;

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));

            String contactsToData = "";

            for (Contact c : contacts) {
                contactsToData += lastKnownId + ";" + c.getFornavn() + ";" + c.getEfternavn() + ";" + c.getEmail() + ";" + c.getNummer() + ";" + c.getNummer2() + "\n";
                lastKnownId++;
            }
            bw.write(contactsToData);


            bw.close();
            success = true;
        }catch (Exception e){
          e.printStackTrace();
        }


        return success;
    }

    public boolean removeContact(Contact c){
        boolean success = false;

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));

            String contactsToData = "";

            for (Contact co : MainApp.contacts){
                if (co.getId() != c.getId()){
                    contactsToData += co.getId() + ";" + co.getFornavn() + ";" + co.getEfternavn() + ";" + co.getEmail() + ";" + co.getNummer() + ";" + co.getNummer2() + "\n";
                }
            }

            bw.write(contactsToData);
            bw.close();

            success = true;

        }catch (Exception e){
            e.printStackTrace();
        }


        return success;
    }
}
