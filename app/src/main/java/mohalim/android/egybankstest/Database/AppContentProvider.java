package mohalim.android.egybankstest.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AppContentProvider extends ContentProvider {

    private AppSQLiteHelper appSQLiteHelper;

    public static final int QUESTIONS = 100;


    public static final int CHOICES = 200;
    public static final int CHOICES_WITH_QUESTION_ID = 201;

    public static final int SESSION = 300;
    public static final int SESSION_FOR_CATEGORY = 301;


    private static final UriMatcher myUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        Context context = getContext();
        appSQLiteHelper = new AppSQLiteHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = appSQLiteHelper.getReadableDatabase();
        int match = myUriMatcher.match(uri);

        Cursor returnCursor;

        switch (match){
            case SESSION:
                returnCursor = db.query(
                  AppContract.SessionEntry.TABLE_NAME,
                  projection,
                  selection,
                  selectionArgs,
                  null,
                  null,
                  sortOrder
                );

                break;

            case SESSION_FOR_CATEGORY:
                String category = uri.getPathSegments().get(1);
                String categorySelection = AppContract.SessionEntry.COLUMN_SESSION_CATEGORY + "=?";
                String[] categorySelectionArgs = new String[]{category};


                returnCursor = db.query(
                        AppContract.SessionEntry.TABLE_NAME,
                        projection,
                        categorySelection,
                        categorySelectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;


            case QUESTIONS:
                returnCursor = db.query(
                        AppContract.QuestionsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case CHOICES:
                returnCursor = db.query(
                        AppContract.ChoiceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case CHOICES_WITH_QUESTION_ID:

                String questionId = uri.getPathSegments().get(1);
                String questionIDSelection = AppContract.ChoiceEntry.COLUMN_QUESTION_ID + "=?";
                String[] questionIDSelectionArgs = new String[]{questionId};


                returnCursor = db.query(
                        AppContract.ChoiceEntry.TABLE_NAME,
                        projection,
                        questionIDSelection,
                        questionIDSelectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

            default:
                throw new UnsupportedOperationException("Wrong URI : "+uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = appSQLiteHelper.getWritableDatabase();
        int match = myUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case SESSION:
                long sessionID = db.insert(AppContract.SessionEntry.TABLE_NAME, null, values);
                if (sessionID > 0){
                    returnUri = ContentUris.withAppendedId(AppContract.SessionEntry.CONTENT_URI,sessionID);
                }else{
                    throw new SQLException("Error: Failed to insert row "+ uri);
                }
                break;


            case QUESTIONS:
                long questionId = db.insert(AppContract.QuestionsEntry.TABLE_NAME, null, values);
                if (questionId > 0){
                    returnUri = ContentUris.withAppendedId(AppContract.SessionEntry.CONTENT_URI,questionId);
                }else{
                    throw new SQLException("Error: Failed to insert row "+ uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Wrong URI : "+ uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = appSQLiteHelper.getWritableDatabase();
        int match = myUriMatcher.match(uri);
        int rowsinserted = 0;

        switch (match){

            case CHOICES:
                db.beginTransaction();

                try{
                    for (ContentValues contentValues : values){
                        long _id  = db.insert(AppContract.ChoiceEntry.TABLE_NAME,null,contentValues);
                        if (_id != -1){
                            rowsinserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }

                break;

            default:
                super.bulkInsert(uri,values);

        }

        if (rowsinserted > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsinserted;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = appSQLiteHelper.getWritableDatabase();
        int match = myUriMatcher.match(uri);

        int rowDeleted = 0;

        switch (match){
            case SESSION:
                rowDeleted = db.delete(
                                AppContract.SessionEntry.TABLE_NAME,
                                selection,
                                selectionArgs
                              );


                break;

            case QUESTIONS:
                rowDeleted = db.delete(
                        AppContract.QuestionsEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );


                break;

            case CHOICES:
                rowDeleted = db.delete(
                        AppContract.ChoiceEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );


                break;

            default:
                return 0;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AppContract.AUTHORITY,AppContract.PATH_QUESTIONS,QUESTIONS);
        uriMatcher.addURI(AppContract.AUTHORITY,AppContract.PATH_CHOICES,CHOICES);
        uriMatcher.addURI(AppContract.AUTHORITY,AppContract.PATH_CHOICES + "/#",CHOICES_WITH_QUESTION_ID);
        uriMatcher.addURI(AppContract.AUTHORITY,AppContract.PATH_SESSION,SESSION);
        uriMatcher.addURI(AppContract.AUTHORITY,AppContract.PATH_SESSION + "/*",SESSION_FOR_CATEGORY);
        return uriMatcher;
    }
}
