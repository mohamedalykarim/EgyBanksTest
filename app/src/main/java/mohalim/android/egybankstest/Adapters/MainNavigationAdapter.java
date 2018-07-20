package mohalim.android.egybankstest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import mohalim.android.egybankstest.Models.MenuItem;
import mohalim.android.egybankstest.R;

public class MainNavigationAdapter extends ArrayAdapter<MenuItem> {
    Context mContext;

    public MainNavigationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.navigation_menu_item, parent, false);

        return view;



    }
}


