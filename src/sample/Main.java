package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private Map<Integer, Country> countries = new HashMap<>();

    private int numberOfCorrectAnswers = 0;
    private int numberOfQuestions;
    private int questionNumberStart = 1;
    private int questionNumber = questionNumberStart;
    private boolean nextQuestion = false;

    //fx
    private Label menuTittleLabel = new Label();
    private Label questionCapital = new Label();
    private Label score = new Label();
    private Label response = new Label();
    private Label fieldInformation = new Label();
    private Label playerScore = new Label();

    private Stage window;
    private Scene sceneMenu, sceneQuiz, sceneQuizScore;

    private TextField answer = new TextField();
    private Button check = new Button();
    private Button skipQuestionBtn = new Button();
    private Button startQuizStandardBtn = new Button();

    private ImageView flagView = new ImageView();

    @Override
    public void start(Stage primaryStage) throws Exception {

        int width = 600;
        int height = 500;

        window = primaryStage;

        // Menu scene
        startQuizStandardBtn.setText("Start quiz!");
        startQuizStandardBtn.setOnAction(e -> window.setScene(sceneQuiz));

        menuTittleLabel.setText("Welcome to Flag quiz press the button to start the quiz you want");


        VBox menuLayout = new VBox(20);
        menuLayout.getChildren().addAll(menuTittleLabel, startQuizStandardBtn);
        sceneMenu = new Scene(menuLayout, width, height);

        // Standard quiz scene
        GridPane quizGrid = new GridPane();
        sceneQuiz = new Scene(quizGrid, width, height);


        // adding to quizGrid
        quizGrid.add(questionCapital, 0, 0, 2, 1);
        quizGrid.add(flagView, 0, 1, 2, 2);
        quizGrid.add(fieldInformation, 0, 4, 1, 1);
        quizGrid.add(answer, 1, 4, 1, 1);
        quizGrid.add(check, 3, 4, 1, 1);
        quizGrid.add(skipQuestionBtn, 3, 5, 1, 1);
        quizGrid.add(score, 0, 5, 1, 1);
        quizGrid.add(response, 1, 5, 1, 1);


        quizGrid.setGridLinesVisible(false);


        // Quizscore scene

        Button returnToMenuBtn = new Button();

        returnToMenuBtn.setText("Return to menu");
        // returnToMenuBtn.setOnAction(e -> window.setScene(sceneMenu));

        returnToMenuBtn.setOnAction((ActionEvent e) -> {
            window.setScene(sceneMenu);
            numberOfCorrectAnswers = 0;
            updateScore();

        });


        VBox quizScoreLayout = new VBox(20);
        quizScoreLayout.getChildren().addAll(playerScore, returnToMenuBtn);
        sceneQuizScore = new Scene(quizScoreLayout, width, height);


        // Start
        window.setScene(sceneMenu);
        window.setTitle("Flag quiz game");

        populateMap();

        flagView.minHeight(1 / height);
        flagView.minWidth(1 / width);

        fieldInformation.setText("Type in your answer here:");

        response.setText("");
        check.setText("Check");
        skipQuestionBtn.setText("Skip");
        numberOfQuestions = countries.size();
        fillScene(0);

        sceneMenu.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        sceneQuiz.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        sceneQuizScore.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        primaryStage.show();


        nextQuestion();

        //answer.addEventFilter(Event.ANY,e -> System.out.println(e));
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


        check.setOnAction((ActionEvent e) -> checkAnswer(selectedCapitalName));

        sceneQuiz.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    checkAnswer(selectedCapitalName);
                }
            }
        });
        skipQuestionBtn.setOnAction((ActionEvent e) -> {
            if (skipQuestionBtn.getText().equalsIgnoreCase("skip")) {
                response.setText("The correct answer is " + selectedCapitalName);
                response.setStyle("-fx-text-fill: white");
                skipQuestionBtn.setText("next");
                window.show();
                check.setDisable(true);
            } else {
                nextQuestion = true;
                nextQuestion();
                check.setDisable(false);
                skipQuestionBtn.setText("skip");
                window.show();
            }


        });
    }

    private void checkAnswer(String selectedCapitalName) {
        String userInputAnswer = answer.getText().toLowerCase();
        if (selectedCapitalName.toLowerCase().equals(userInputAnswer)) {
            numberOfCorrectAnswers += 1;
            updateScore();
            nextQuestion = true;
            nextQuestion();
            answer.clear();
            response.setText("Correct!");
            response.setStyle("-fx-text-fill: green");

        } else {

            response.setText(answer.getText() + "is wrong, please try again");
            response.setStyle("-fx-text-fill: red");
            answer.clear();
        }
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


        // Europe
        countries.put(counter++, no);
        countries.put(counter++, swe);
        countries.put(counter++, dk);
        countries.put(counter++, md);
        countries.put(counter++, be);
        countries.put(counter++, fr);

        // NA
        countries.put(counter++, us);
        countries.put(counter++, ca);

    }

    private void nextQuestion() {

        if (questionNumber < countries.size()) {
            while (nextQuestion) {
                fillScene(questionNumber);
                questionNumber += 1;
                nextQuestion = false;
                response.setText("");

            }
        } else {
            playerScore.setText("You got: " + numberOfCorrectAnswers + " out of " + numberOfQuestions);
            fillScene(0);
            questionNumber = questionNumberStart;
            window.setScene(sceneQuizScore);
            response.setText("");
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
