package mohalim.android.egybankstest;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mohalim.android.egybankstest.Adapters.MyQuizPager;
import mohalim.android.egybankstest.Models.Question;

public class QuizActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyQuizPager pagerAdapter;
    ArrayList<Question> questions;

    DatabaseReference testQuery = FirebaseDatabase.getInstance()
            .getReference("test");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        viewPager = findViewById(R.id.quiz_pager);

        questions = new ArrayList<>();

        Query query = testQuery.orderByChild("question_category").equalTo("iq_alahly");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Question question = data.getValue(Question.class);
                    questions.add(question);
                    pagerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        pagerAdapter = new MyQuizPager(getSupportFragmentManager(),questions);
        viewPager.setAdapter(pagerAdapter);



    }
}
