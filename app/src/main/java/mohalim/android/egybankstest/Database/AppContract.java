package mohalim.android.egybankstest.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class AppContract {
    public static final String AUTHORITY = "mohalim.android.egybankstest";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_QUESTIONS = "questions";
    public static final String PATH_CHOICES = "choices";
    public static final String PATH_SESSION = "session";

    public static class QuestionsEntry implements BaseColumns{

        public static final Uri CONTENT_URI
                = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_QUESTIONS)
                .build();

        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_QUESTION_CATEGORY = "question_category";
        public static final String COLUMN_QUESTION_TEXT = "question_text";
        public static final String COLUMN_QUESTION_TYPE = "question_type";
        public static final String COLUMN_SESSION_ID = "session_id";
        public static final String COLUMN_ANSWER_CHOSEN = "answer_chosen";
        public static final String COLUMN_IS_CHOSEN_CORRECT = "is_chosen_correct";
    }

    public static class ChoiceEntry implements BaseColumns{

        public static final Uri CONTENT_URI
                = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CHOICES)
                .build();

        public static final String TABLE_NAME = "choices";
        public static final String COLUMN_CHOICE_TEXT= "choice_text";
        public static final String COLUMN_IS_TRUE= "is_correct";
        public static final String COLUMN_QUESTION_ID= "question_id";
    }

    public static class SessionEntry implements BaseColumns{
        public static final Uri CONTENT_URI
                = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SESSION)
                .build();
        public static final String TABLE_NAME = "sessions";
        public static final String COLUMN_SESSION_CATEGORY= "session_category";
        public static final String COLUMN_CURRENT_QUESTION= "current_question";


    }
}
