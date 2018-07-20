package mohalim.android.egybankstest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mohalim.android.egybankstest.Models.Resume;
import mohalim.android.egybankstest.R;

public class MainResmueRecyclerViewAdapter extends RecyclerView.Adapter<MainResmueRecyclerViewAdapter.MainResumeViewHolder> {
    Context mContext;
    ArrayList<Resume> resumes;

    public MainResmueRecyclerViewAdapter(Context mContext, ArrayList<Resume> resumes) {
        this.mContext = mContext;
        this.resumes = resumes;
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
        Resume resume = resumes.get(position);
        Picasso.get().load(resume.getProfileImage()).into(holder.profileImage);
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return resumes.size();
    }

    class MainResumeViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;

        public MainResumeViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}
