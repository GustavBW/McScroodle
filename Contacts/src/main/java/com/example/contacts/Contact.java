package com.example.contacts;

public class Contact {

    private String fornavn = null, efternavn = null, email = null;
    private int nummer = 0, nummer2 = 0, id = 0;

    public Contact(int id, String fornavn, String efternavn, String email, int nummer, int nummer2) {
        this.id = id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.nummer = nummer;
        this.nummer2 = nummer2;
    }

    public Contact( int id, String fornavn, String efternavn, int nummer){
        this(id, fornavn, efternavn, "email", nummer, 69696969);
    }

    public String getFornavn(){
        return fornavn;
    }
    public String getEfternavn(){return efternavn;}
    public String getEmail(){return email;}
    public int getNummer(){return nummer;}
    public int getNummer2(){return nummer2;}
    public int getId(){return id;}

    public void setFornavn(String n){fornavn = n;}
    public void setEfternavn(String n){efternavn = n;}
    public void setEmail(String n){email = n;}
    public void setNummer(int n){nummer = n;}
    public void setNummer2(int n){nummer2 = n;}
}
