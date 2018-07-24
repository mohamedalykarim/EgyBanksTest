package mohalim.android.egybankstest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mohalim.android.egybankstest.Models.UserProfile;
import mohalim.android.egybankstest.R;

public class MainResmueRecyclerViewAdapter extends RecyclerView.Adapter<MainResmueRecyclerViewAdapter.MainResumeViewHolder> {
    Context mContext;
    ArrayList<UserProfile> resumes;

    final private MainResumeItemClickListener mainRecyclerItemClickListener;

    public MainResmueRecyclerViewAdapter(Context mContext, ArrayList<UserProfile> resumes, MainResumeItemClickListener listener) {
        this.mContext = mContext;
        this.resumes = resumes;
        this.mainRecyclerItemClickListener = listener;
    }

    @NonNull
    @Override
    public MainResumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_resume_item, parent, false);

        return new MainResumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainResumeViewHolder holder, int position) {
        UserProfile resume = resumes.get(position);
        if (!TextUtils.isEmpty(resume.getProfileImage())){
            Picasso.get().load(resume.getProfileImage()).into(holder.profileImage);
        }else{
            Picasso.get().load(R.drawable.default_profile).into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return resumes.size();
    }

    class MainResumeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView profileImage;

        public MainResumeViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mainRecyclerItemClickListener.onResumeItemClickListener(position);
        }
    }


    public interface MainResumeItemClickListener{
        void onResumeItemClickListener(int position);
    }
}
