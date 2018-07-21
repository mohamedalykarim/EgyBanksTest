package mohalim.android.egybankstest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mohalim.android.egybankstest.Models.MainMenuItem;
import mohalim.android.egybankstest.R;

public class MainMenuRecyclerAdapter extends RecyclerView.Adapter<MainMenuRecyclerAdapter.MainMenuViewHolder> {
    Context mContext;
    ArrayList<MainMenuItem> menuItems;
    MainMenuClickListener mainMenuClickListener;

    public MainMenuRecyclerAdapter(Context mContext, ArrayList<MainMenuItem> itemArrayAdapter, MainMenuClickListener listener) {
        this.mContext = mContext;
        this.menuItems = itemArrayAdapter;
        this.mainMenuClickListener = listener;
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_menu_item,parent,false);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuViewHolder holder, int position) {
        holder.bind(
                menuItems.get(position).getImageIcon(),
                menuItems.get(position).getTitle(),
                menuItems.get(position).getSubTitle()
        );
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MainMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iconImage;
        TextView titleTV, subTitleTV;

        public MainMenuViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iconImage = itemView.findViewById(R.id.icon_image_main_menu);
            titleTV = itemView.findViewById(R.id.title_main_menu);
            subTitleTV = itemView.findViewById(R.id.subtitle_main_menu);

        }

        public void bind(int resource, String title, String subTitle){
            iconImage.setImageResource(resource);
            titleTV.setText(title);
            subTitleTV.setText(subTitle);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mainMenuClickListener.onMenuItemClickListener(position);
        }
    }

    public interface MainMenuClickListener{
        void onMenuItemClickListener(int position);
    }
}
