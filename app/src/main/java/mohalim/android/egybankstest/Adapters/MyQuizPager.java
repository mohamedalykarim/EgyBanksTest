package mohalim.android.egybankstest.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import mohalim.android.egybankstest.Fragments.QuizFragement;
import mohalim.android.egybankstest.Models.Question;

public class MyQuizPager extends FragmentStatePagerAdapter {
    ArrayList<Question> questions;

    public MyQuizPager(FragmentManager fm, ArrayList<Question> questions) {
        super(fm);
        this.questions = questions;
    }


    @Override
    public Fragment getItem(int position) {
        return QuizFragement.newInstance(questions, position);
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
