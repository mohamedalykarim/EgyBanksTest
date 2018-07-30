package mohalim.android.egybankstest.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.BottomNavigationView;

public class AppSQLiteHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "egytest.db";
    private static final int DATABASE_VERSION = 1;

    private static final String QUESTION_CREATE_TABLE = "CREATE TABLE "
            + AppContract.QuestionsEntry.TABLE_NAME
            + " ("
            + AppContract.QuestionsEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AppContract.QuestionsEntry.COLUMN_QUESTION_CATEGORY
            + " TEXT NOT NULL, "
            + AppContract.QuestionsEntry.COLUMN_QUESTION_TEXT
            + " TEXT NOT NULL, "
            + AppContract.QuestionsEntry.COLUMN_QUESTION_TYPE
            + " TEXT NOT NULL,"
            + AppContract.QuestionsEntry.COLUMN_SESSION_ID
            + " INTEGER NOT NULL,"
            + AppContract.QuestionsEntry.COLUMN_ANSWER_CHOSEN
            + " INTEGER"
            + ");";

    private static final String QUESTION_DROP_TABLE = "DROP TABLE IF EXISTS "
            + AppContract.QuestionsEntry.TABLE_NAME;

    private static final String CHOICES_CREATE_TABLE = "CREATE TABLE "
            + AppContract.ChoiceEntry.TABLE_NAME
            + " ("
            + AppContract.ChoiceEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AppContract.ChoiceEntry.COLUMN_CHOICE_TEXT
            + " TEXT NOT NULL,"
            + AppContract.ChoiceEntry.COLUMN_QUESTION_ID
            + " INTEGER NOT NULL,"
            + AppContract.ChoiceEntry.COLUMN_IS_TRUE
            + " BOOLEAN NOT NULL DEFAULT 0"
            + ");";

    private static final String CHOICES_DROP_TABLE = "DROP TABLE IF EXISTS "
            + AppContract.ChoiceEntry.TABLE_NAME;

    private static final String SESSION_CREATE_TABLE = "CREATE TABLE "
            + AppContract.SessionEntry.TABLE_NAME
            + " ("
            + AppContract.SessionEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AppContract.SessionEntry.COLUMN_SESSION_CATEGORY
            + " TEXT NOT NULL,"
            + AppContract.SessionEntry.COLUMN_CURRENT_QUESTION
            + " INTEGER NOT NULL DEFAULT 0"
            + ");";

    private static final String SESSION_DROP_TABLE = "DROP TABLE IF EXISTS "
            + AppContract.SessionEntry.TABLE_NAME;


    public AppSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUESTION_CREATE_TABLE);
        db.execSQL(CHOICES_CREATE_TABLE);
        db.execSQL(SESSION_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUESTION_DROP_TABLE);
        db.execSQL(CHOICES_DROP_TABLE);
        db.execSQL(SESSION_DROP_TABLE);
        onCreate(db);
    }
}
