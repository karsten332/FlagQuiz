package sample;

public class QuizController {
    private int numberOfCorrectAnswers = 0;
    private int numberOfQuestions;

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public boolean checkAnswerString(String selectedField, String userInput) {

        if (selectedField.toLowerCase().equals(userInput.toLowerCase())) {
            return true;

        } else {

            return false;
        }
    }


}

