package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
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

private Populate populate;
private QuizController quizController;

    private int width = 600;
    private int height = 500;
    private int correctAnswer = 0;
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
    private Button startQuizEuropeCapitalBtn = new Button();

    private ImageView flagView = new ImageView();

    @Override
    public void start(Stage primaryStage) throws Exception {
        populate = new Populate();
        quizController = new QuizController();

        window = primaryStage;

        // CSS id assignment
        menuTittleLabel.setId("menuTittleLabel");

        // Menu scene

        menuTittleLabel.setText("Welcome to Flag quiz press the button to start the quiz you want");

        startQuizStandardBtn.setText("Start world capital quiz!");
        startQuizStandardBtn.setOnAction(e -> {
            populate.populateMapStandardQuiz();
            startQuizScene();
            window.setScene(sceneQuiz);
        });

        startQuizEuropeCapitalBtn.setText("Europe capitals");
        startQuizEuropeCapitalBtn.setOnAction(event -> {
            populate.populateMapEuropeCapitalQuiz();
            startQuizScene();
            window.setScene(sceneQuiz);
        });

        Button startQuizScandinaviaCapitalBtn = new Button();
        startQuizScandinaviaCapitalBtn.setText("Scandinavia capitals");
        startQuizScandinaviaCapitalBtn.setOnAction(event -> {
            populate.populateMapQuizScandinaviaCapitalQuiz();
            startQuizScene();
            window.setScene(sceneQuiz);
        });


        VBox menuLayout = new VBox(20);
        menuLayout.getChildren().addAll(menuTittleLabel, startQuizStandardBtn, startQuizEuropeCapitalBtn,startQuizScandinaviaCapitalBtn);
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

        returnToMenuBtn.setOnAction((ActionEvent e) -> {
            quizController.setNumberOfCorrectAnswers(0);
            questionNumber = 0;
            populate.clear();
            populate.setCounter(0);
            updateScore();
            window.setScene(sceneMenu);
        });

        VBox quizScoreLayout = new VBox(20);
        quizScoreLayout.getChildren().addAll(playerScore, returnToMenuBtn);
        sceneQuizScore = new Scene(quizScoreLayout, width, height);

        // Start
        window.setScene(sceneMenu);
        window.setTitle("Flag quiz game");
        sceneMenu.getStylesheets().add(Main.class.getResource("Style.css").toString());
        sceneQuiz.getStylesheets().add(Main.class.getResource("Style.css").toString());
        sceneQuizScore.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm()); // .toExternalForm and toString() is the same
        primaryStage.show();

        // answer.addEventFilter(Event.ANY,e -> System.out.println(e));
    }

    private void startQuizScene(){
        flagView.minHeight(1 / height);
        flagView.minWidth(1 / width);

        fieldInformation.setText("Type in your answer here:");
        response.setText("");
        check.setText("Check");
        skipQuestionBtn.setText("Skip");
        quizController.setNumberOfQuestions(populate.size());
        fillScene(0);
        nextQuestion();
    }

    private void nextQuestion() {
        if (questionNumber < populate.size())  {
            while (nextQuestion) {
                fillScene(questionNumber);
                questionNumber += 1;
                nextQuestion = false;
                response.setText("");
            }
        } else {
            playerScore.setText("You got: " + quizController.getNumberOfCorrectAnswers() + " out of " + quizController.getNumberOfQuestions());
            questionNumber = questionNumberStart;
            window.setScene(sceneQuizScore);
            response.setText("");
        }
    }

    private void checkAnswer(String selectedField) {
        String userInputAnswer = answer.getText().toLowerCase();

        if (quizController.checkAnswerString(selectedField,userInputAnswer)) {
            correctAnswer ++;
            quizController.setNumberOfCorrectAnswers(correctAnswer);
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
    private void fillScene(int countrySelected) {
        Country selected;
        String selectedCountryName;
        String selectedCapitalName;
        String selectedFlagPicturePath;

        selected = populate.getCountry(countrySelected);
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

    private void updateScore() {
        score.setText(quizController.getNumberOfCorrectAnswers() + " / " + quizController.getNumberOfQuestions());
    }



    public static void main(String[] args) {
        launch(args);
    }
}
