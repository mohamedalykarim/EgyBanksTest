package mohalim.android.egybankstest.Models;

public class Choice {
    String choiceText;
    int isCorrect;

    public Choice() {
    }


    public Choice(String choiceText, int isCorrect) {
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public int isCorrect() {
        return isCorrect;
    }

    public void setCorrect(int correct) {
        isCorrect = correct;
    }
}
