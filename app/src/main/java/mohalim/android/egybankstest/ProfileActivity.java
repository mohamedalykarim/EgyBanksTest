package mohalim.android.egybankstest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Time;

import mohalim.android.egybankstest.Fragments.DatePickerFragment;
import mohalim.android.egybankstest.Models.UserProfile;
import mohalim.android.egybankstest.Utils.ImageUtils;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 125;
    EditText nameET, aboutMeET, experienceET, educationET,
             skillsET, certificationET, coursesET, langaugesET,
             nationalityET, phoneET, mobileET, addressET,
             moreInfoET;

    Button datePickerButton;
    ImageView profileImage;
    FrameLayout updateProfileImageLoading, haveProfile;

    DatabaseReference userReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseStorage storage;


    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameET = findViewById(R.id.et_name);
        aboutMeET = findViewById(R.id.et_about_me);
        experienceET = findViewById(R.id.et_experience);
        educationET = findViewById(R.id.et_education);
        skillsET = findViewById(R.id.et_skills);
        certificationET = findViewById(R.id.et_certifications);
        coursesET = findViewById(R.id.et_courses);
        langaugesET = findViewById(R.id.et_language);
        nationalityET = findViewById(R.id.et_nationality);
        phoneET = findViewById(R.id.et_phone);
        mobileET = findViewById(R.id.et_mobile);
        addressET = findViewById(R.id.et_address);
        moreInfoET = findViewById(R.id.et_more_info);

        profileImage = findViewById(R.id.profile_image);
        datePickerButton = findViewById(R.id.birth_date_btn);

        updateProfileImageLoading = findViewById(R.id.loading_profile_image);
        haveProfile = findViewById(R.id.have_profile);

        haveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,SignUpActivity.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        if (mAuth.getCurrentUser() !=null){
           retrieveData();
           haveProfile.setVisibility(View.GONE);
        }else{
            haveProfile.setVisibility(View.VISIBLE);
        }



    }


    public void retrieveData(){
        userReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userProfile = dataSnapshot.getValue(UserProfile.class);
                nameET.setText(userProfile.getName());
                aboutMeET.setText(userProfile.getAboutMe());
                experienceET.setText(userProfile.getExperience());
                educationET.setText(userProfile.getEducation());
                skillsET.setText(userProfile.getSkills());
                certificationET.setText(userProfile.getCertifications());
                coursesET.setText(userProfile.getCourses());
                langaugesET.setText(userProfile.getLangauge());
                nationalityET.setText(userProfile.getNationality());
                phoneET.setText(userProfile.getPhone());
                mobileET.setText(userProfile.getMobile());
                addressET.setText(userProfile.getLocation());
                moreInfoET.setText(userProfile.getExtraInfo());
                if (!TextUtils.isEmpty(userProfile.getProfileImage()))
                Picasso.get().load(userProfile.getProfileImage()).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUser(View view){
        String name = nameET.getText().toString();
        String aboutMe = aboutMeET.getText().toString();
        String experience = experienceET.getText().toString();
        String education = educationET.getText().toString();
        String skills = skillsET.getText().toString();
        String certifications = certificationET.getText().toString();
        String courses = coursesET.getText().toString();
        String languages = langaugesET.getText().toString();
        String nationality = nationalityET.getText().toString();
        String phone = phoneET.getText().toString();
        String mobile = mobileET.getText().toString();
        String address = addressET.getText().toString();
        String moreInfo = moreInfoET.getText().toString();

        if (userProfile != null){
            userProfile.setName(name);
            userProfile.setAboutMe(aboutMe);
            userProfile.setExperience(experience);
            userProfile.setEducation(education);
            userProfile.setSkills(skills);
            userProfile.setCertifications(certifications);
            userProfile.setCourses(courses);
            userProfile.setLangauge(languages);
            userProfile.setNationality(nationality);
            userProfile.setPhone(phone);
            userProfile.setMobile(mobile);
            userProfile.setLocation(address);
            userProfile.setExtraInfo(moreInfo);

            userReference.setValue(userProfile)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "Update Completed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }

    }

    public void updateImageProfile(View view){
        updateProfileImageLoading.setVisibility(View.VISIBLE);
        Intent i=new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,REQUEST_CODE);

    }

    public void onClickDatePicker(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Uri uri= data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                byte[] imageData = ImageUtils.getByteFromBitmap(bitmap,100);

                final StorageReference filepath = storage.getReference("Egy_Banks_App").child("profile").child(user.getUid());

                filepath.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // On Uploading Image URL
                        filepath.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(final Uri newUri) {

                                        // On Getting Uploaded Image URL

                                        Picasso.get().load(newUri)
                                                .placeholder(getResources().getDrawable(R.drawable.default_profile))
                                                .into(profileImage);

                                        updateProfileImageLoading.setVisibility(View.GONE);
                                        userReference.child("profileImage").setValue(newUri.toString());


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                filepath.delete();
                                updateProfileImageLoading.setVisibility(View.GONE);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        updateProfileImageLoading.setVisibility(View.GONE);
                    }
                });

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }



        }else {
            updateProfileImageLoading.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAuth.getCurrentUser() != null){
            DatabaseReference birthdateRef = userReference.child("birthdate");
            birthdateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    datePickerButton.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



    }
}
