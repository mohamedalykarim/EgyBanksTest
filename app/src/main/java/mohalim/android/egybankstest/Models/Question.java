package mohalim.android.egybankstest.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Question implements Parcelable{
    int questionId;
    String question_type, question_category, question_text;
    HashMap<String, HashMap<String,Object>> choices;

    public Question() {
    }

    public Question(int questionId, String question_type, String question_category, String question_text, HashMap<String, HashMap<String, Object>> choices) {
        this.questionId = questionId;
        this.question_type = question_type;
        this.question_category = question_category;
        this.question_text = question_text;
        this.choices = choices;
    }

    protected Question(Parcel in) {
        questionId = in.readInt();
        question_type = in.readString();
        question_category = in.readString();
        question_text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionId);
        dest.writeString(question_type);
        dest.writeString(question_category);
        dest.writeString(question_text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_category() {
        return question_category;
    }

    public void setQuestion_category(String question_category) {
        this.question_category = question_category;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public HashMap<String, HashMap<String, Object>> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<String, HashMap<String, Object>> choices) {
        this.choices = choices;
    }
}
