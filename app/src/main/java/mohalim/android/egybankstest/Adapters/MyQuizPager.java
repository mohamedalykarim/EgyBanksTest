package mohalim.android.egybankstest.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import mohalim.android.egybankstest.Fragments.QuizFragement;
import mohalim.android.egybankstest.Models.Question;

public class MyQuizPager extends FragmentStatePagerAdapter {
    ArrayList<Question> questions;
    String selectedQuiz;

    public MyQuizPager(FragmentManager fm, ArrayList<Question> questions, String selectedQuiz) {
        super(fm);
        this.questions = questions;
        this.selectedQuiz = selectedQuiz;
    }


    @Override
    public Fragment getItem(int position) {
        QuizFragement fragment = new QuizFragement();
        fragment.setQuestions(questions);
        fragment.setQuestionPosition(position);
        fragment.setSelectedQuiz(selectedQuiz);

        return fragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public int getItemPosition(Object object) {
        QuizFragement fragment = (QuizFragement) object;
        int position = fragment.getQuestionPosition();

        if (position >= 0) {
            return position;
        } else {
            return POSITION_NONE;
        }
    }

}
