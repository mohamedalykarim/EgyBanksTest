package mohalim.android.egybankstest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mohalim.android.egybankstest.Models.UserProfile;

public class ResumeActivity extends AppCompatActivity {

    TextView primaryNameTV,
            aboutMeTV, educationTV, experienceTV, certificationsTV,
             coursesTV, skillsTV, moreInfoTV,
            nameTV, birthDateTV, nationalityTV, languagesTV, phoneTV, mobileTV, emailTV, addressTV,
            aboutmeTitle, educationTitle, experienceTitle, certificationsTitle,
            coursesTitle, skillsTitle, moreInfoTitle;




    ImageView profileImage, backgroundProfile;
    View primaryLine, transparent, aboutMeLine, educationLine, experienceLine,
            certificationsLine, coursesLine, skillsLine, moreInfoLine;

    private static final String RESUMES_EXTRA = "extra_resume";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        primaryNameTV = findViewById(R.id.primary_name_tv);
        aboutMeTV = findViewById(R.id.aboutme_tv);
        educationTV = findViewById(R.id.education_tv);
        experienceTV = findViewById(R.id.experience_tv);
        certificationsTV = findViewById(R.id.certifications_tv);
        coursesTV = findViewById(R.id.courses_tv);
        skillsTV = findViewById(R.id.skills_tv);
        moreInfoTV = findViewById(R.id.more_info_tv);

        nameTV = findViewById(R.id.name_tv);
        birthDateTV = findViewById(R.id.birth_date_tv);
        nationalityTV = findViewById(R.id.nationality_tv);
        languagesTV = findViewById(R.id.languages_tv);
        phoneTV = findViewById(R.id.phone_tv);
        mobileTV = findViewById(R.id.mobile_tv);
        emailTV = findViewById(R.id.email_tv);
        addressTV = findViewById(R.id.address_tv);


        aboutmeTitle = findViewById(R.id.aboutme_title_tv);
        educationTitle = findViewById(R.id.education_title_tv);
        experienceTitle = findViewById(R.id.experience_title_tv);
        certificationsTitle = findViewById(R.id.certifications_title_tv);
        coursesTitle = findViewById(R.id.courses_title_tv);
        skillsTitle = findViewById(R.id.skills_title_tv);
        moreInfoTitle = findViewById(R.id.more_info_title_tv);


        primaryLine = findViewById(R.id.view_primary);
        aboutMeLine = findViewById(R.id.view_aboutme);
        educationLine = findViewById(R.id.view_education);
        experienceLine = findViewById(R.id.view_experience);
        certificationsLine = findViewById(R.id.view_certifications);
        coursesLine = findViewById(R.id.view_courses);
        skillsLine = findViewById(R.id.view_skills);
        moreInfoLine = findViewById(R.id.view_more_info);


        profileImage = findViewById(R.id.profile_image);
        backgroundProfile = findViewById(R.id.background_profile);
        transparent = findViewById(R.id.transparent);

        Intent intent = getIntent();
        if (intent.hasExtra(RESUMES_EXTRA)){
            UserProfile userProfile = intent.getParcelableExtra(RESUMES_EXTRA);

                primaryNameTV.setText(userProfile.getName());

                aboutMeTV.setText(userProfile.getAboutMe());

                educationTV.setText(userProfile.getEducation());

                experienceTV.setText(userProfile.getExperience());

                certificationsTV.setText(userProfile.getCertifications());

                coursesTV.setText(userProfile.getCourses());

                skillsTV.setText(userProfile.getSkills());

                moreInfoTV.setText(userProfile.getExtraInfo());

                if (!TextUtils.isEmpty(userProfile.getProfileImage())){
                    Picasso.get().load(userProfile.getProfileImage())
                            .placeholder(getResources().getDrawable(R.drawable.default_profile))
                            .into(profileImage);

                    Picasso.get().load(userProfile.getProfileImage())
                            .placeholder(getResources().getDrawable(R.drawable.default_profile))
                            .into(backgroundProfile, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Bitmap bitmap = ((BitmapDrawable)backgroundProfile.getDrawable()).getBitmap();
                                    Palette palette =  Palette.from(bitmap).generate();
                                    int backgroundColor = palette.getDarkMutedColor(Color.DKGRAY);
                                    int titleColor = palette.getMutedColor(Color.GRAY);
                                    int subtitleColor = palette.getLightMutedColor(Color.LTGRAY);

                                    backgroundColor = ColorUtils.setAlphaComponent(backgroundColor, 235);
                                    transparent.setBackgroundColor(backgroundColor);
                                    setColors(titleColor,subtitleColor);



                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });

                }else{
                    Picasso.get().load(R.drawable.default_profile).into(profileImage);
                    Picasso.get().load(R.drawable.default_profile).into(backgroundProfile);
                }

                nameTV.setText(userProfile.getName());
                emailTV.setText(userProfile.getEmail());
                birthDateTV.setText(userProfile.getBirthdate());
                nationalityTV.setText(userProfile.getNationality());
                languagesTV.setText(userProfile.getLangauge());
                phoneTV.setText(userProfile.getPhone());
                mobileTV.setText(userProfile.getMobile());
                addressTV.setText(userProfile.getLocation());


        }

    }

    public void  setColors(int titleColor, int subTitleColor){
                primaryNameTV.setTextColor(subTitleColor);

                aboutMeTV.setTextColor(titleColor);
                educationTV.setTextColor(titleColor);
                experienceTV.setTextColor(titleColor);
                certificationsTV.setTextColor(titleColor);
                coursesTV.setTextColor(titleColor);
                skillsTV.setTextColor(titleColor);
                moreInfoTV.setTextColor(titleColor);

        primaryLine.setBackgroundColor(subTitleColor);
        aboutMeLine.setBackgroundColor(subTitleColor);
        educationLine.setBackgroundColor(subTitleColor);
        experienceLine.setBackgroundColor(subTitleColor);
        certificationsLine.setBackgroundColor(subTitleColor);
        coursesLine.setBackgroundColor(subTitleColor);
        skillsLine.setBackgroundColor(subTitleColor);
        moreInfoLine.setBackgroundColor(subTitleColor);


        aboutmeTitle.setTextColor(subTitleColor);
        educationTitle.setTextColor(subTitleColor);
        experienceTitle.setTextColor(subTitleColor);
        certificationsTitle.setTextColor(subTitleColor);
        coursesTitle.setTextColor(subTitleColor);
        skillsTitle.setTextColor(subTitleColor);
        moreInfoTitle.setTextColor(subTitleColor);

    }
}
