package com.example.contacts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HelloApplication extends Application{

    public static TextReader tr;
    public static Scene primaryScene;
    public static Stage primaryStage;

    private static ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<Button> contactButtons = new ArrayList<>();
    private HashMap<Button, Scene> sceneMap = new HashMap<>();

    private final int buttonSpacing = 5;
    private final int buttonWidth = (int) (buttonSpacing * 30);
    private final int buttonHeight = (int) (buttonWidth * 0.3);
    private final int WIDTH = 340, HEIGHT = 520;


    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        Contact c0 = new Contact(5, "Jhon", "Hobbs", 10293980);
        Contact c1 = new Contact(3, "Caitlyn", "Jenner", 80671085);
        Contact c2 = new Contact(4, "Todd", "Howard", 12121212);
        contacts.add(c0);
        contacts.add(c2);
        contacts.add(c1);


        VBox layout = new VBox(buttonSpacing);
        layout.setAlignment(Pos.CENTER);

        createButtons(layout);

        primaryScene = new Scene(layout, WIDTH, HEIGHT);

        primaryStage.setTitle("Contacts");
        primaryStage.setResizable(false);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void createButtons(VBox layout) {
        for(Contact c : contacts){

            Button newButton = new Button(c.getFornavn() + " " + c.getEfternavn());
            createContactScene(newButton, c);

            addContactButtonDetails(newButton);

            layout.getChildren().add(newButton);
            contactButtons.add(newButton);
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

    private void createMenu(StackPane layout){
        Menu buttonMenu = new Menu("Contacts.csv");
        for (Button b : contactButtons){
            //buttonMenu.getItems().add(b);

        }

        //layout.getChildren().add(buttonMenu);
    }

    public static void main(String[] args) {
        tr = new TextReader();

        /*
        for(Contact c : tr.getContacts()) {
            contacts.add(c);
        }
        */

        launch();
    }

    private void addBackButton(VBox layout){
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(primaryScene));

        backButton.setPrefWidth(buttonWidth);
        backButton.setPrefHeight(buttonHeight);
        backButton.setPadding(new Insets(buttonSpacing));
        backButton.setAlignment(Pos.CENTER);

        layout.getChildren().add(backButton);
    }
}