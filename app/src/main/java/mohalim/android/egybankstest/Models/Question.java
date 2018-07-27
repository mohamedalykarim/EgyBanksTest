package mohalim.android.egybankstest.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Question implements Parcelable{
    String question_type, question_category, question_text;
    Object choices;

    public Question() {
    }

    public Question(String question_type, String question_category, String question_text, Object choices) {
        this.question_type = question_type;
        this.question_category = question_category;
        this.question_text = question_text;
        this.choices = choices;
    }

    protected Question(Parcel in) {
        question_type = in.readString();
        question_category = in.readString();
        question_text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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

    public Object getChoices() {
        return choices;
    }

    public void setChoices(Object choices) {
        this.choices = choices;
    }
}
