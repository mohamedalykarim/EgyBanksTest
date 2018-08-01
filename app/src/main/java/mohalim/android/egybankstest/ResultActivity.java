package mohalim.android.egybankstest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import mohalim.android.egybankstest.Database.AppContract;
import mohalim.android.egybankstest.Models.Question;

public class ResultActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Integer> {
    private static final String SELECTED_QUIZ = "selected_quiz";
    private static final int LOADER_ID = 100;
    private static final String SESSION = "session";

    ArrayList<Question> questions;
    String selectedQuiz;

    double correctAnswersCount = 0, wrongAnswersCount = 0, totalQuestions = 0, result = 0;

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTV = findViewById(R.id.result_tv);

        if (getIntent().hasExtra(SELECTED_QUIZ)){
            questions = new ArrayList<>();
            selectedQuiz = getIntent().getStringExtra(SELECTED_QUIZ);
            Bundle bundle = new Bundle();
            bundle.putString(SELECTED_QUIZ, selectedQuiz);

            getSupportLoaderManager().initLoader(LOADER_ID,bundle,this).forceLoad();
        }
    }




    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<Integer>(this) {
            @Nullable
            @Override
            public Integer loadInBackground() {
                String selectedQuiz = args.getString(SELECTED_QUIZ);

                Uri sessionCategoryUri = AppContract.SessionEntry.CONTENT_URI
                        .buildUpon()
                        .appendPath(selectedQuiz)
                        .build();



                Cursor sessionCursor = getContentResolver()
                        .query(sessionCategoryUri,
                                null,
                                null,
                                null
                                ,null);



                if (sessionCursor.getCount() > 0) {
                    sessionCursor.moveToFirst();
                    int sessionId = sessionCursor.getInt(sessionCursor.getColumnIndex(AppContract.SessionEntry._ID));

                    Uri qusetionSessionUri = AppContract.QuestionsEntry.CONTENT_URI
                            .buildUpon()
                            .appendPath(SESSION)
                            .appendPath(String.valueOf(sessionId))
                            .build();


                    Cursor questionsCursor = getContentResolver().query(
                            qusetionSessionUri,
                            null,
                            null,
                            null,
                            null);


                    for (int i = 0; i <questionsCursor.getCount(); i++){
                        questionsCursor.moveToNext();
                        int isChosenCorrect = questionsCursor
                                .getInt(questionsCursor.getColumnIndex(AppContract.QuestionsEntry.COLUMN_IS_CHOSEN_CORRECT));

                        if (isChosenCorrect == 0){
                            wrongAnswersCount++;
                        }else if (isChosenCorrect == 1){
                            correctAnswersCount++;
                        }


                    }

                    totalQuestions = wrongAnswersCount+correctAnswersCount;

                    result = (correctAnswersCount / totalQuestions) *100;





                }



                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
        resultTV.setText((int) result + "%");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }
}
