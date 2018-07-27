package mohalim.android.egybankstest.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AppContentProvider extends ContentProvider {

    private AppSQLiteHelper appSQLiteHelper;

    public static final int QUESTIONS = 100;
    public static final int CHOICES = 200;
    public static final int CHOICES_WITH_QUESTION_ID = 201;

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
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
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
        return uriMatcher;
    }
}
