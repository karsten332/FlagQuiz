package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Populate populate;
    private QuizController quizController;
    private int correctAnswer = 0;
    private int questionNumberStart = 0;
    private int questionNumber = questionNumberStart;
    private String rightAnswer;
    private String selectedQuizType;
    private String questionTextType;

    //fx
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
    private ImageView flagView = new ImageView();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label menuTittleLabel = new Label();
        int width = 600;
        int height = 500;

        populate = new Populate();
        quizController = new QuizController();

        window = primaryStage;

        // CSS id assignment
        menuTittleLabel.setId("menuTittleLabel");

        // Menu scene

        menuTittleLabel.setText("Welcome to Flag quiz press the button to start the quiz you want");

        Button startQuizStandardBtn = new Button();
        startQuizStandardBtn.setText("Start world capital quiz!");
        startQuizStandardBtn.setOnAction(e -> {
            populate.populateMapStandardQuiz();
            whenButtonPressed("Capital");
        });

        Button startQuizEuropeCapitalBtn = new Button();
        startQuizEuropeCapitalBtn.setText("Europe capitals");
        startQuizEuropeCapitalBtn.setOnAction(event -> {
            populate.populateMapEuropeCapitalQuiz();
            whenButtonPressed("Capital");
        });

        Button startQuizScandinaviaCapitalBtn = new Button();
        startQuizScandinaviaCapitalBtn.setText("Scandinavia capitals");
        startQuizScandinaviaCapitalBtn.setOnAction(event -> {
            populate.populateMapQuizScandinaviaCapitalQuiz();
            whenButtonPressed("Capital");
        });

        Button startQuizAsiaCountryBtn = new Button();
        startQuizAsiaCountryBtn.setText("Countries in Asia");
        startQuizAsiaCountryBtn.setOnAction(event -> {
            populate.populateMapQuizAsiaCountriesQuiz();
            whenButtonPressed("Country");

        });

        VBox menuLayout = new VBox(20);
        menuLayout.getChildren().addAll(menuTittleLabel, startQuizStandardBtn, startQuizEuropeCapitalBtn, startQuizScandinaviaCapitalBtn, startQuizAsiaCountryBtn);
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

        // QuizScore scene
        Button returnToMenuBtn = new Button();

        returnToMenuBtn.setText("Return to menu");

        returnToMenuBtn.setOnAction((ActionEvent e) -> {
            quizController.setNumberOfCorrectAnswers(0);
            questionNumber = 0;
            correctAnswer = 0;
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
        window.show();
        // answer.addEventFilter(Event.ANY,e -> System.out.println(e));
    }

    private void whenButtonPressed(String quiz) {
        findSelectedQuizType(quiz);
        startQuizScene();
        window.setScene(sceneQuiz);
    }

    private void startQuizScene() {
        flagView.setFitHeight(400);
        flagView.setFitWidth(500);
        fieldInformation.setText("Type in your answer here:");
        response.setText("");
        check.setText("Check");
        skipQuestionBtn.setText("Skip");
        quizController.setNumberOfQuestions(populate.size());
        fillScene(0, selectedQuizType);
        nextQuestion();
    }

    private void nextQuestion() {
        if (questionNumber < populate.size()) {
                fillScene(questionNumber, selectedQuizType);
                questionNumber += 1;
                response.setText("");
        } else {
            playerScore.setText("You got: " + quizController.getNumberOfCorrectAnswers() + " out of " + quizController.getNumberOfQuestions());
            questionNumber = questionNumberStart;
            window.setScene(sceneQuizScore);
            response.setText("");
        }
    }

    /**
     * Check the userinput to the field value in the country object
     *
     * @param selectedField the field that is going to be compared to the userinput
     */
    private void checkAnswer(String selectedField) {
        String userInputAnswer = answer.getText().toLowerCase();

        if (quizController.checkAnswerString(selectedField, userInputAnswer)) {
            correctAnswer++;
            quizController.setNumberOfCorrectAnswers(correctAnswer);
            updateScore();
            nextQuestion();
            answer.clear();
            response.setText("Correct!");
            response.setStyle("-fx-text-fill: green");
        } else {
            response.setText(answer.getText() + " is wrong, please try again");
            response.setStyle("-fx-text-fill: red");
            answer.clear();
        }
    }

    /**
     * Change the content of the quizscene
     *
     * @param countrySelected the number of the country that is going to be presented in the scene.
     * @param typeofQuiz      the type of quiz the window is going to presents
     */

    private void fillScene(int countrySelected, String typeofQuiz) {
        Country selected;
        String selectedCountryName;
        String selectedCapitalName;
        String selectedFlagPicturePath;

        selected = populate.getCountry(countrySelected);
        selectedCountryName = selected.getCountryName();
        selectedCapitalName = selected.getCapital();
        selectedFlagPicturePath = selected.getFlagPicturePath();

        switch (typeofQuiz) {
            case "Capital":
                rightAnswer = selectedCapitalName;
                questionTextType = "What is the capital in " + selectedCountryName + "?";
                break;
            case "Country":
                rightAnswer = selectedCountryName;
                questionTextType = "What is the country name?";
                break;
        }

        updateScore();

        Image flagImg = new Image(selectedFlagPicturePath);
        flagView.setImage(flagImg);

        check.setOnAction((ActionEvent e) -> checkAnswer(rightAnswer));

        sceneQuiz.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                checkAnswer(rightAnswer);
            }
        });

        skipQuestionBtn.setOnAction((ActionEvent e) -> {
            if (skipQuestionBtn.getText().equalsIgnoreCase("skip")) {
                response.setText("The correct answer is " + rightAnswer);
                response.setStyle("-fx-text-fill: white");
                skipQuestionBtn.setText("next");
                window.show();
                check.setDisable(true);
            } else {
                nextQuestion();
                check.setDisable(false);
                skipQuestionBtn.setText("skip");
                window.show();
            }
        });
        questionCapital.setText(questionTextType);
    }

    private void findSelectedQuizType(String typeOfQuiz) {
        switch (typeOfQuiz) {
            case "Capital":
                selectedQuizType = "Capital";
                break;
            case "Country":
                selectedQuizType = "Country";
                break;
        }
    }

    private void updateScore() {
        score.setText(quizController.getNumberOfCorrectAnswers() + " / " + quizController.getNumberOfQuestions());
    }

    public static void main(String[] args) {
        launch(args);
    }
}