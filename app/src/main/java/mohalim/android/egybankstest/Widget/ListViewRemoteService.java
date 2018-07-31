package mohalim.android.egybankstest.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mohalim.android.egybankstest.Models.UserProfile;
import mohalim.android.egybankstest.R;

public class ListViewRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ResumeWidgetFactory(this.getApplicationContext());
    }
}

class ResumeWidgetFactory implements RemoteViewsService.RemoteViewsFactory{
    private List<UserProfile> userProfiles = new ArrayList<UserProfile>();
    private Context mContext;
    private int mAppWidgetId;
    private Query resumeQuery;


    public ResumeWidgetFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {




        resumeQuery = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name").limitToFirst(10);
        resumeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userProfiles.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    UserProfile userProfile = data.getValue(UserProfile.class);
                    userProfiles.add(userProfile);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onDestroy() {
        userProfiles.clear();
    }

    @Override
    public int getCount() {
        return userProfiles.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_resume_row);

        if (userProfiles != null){
            rv.setTextViewText(R.id.widget_row_title, userProfiles.get(position).getName());
        }



        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
