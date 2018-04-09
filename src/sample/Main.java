package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private Map<Integer, Country> countries = new HashMap<>();

    private int numberOfCorrectAnswers = 0;
    private int numberOfQuestions;
    private int questionNumber = 0;
    private boolean nextQuestion = true;


    //fx
    private Label questionCapital = new Label();
    private Label score = new Label();
    private Label response = new Label();
    private Label fieldInformation = new Label();

    private TextField answer = new TextField();
    private Button check = new Button();
    private ImageView flagView = new ImageView();


    @Override
    public void start(Stage primaryStage) throws Exception {

        populateMap();

        // fx elements
        GridPane root = new GridPane();


        // adding to root
        root.add(questionCapital, 0, 0, 2, 1);
        root.add(flagView, 0, 1, 2, 2);
        root.add(fieldInformation, 0, 4, 1, 1);
        root.add(answer, 1, 4, 1, 1);
        root.add(check, 3, 4, 1, 1);
        root.add(score, 0, 5, 1, 1);
        root.add(response, 1, 5, 1, 1);


        root.setGridLinesVisible(false);

        int width = 600;
        int height = 500;

        flagView.minHeight(1 / height);
        flagView.minWidth(1 / width);

        fieldInformation.setText("Type in your answer here:");

        response.setText("");
        check.setText("Check");
        numberOfQuestions = countries.size();

        primaryStage.setTitle("Flag quiz game ");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();


        nextQuestion();


    }


    private void fillScene(int countrySelected) {

        Country selected;
        String selectedCountryName;
        String selectedCapitalName;
        String selectedFlagPicturePath;

        selected = countries.get(countrySelected);

        selectedCountryName = selected.getCountryName();
        selectedCapitalName = selected.getCapital();
        selectedFlagPicturePath = selected.getFlagPicturePath();

        questionCapital.setText("What is the capital in " + selectedCountryName + "?");

        updateScore();


        Image flagImg = new Image(selectedFlagPicturePath);
        flagView.setImage(flagImg);


        check.setOnAction((ActionEvent e) -> {
            if (selectedCapitalName.toLowerCase().equals(answer.getText().toLowerCase())) {
                numberOfCorrectAnswers += 1;
                updateScore();
                nextQuestion = true;
                nextQuestion();


                answer.clear();
            } else {
                response.setText(answer.getText() + "is wrong, please try again");
                answer.clear();
            }

        });
    }


    private void updateScore() {
        score.setText(numberOfCorrectAnswers + " / " + numberOfQuestions);
    }

    private void populateMap() {
        int counter = 0;

        // Countries
        Country no = new Country("Norway", "oslo", "europe", "sample/pictures/no.png");
        Country swe = new Country("Sweden", "stockholm", "europe", "sample/pictures/se.png");
        Country dk = new Country("Denmark", "copenhagen", "europe", "sample/pictures/dk.png");
        Country md = new Country("Moldova", "Chisinau", "europe", "sample/pictures/md.png");
        Country fr = new Country("France", "Paris", "europe", "sample/pictures/fr.png");
        Country be = new Country("Belgium", "Brussels", "europe", "sample/pictures/be.png");
        Country us = new Country("USA", "washington", "north america", "sample/pictures/us.png");
        Country ca = new Country("Canada", "Ottawa", "north america", "sample/pictures/ca.png");


        countries.put(counter++, no);
        countries.put(counter++, swe);
        countries.put(counter++, dk);
        countries.put(counter++, md);
        countries.put(counter++, be);
        countries.put(counter++, fr);

        countries.put(counter++, us);
        countries.put(counter++, ca);


    }

    private void nextQuestion() {
        if (questionNumber < countries.size()) {
            while (nextQuestion) {
                    fillScene(questionNumber);
                    questionNumber += 1;
                    nextQuestion = false;
                    response.setText("Correct!");
            }
        } else {
            System.out.println("hello");
            response.setText("You got: " + numberOfCorrectAnswers + " out of " + numberOfQuestions);
            check.setDisable(true);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
