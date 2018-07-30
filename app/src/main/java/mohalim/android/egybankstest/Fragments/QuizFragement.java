package mohalim.android.egybankstest.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mohalim.android.egybankstest.Database.AppContract;
import mohalim.android.egybankstest.Models.Choice;
import mohalim.android.egybankstest.Models.Question;
import mohalim.android.egybankstest.R;

public class QuizFragement extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String QUESTIONS = "questions";
    private static final String POSITION = "position";
    private static final String UPDATE = "update";

    private static final int GET_CHOICES_LOADER_ID = 100;
    private static final int UPDATE_CHOSEN_ANSWER_LOADER_ID = 101;

    ArrayList<Question> questions;
    ArrayList<Choice> choices;
    int questionPosition;

    RadioGroup radioGroup;
    TextView questionText;

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

        choices = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        questionText = view.findViewById(R.id.m);
        radioGroup = view.findViewById(R.id.choices_radio);
        radioGroup.removeAllViews();

        questionText.setText(questions.get(questionPosition).getQuestion_text());


        if (getActivity().getSupportLoaderManager() == null){
            getActivity().getSupportLoaderManager()
                    .initLoader(GET_CHOICES_LOADER_ID,null,this)
                    .forceLoad();
        }else{
            getActivity().getSupportLoaderManager()
                    .restartLoader(GET_CHOICES_LOADER_ID,null,this)
                    .forceLoad();
        }





        return view;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id==GET_CHOICES_LOADER_ID){


            Uri choicesUri = AppContract.ChoiceEntry.CONTENT_URI
                    .buildUpon()
                    .appendPath(String.valueOf(questions.get(questionPosition).getQuestionId()))
                    .build();

            String questionId = String.valueOf(questions.get(questionPosition).getQuestionId());
            String questionIDSelection = AppContract.ChoiceEntry.COLUMN_QUESTION_ID + "=?";
            String[] questionIDSelectionArgs = new String[]{questionId};


            return new CursorLoader(
                    getActivity(),
                    choicesUri,
                    null,
                    questionIDSelection,
                    questionIDSelectionArgs,
                    null
            );

        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor choicesCursor) {



        if (choicesCursor.getCount() >0){
            while (choicesCursor.moveToNext()){
                String text = choicesCursor.getString(choicesCursor.getColumnIndex(AppContract.ChoiceEntry.COLUMN_CHOICE_TEXT));
                int is_correct = choicesCursor.getInt(choicesCursor.getColumnIndex(AppContract.ChoiceEntry.COLUMN_IS_TRUE));

                Choice choice = new Choice();
                choice.setChoiceText(text);
                choice.setCorrect(is_correct);

                choices.add(choice);
            }

            for (int i=0; i<choices.size();i++){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View radioButton = inflater.inflate(R.layout.radio_button,radioGroup,false);
                ((RadioButton) radioButton).setText(choices.get(i).getChoiceText());
                radioButton.setTag(choices.get(i).isCorrect());
                radioGroup.addView(radioButton);
            }


        }

        for (int i = 0; i<radioGroup.getChildCount();i++){
            radioGroup.getChildAt(i).setId(i+1);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                Uri updateChosenUri = AppContract.QuestionsEntry.CONTENT_URI
                        .buildUpon()
                        .appendPath(UPDATE)
                        .appendPath(String.valueOf(questions.get(questionPosition).getQuestionId())).build();

                ContentValues contentValues = new ContentValues();
                contentValues.put(AppContract.QuestionsEntry.COLUMN_ANSWER_CHOSEN,checkedId);

                getActivity().getContentResolver().update(updateChosenUri,contentValues,null,null);



                Cursor cursor = getActivity().getContentResolver().query(
                        AppContract.QuestionsEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(questions.get(questionPosition).getQuestionId())).build(),
                        null,null,null,null
                );

                if (cursor.getCount() == 1){
                    cursor.moveToFirst();
                    Log.v("string", cursor.getInt(cursor.getColumnIndex(AppContract.QuestionsEntry.COLUMN_ANSWER_CHOSEN))+"");
                }
            }
        });


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
