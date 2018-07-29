package mohalim.android.egybankstest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import mohalim.android.egybankstest.Adapters.MyQuizPager;
import mohalim.android.egybankstest.Database.AppContract;
import mohalim.android.egybankstest.Models.Choice;
import mohalim.android.egybankstest.Models.Question;

import static mohalim.android.egybankstest.Utils.JSONUtils.getChoicesFromJSON;

public class QuizActivity extends AppCompatActivity {

    private static final String SELECTED_QUIZ = "selected_quiz";
    private static final String IQ_ALAHLY = "iq_alahly";
    private static final String ENGLISH_ALAHLY = "english_alahly";
    private static final String TECHNICAL_ALAHLY = "technical_alahly";
    private static final String QUESTION_CATEGORY = "question_category";


    ViewPager viewPager;
    MyQuizPager pagerAdapter;
    ArrayList<Question> questionsFromSession;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        viewPager = findViewById(R.id.quiz_pager);

        questionsFromSession = new ArrayList<>();

        pagerAdapter = new MyQuizPager(getSupportFragmentManager(),questionsFromSession);
        viewPager.setAdapter(pagerAdapter);

        handleSession();



    }

    private void handleSession() {
        String selectedQuiz = "";

        if (getIntent().hasExtra(SELECTED_QUIZ)){
            selectedQuiz = getIntent().getStringExtra(SELECTED_QUIZ);

            Uri sessionUri = AppContract.SessionEntry.CONTENT_URI
                    .buildUpon()
                    .appendPath(selectedQuiz)
                    .build();

            Cursor cursorSession = getContentResolver().query(
                    sessionUri,
                    null,
                    null,
                    null,
                    null);


            if (cursorSession.getCount() > 0){
                /**
                 * if there is a session
                 */

            }else{
                /**
                 * if there is no session
                 */
                Query testQuery = null;

                if (selectedQuiz.equals(IQ_ALAHLY)){
                    testQuery = FirebaseDatabase.getInstance()
                            .getReference("test").orderByChild(QUESTION_CATEGORY).equalTo(IQ_ALAHLY);
                }else if (selectedQuiz.equals(ENGLISH_ALAHLY)){
                    testQuery = FirebaseDatabase.getInstance()
                            .getReference("test").orderByChild(QUESTION_CATEGORY).equalTo(ENGLISH_ALAHLY);
                }else if (selectedQuiz.equals(TECHNICAL_ALAHLY)){
                    testQuery = FirebaseDatabase.getInstance()
                            .getReference("test").orderByChild(QUESTION_CATEGORY).equalTo(TECHNICAL_ALAHLY);
                }

                if (testQuery ==null){
                    Toast.makeText(this, "Error: Please Try Again Later", Toast.LENGTH_SHORT).show();
                    return;
                }


                final String finalSelectedQuiz = selectedQuiz;
                testQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Question> questions = new ArrayList<>();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            Question question = data.getValue(Question.class);
                            questions.add(question);
                        }

                        insertNewSession(questions,finalSelectedQuiz);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        }







    }


    public void insertNewSession(ArrayList<Question> questions, String selectedQuiz){
        Uri sessionUri = AppContract.SessionEntry.CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.SessionEntry.COLUMN_SESSION_CATEGORY, selectedQuiz);

        Uri sessionUriId = getContentResolver().insert(
                sessionUri,
                contentValues
        );

        int sessionId = Integer.parseInt(sessionUriId.getPathSegments().get(1));

        Collections.shuffle(questions);
        Uri questionUri = AppContract.QuestionsEntry.CONTENT_URI;
        Uri choicesUri = AppContract.ChoiceEntry.CONTENT_URI;

        for (int i = 0; i< questions.size(); i++){
            ContentValues questionsContentvalues = new ContentValues();
            questionsContentvalues.put(AppContract.QuestionsEntry.COLUMN_SESSION_ID, sessionId);
            questionsContentvalues.put(AppContract.QuestionsEntry.COLUMN_QUESTION_CATEGORY,questions.get(i).getQuestion_category());
            questionsContentvalues.put(AppContract.QuestionsEntry.COLUMN_QUESTION_TEXT,questions.get(i).getQuestion_text());
            questionsContentvalues.put(AppContract.QuestionsEntry.COLUMN_QUESTION_TYPE,questions.get(i).getQuestion_type());

            Uri newQuestionUri = getContentResolver().insert(questionUri,questionsContentvalues);
            int questionID = Integer.parseInt(newQuestionUri.getPathSegments().get(1));

            HashMap<String, HashMap<String, Object>> choicesString = questions.get(i).getChoices();
            Iterator it = choicesString.entrySet().iterator();
            while (it.hasNext()){
                HashMap.Entry<String, HashMap<String, Object>> choices = (HashMap.Entry<String, HashMap<String, Object>>) it.next();
                HashMap<String,Object> choice = choices.getValue();
                Iterator choiceIT = choice.entrySet().iterator();
                while (choiceIT.hasNext()){
                    HashMap.Entry<String,Object> theChoice = (HashMap.Entry<String, Object>) choiceIT.next();
                    if (theChoice.getKey().equals("is_correct")){
                        Log.v("choice :", theChoice.getValue().toString());
                    }
                }
            }



        }
    }
}
