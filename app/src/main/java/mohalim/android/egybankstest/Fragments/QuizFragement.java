package mohalim.android.egybankstest.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import mohalim.android.egybankstest.ResultActivity;

public class QuizFragement extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String UPDATE = "update";
    private static final String SELECTED_QUIZ = "selected_quiz";
    private static final String CHECKED_ID = "checked_id";

    private static final int GET_CHOICES_LOADER_ID = 100;
    private static final int GET_CHOSEN_ANSWER_LOADER_ID = 101;

    private static final int GET_CHOICES_LOADER_ID_FIRST = 103;
    private static final int GET_CHOSEN_ANSWER_LOADER_ID_FIRST = 104;

    private static final int UPDATE_ANSWER_LOADER_ID = 105;



    ArrayList<Question> questions;
    ArrayList<Choice> choices;
    int questionPosition;
    String selectedQuiz;

    RadioGroup radioGroup;
    TextView questionText;
    int chosenAsnwer = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        /**
         * initiate the loader or restart it depending on the position
         * of the question.
         */

        if (questionPosition == 0){

            if (getActivity().getSupportLoaderManager() == null){
                getActivity().getSupportLoaderManager().initLoader(
                        GET_CHOICES_LOADER_ID_FIRST,
                        null,
                        this
                ).forceLoad();
            }else{
                getActivity().getSupportLoaderManager().restartLoader(
                        GET_CHOICES_LOADER_ID_FIRST,
                        null,
                        this
                ).forceLoad();
            }


        }else if (questionPosition == 1){
            if (getActivity().getSupportLoaderManager() == null){
                getActivity().getSupportLoaderManager().initLoader(
                        GET_CHOICES_LOADER_ID,
                        null,
                        this
                ).forceLoad();
            }else {
                getActivity().getSupportLoaderManager().restartLoader(
                        GET_CHOICES_LOADER_ID,
                        null,
                        this
                ).forceLoad();
            }


        }else {

            getActivity().getSupportLoaderManager().restartLoader(
                    GET_CHOICES_LOADER_ID,
                    null,
                    this
            ).forceLoad();
        }




        /**
         * end the session
         * and go to result activity
         */

        FloatingActionButton fab = view.findViewById(R.id.end_session);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra(SELECTED_QUIZ,selectedQuiz);
                startActivity(intent);
                getActivity().getSupportLoaderManager().destroyLoader(GET_CHOICES_LOADER_ID);
                getActivity().getSupportLoaderManager().destroyLoader(GET_CHOSEN_ANSWER_LOADER_ID);
                getActivity().getSupportLoaderManager().destroyLoader(GET_CHOICES_LOADER_ID_FIRST);
                getActivity().getSupportLoaderManager().destroyLoader(GET_CHOSEN_ANSWER_LOADER_ID_FIRST);
                getActivity().getSupportLoaderManager().destroyLoader(UPDATE_ANSWER_LOADER_ID);
                getActivity().finish();
            }
        });



        return view;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable final Bundle args) {


        if (id==GET_CHOICES_LOADER_ID || id == GET_CHOICES_LOADER_ID_FIRST){

            /**
             * get the choices cursor
             */

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



        }else if(id == GET_CHOSEN_ANSWER_LOADER_ID || id == GET_CHOSEN_ANSWER_LOADER_ID_FIRST){

            /**
             * handle the choices clicks and add the values to the database
             */

            return new AsyncTaskLoader<Cursor>(getActivity()) {
                @Nullable
                @Override
                public Cursor loadInBackground() {

                    Uri questionUri = AppContract.QuestionsEntry.CONTENT_URI
                            .buildUpon()
                            .appendPath(questions.get(questionPosition).getQuestionId() +"")
                            .build();

                    Cursor questionCursor = getActivity().getContentResolver().query(
                            questionUri,
                            null,
                            null,
                            null,
                            null
                    );

                    if (questionCursor.getCount() > 0){
                        questionCursor.moveToFirst();
                        chosenAsnwer = questionCursor.getInt(
                          questionCursor.getColumnIndex(AppContract.QuestionsEntry.COLUMN_ANSWER_CHOSEN)
                        );

                    }







                    return null;
                }
            };

        }else if (id == UPDATE_ANSWER_LOADER_ID){
            return new AsyncTaskLoader<Cursor>(getActivity()) {
                @Nullable
                @Override
                public Cursor loadInBackground() {


                    int checkedId = args.getInt(CHECKED_ID);


                    Uri updateChosenUri = AppContract.QuestionsEntry.CONTENT_URI
                            .buildUpon()
                            .appendPath(UPDATE)
                            .appendPath(String.valueOf(questions.get(questionPosition).getQuestionId())).build();

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(AppContract.QuestionsEntry.COLUMN_ANSWER_CHOSEN,checkedId);
                    if (radioGroup.getChildAt(checkedId-1).getTag().toString().equals("1")){
                        contentValues.put(AppContract.QuestionsEntry.COLUMN_IS_CHOSEN_CORRECT, 1);
                    }else if (radioGroup.getChildAt(checkedId-1).getTag().toString().equals("0")){
                        contentValues.put(AppContract.QuestionsEntry.COLUMN_IS_CHOSEN_CORRECT, 0);
                    }

                    getActivity().getContentResolver().update(updateChosenUri,contentValues,null,null);



                            /*Cursor cursor = getActivity().getContentResolver().query(
                                    AppContract.QuestionsEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(questions.get(questionPosition).getQuestionId())).build(),
                                    null,null,null,null
                            );

                            if (cursor.getCount() == 1){
                                cursor.moveToFirst();
                                Log.v("string", cursor.getInt(cursor.getColumnIndex(AppContract.QuestionsEntry.COLUMN_IS_CHOSEN_CORRECT))+"");
                                Log.v("string 2", group.getChildAt(radioGroup.getCheckedRadioButtonId()-1).getTag().toString());
                            }*/


                    return null;
                }
            };
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor choicesCursor) {


        /**
         * add the choices views and handle it
         */

        if (loader.getId() == GET_CHOICES_LOADER_ID_FIRST || loader.getId() == GET_CHOICES_LOADER_ID){

            handleGetChoicesLoader(choicesCursor);
        }else if (loader.getId() == GET_CHOSEN_ANSWER_LOADER_ID || loader.getId() == GET_CHOSEN_ANSWER_LOADER_ID_FIRST){
            if (chosenAsnwer != 0){
                radioGroup.check(chosenAsnwer);
                for (int i =0; i<radioGroup.getChildCount(); i++){
                    radioGroup.getChildAt(i).setBackgroundResource(R.drawable.rounded_radio);
                    radioGroup.getChildAt(chosenAsnwer-1).setBackgroundResource(R.drawable.selected_rounded_radio);
                }

            }


            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    for (int i =0; i<group.getChildCount(); i++){
                        group.getChildAt(i).setBackgroundResource(R.drawable.rounded_radio);
                        group.getChildAt(checkedId-1).setBackgroundResource(R.drawable.selected_rounded_radio);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putInt(CHECKED_ID, checkedId);

                    if (getActivity().getSupportLoaderManager() == null){
                        getActivity().getSupportLoaderManager().initLoader(
                                UPDATE_ANSWER_LOADER_ID,
                                bundle,
                                QuizFragement.this
                        ).forceLoad();
                    }else{

                        getActivity().getSupportLoaderManager().restartLoader(
                                UPDATE_ANSWER_LOADER_ID,
                                bundle,
                                QuizFragement.this
                        ).forceLoad();

                    }



                }
            });


        }







    }

    /**
     * this method get choices cursor and convert it to radio buttons
     * and add views to radio group.
     * @param choicesCursor
     */

    public void handleGetChoicesLoader(Cursor choicesCursor){
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

            for (int i = 0; i<radioGroup.getChildCount();i++){
                radioGroup.getChildAt(i).setId(i+1);
            }



        }


        if (questionPosition == 0){
            if (getActivity().getSupportLoaderManager() == null){

                getActivity().getSupportLoaderManager().initLoader(
                        GET_CHOSEN_ANSWER_LOADER_ID_FIRST,
                        null,
                        this
                ).forceLoad();
            }else{

                getActivity().getSupportLoaderManager().restartLoader(
                        GET_CHOSEN_ANSWER_LOADER_ID_FIRST,
                        null,
                        this
                ).forceLoad();
            }
        }else if (questionPosition == 1){

            if (getActivity().getSupportLoaderManager() == null){
                getActivity().getSupportLoaderManager().initLoader(
                        GET_CHOSEN_ANSWER_LOADER_ID,
                        null,
                        this
                ).forceLoad();
            }else {
                getActivity().getSupportLoaderManager().restartLoader(
                        GET_CHOSEN_ANSWER_LOADER_ID,
                        null,
                        this
                ).forceLoad();
            }



        }else {

            getActivity().getSupportLoaderManager().restartLoader(
                    GET_CHOSEN_ANSWER_LOADER_ID,
                    null,
                    this
            ).forceLoad();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setQuestionPosition(int questionPosition) {
        this.questionPosition = questionPosition;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getQuestionPosition() {
        return questionPosition;
    }

    public void setSelectedQuiz(String selectedQuiz) {
        this.selectedQuiz = selectedQuiz;
    }

}

