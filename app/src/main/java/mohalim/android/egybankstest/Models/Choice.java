package mohalim.android.egybankstest.Models;

public class Choice {
    String choiceText;
    boolean isCoorect;

    public Choice() {
    }

    public Choice(String choiceText, boolean isCoorect) {
        this.choiceText = choiceText;
        this.isCoorect = isCoorect;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean isCoorect() {
        return isCoorect;
    }

    public void setCoorect(boolean coorect) {
        isCoorect = coorect;
    }
}
