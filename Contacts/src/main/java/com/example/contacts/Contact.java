package com.example.contacts;

public class Contact {

    private String fornavn = null, efternavn = null, email = null;
    private String nummer, nummer2;
    private int id = 0;

    public Contact(int id, String fornavn, String efternavn, String email, String nummer, String nummer2) {
        this.id = id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.nummer = nummer;
        this.nummer2 = nummer2;
    }

    public Contact( int id, String fornavn, String efternavn, String nummer){
        this(id, fornavn, efternavn, "email", nummer, "69696969");
    }

    public Contact(String fornavn, String efternavn, String email, String nummer, String nummer2){
        this(999,fornavn,efternavn,email,nummer,nummer2);
        this.id = getNewId();
    }

    private int getNewId(){
        int highestIdFound = 0;

        for (Contact c : MainApp.contacts){
            if(c.getId() > highestIdFound){
                highestIdFound = c.getId();
            }
        }
        highestIdFound++;
        return highestIdFound;
    }

    public String getFornavn(){
        return fornavn;
    }
    public String getEfternavn(){return efternavn;}
    public String getEmail(){return email;}
    public String getNummer(){return nummer;}
    public String getNummer2(){return nummer2;}
    public int getId(){return id;}

    public void setFornavn(String n){fornavn = n;}
    public void setEfternavn(String n){efternavn = n;}
    public void setEmail(String n){email = n;}
    public void setNummer(String n){nummer = n;}
    public void setNummer2(String n){nummer2 = n;}
}
