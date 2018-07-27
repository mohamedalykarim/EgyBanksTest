package mohalim.android.egybankstest.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mohalim.android.egybankstest.Models.Question;
import mohalim.android.egybankstest.R;

public class QuizFragement extends Fragment{
    private static final String QUESTIONS = "questions";
    private static final String POSITION = "position";

    ArrayList<Question> questions;
    int questionPosition;

    public static QuizFragement newInstance(ArrayList<Question> questions, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(QUESTIONS,questions);
        args.putInt(POSITION,position);
        
        QuizFragement fragment = new QuizFragement();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(POSITION) && getArguments().containsKey(QUESTIONS)){
            questions = getArguments().getParcelableArrayList(QUESTIONS);
            questionPosition = getArguments().getInt(POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);

        TextView questionText = view.findViewById(R.id.m);
        questionText.setText(questions.get(questionPosition).getQuestion_text());

        return view;

    }
}
