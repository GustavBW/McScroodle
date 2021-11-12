package com.example.contacts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.CharacterStringConverter;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainApp extends javafx.application.Application {

    public static TextReader tr;
    public static Scene primaryScene;
    public static Stage primaryStage;

    public static ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<Button> contactButtons = new ArrayList<>();
    private HashMap<Button, Scene> sceneMap = new HashMap<>();

    private final int buttonSpacing = 5;
    private final int buttonWidth = (int) (buttonSpacing * 30);
    private final int buttonHeight = (int) (buttonWidth * 0.3);
    private final int WIDTH = 340, HEIGHT = 520;

    private VBox mainLayout;


    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        tr.addContact(contacts);

        mainLayout = new VBox(buttonSpacing);
        mainLayout.setAlignment(Pos.CENTER);

        createButtons(mainLayout, true);

        primaryScene = new Scene(mainLayout, WIDTH, HEIGHT);

        primaryStage.setTitle("Contacts");
        primaryStage.setResizable(false);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    private void createSingleButton(Contact c){
        Button newButton = new Button(c.getFornavn() + " " + c.getEfternavn());
        createContactScene(newButton, c);

        addContactButtonDetails(newButton);

        mainLayout.getChildren().add(newButton);
        contactButtons.add(newButton);
    }
    private void createButtons(VBox layout, boolean makeNewContactButton) {
        for(Contact c : contacts){

            Button newButton = new Button(c.getFornavn() + " " + c.getEfternavn());
            createContactScene(newButton, c);

            addContactButtonDetails(newButton);

            layout.getChildren().add(newButton);
            contactButtons.add(newButton);
        }

        if(makeNewContactButton) {
            Button newContactButton = new Button("+");
            newContactButton.setPrefWidth((int) (buttonWidth / 2));
            newContactButton.setPrefHeight((int) (buttonHeight / 2));
            newContactButton.setAlignment(Pos.CENTER);
            newContactButton.setPadding(new Insets(buttonSpacing));
            newContactButton.setOnAction(e -> primaryStage.setScene(getNewContactScene()));

            layout.getChildren().add(newContactButton);
        }
    }

    private Scene getNewContactScene(){

        VBox layout = new VBox(buttonSpacing);
        layout.setAlignment(Pos.CENTER);

        ArrayList<TextField> textfields = new ArrayList<>();

        TextField nameInput = new TextField("Name");
        TextField surnameInput = new TextField("Surname");
        TextField number1Input = new TextField("Number 1");
        TextField number2Input = new TextField("Number 2");
        TextField emailInput = new TextField("Email");
        textfields.add(nameInput);
        textfields.add(surnameInput);
        textfields.add(emailInput);
        textfields.add(number1Input);
        textfields.add(number2Input);

        makeAndAddTextFields(layout, textfields);

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth((int) (buttonWidth / 2));
        saveButton.setPrefHeight((int)(buttonHeight / 2));
        saveButton.setAlignment(Pos.CENTER);
        saveButton.setOnAction(e -> getInputsAndCreateContact(nameInput, surnameInput, emailInput, number1Input, number2Input));
        layout.getChildren().add(saveButton);

        Scene newContactScene = new Scene(layout, WIDTH, HEIGHT);

        addBackButton(layout);

        return newContactScene;
    }

    private void getInputsAndCreateContact(TextField name, TextField surname, TextField email, TextField number1, TextField number2){

        Contact newContact = new Contact(
                name.getCharacters().toString(),
                surname.getCharacters().toString(),
                email.getCharacters().toString(),
                number1.getCharacters().toString(),
                number2.getCharacters().toString()
        );
        makeNewMainLayout(newContact);
    }

    private void makeNewMainLayout(Contact newC){
        mainLayout.getChildren().clear();
        contactButtons.clear();

        contacts.add(newC);
        tr.addContact(contacts);

        contacts.clear();

        for(Contact c : tr.getContacts()){
            contacts.add(c);
        }

        createButtons(mainLayout, false);
        primaryStage.setScene(primaryScene);
    }

    private void makeAndAddTextFields(VBox layout, ArrayList<TextField> textfields){
        for (TextField t : textfields){
            t.setAlignment(Pos.CENTER);
            t.setPrefHeight(buttonHeight);
            t.setPrefWidth(buttonWidth);
            t.setPadding(new Insets(buttonSpacing));
            layout.getChildren().add(t);
        }
    }

    private void addContactButtonDetails(Button newButton) {
        newButton.setPrefWidth(buttonWidth);
        newButton.setPrefHeight(buttonHeight);
        newButton.setPadding(new Insets(buttonSpacing));
        newButton.setAlignment(Pos.CENTER_LEFT);
        newButton.setOnAction(e -> primaryStage.setScene(sceneMap.get(newButton)));
    }

    private void createContactScene(Button button, Contact c){
        VBox newLayout = new VBox(buttonSpacing);
        newLayout.setAlignment(Pos.CENTER);

        //Button name = new Button(c.getFornavn() + " " + c.getEfternavn());
        Button number1 = new Button(String.valueOf(c.getNummer()));
        Button number2 = new Button(String.valueOf(c.getNummer2()));
        Button email = new Button(c.getEmail());
        Button name = new Button(c.getFornavn() + " " + c.getEfternavn());

        ArrayList<Button> buttonsInScene = new ArrayList<Button>();
        buttonsInScene.add(number1);
        buttonsInScene.add(number2);
        buttonsInScene.add(email);
        buttonsInScene.add(name);

        for(Button b : buttonsInScene){
            b.setPrefWidth(buttonWidth);
            b.setPrefHeight(buttonHeight);
            b.setPadding(new Insets(buttonSpacing));
            b.setAlignment(Pos.CENTER_LEFT);
        }

        newLayout.getChildren().add(name);
        newLayout.getChildren().add(number1);
        newLayout.getChildren().add(number2);
        newLayout.getChildren().add(email);
        addBackButton(newLayout);

        Scene newScene = new Scene(newLayout, WIDTH, HEIGHT);

        sceneMap.put(button, newScene);
    }

    public static void main(String[] args) {
        tr = new TextReader();

        for(Contact c : tr.getContacts()) {
            contacts.add(c);
        }

        launch();
    }

    private void addBackButton(VBox layout){
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(primaryScene));

        backButton.setPrefWidth((int) (buttonWidth / 2));
        backButton.setPrefHeight((int) (buttonHeight / 2));
        backButton.setPadding(new Insets(buttonSpacing));
        backButton.setAlignment(Pos.CENTER);

        layout.getChildren().add(backButton);
    }
}