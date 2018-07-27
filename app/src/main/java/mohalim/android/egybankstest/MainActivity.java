package mohalim.android.egybankstest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mohalim.android.egybankstest.Adapters.MainMenuRecyclerAdapter;
import mohalim.android.egybankstest.Adapters.MainResmueRecyclerViewAdapter;
import mohalim.android.egybankstest.Models.MainMenuItem;
import mohalim.android.egybankstest.Models.UserProfile;

public class MainActivity extends AppCompatActivity
        implements MainResmueRecyclerViewAdapter.MainResumeItemClickListener,
                   MainMenuRecyclerAdapter.MainMenuClickListener{

    private static final String RESUMES_EXTRA = "extra_resume";
    RecyclerView resumeRecycler, mainMenuRecycler;
    LinearLayoutManager resumeLayoutManager, mainMenuLayoutManager;
    MainResmueRecyclerViewAdapter mainResmueRecyclerViewAdapter;
    MainMenuRecyclerAdapter mainMenuRecyclerAdapter;
    ArrayList<UserProfile> resumes;
    ArrayList<MainMenuItem> menuItems;

    NavigationView navigationView;
    DrawerLayout mDrawerLayout;

    FirebaseAuth mAuth;
    FirebaseUser user;
    Query resumeQuery;

    ConstraintLayout menuLoginContainer, menuSignUpContainer, menuLogoutContainer;
    TextView usernameTV, aboutmeTV;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * handle DrawerLayout
         */

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);



        /**
         * hadling navigation
         */

        menuLoginContainer = findViewById(R.id.login_container);
        menuLogoutContainer = findViewById(R.id.logout_container);
        menuSignUpContainer = findViewById(R.id.signup_container);
        usernameTV = findViewById(R.id.username_tv);
        aboutmeTV = findViewById(R.id.aboutme_tv);
        profileImageView = findViewById(R.id.profile_image);

        menuLoginContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        menuSignUpContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        menuLogoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                handleMenuVisiblity();
                usernameTV.setText(getResources().getString(R.string.welcome_guest));
                profileImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_profile));

            }
        });





        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        /**
         * handle toolbar
         */
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);







        /**
         * Handle ResumeActivity Recycler View
         */

        resumeRecycler = findViewById(R.id.resume_recycler_view);
        resumeLayoutManager = new LinearLayoutManager(this);
        resumeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        resumes = new ArrayList<>();
        mainResmueRecyclerViewAdapter = new MainResmueRecyclerViewAdapter(this,resumes,this);
        resumeRecycler.setLayoutManager(resumeLayoutManager);
        resumeRecycler.setAdapter(mainResmueRecyclerViewAdapter);

        getResumes();




        /**
         * Handle ResumeActivity Recycler View
         */
        mainMenuRecycler = findViewById(R.id.main_menu_recycler);
        mainMenuLayoutManager = new LinearLayoutManager(this);
        menuItems = new ArrayList<>();
        mainMenuRecyclerAdapter = new MainMenuRecyclerAdapter(this,menuItems, this);
        mainMenuRecycler.setLayoutManager(mainMenuLayoutManager);
        mainMenuRecycler.setAdapter(mainMenuRecyclerAdapter);

        menuItems.add(new MainMenuItem(R.drawable.alahly,"Alahly Test Bank", "You Can Simulate Al ahly Bank Test Here"));
        menuItems.add(new MainMenuItem(R.drawable.banquemisr ,"BanqueMisr Test Bank", "You Can Simulate Al ahly Bank Test Here"));
        menuItems.add(new MainMenuItem(R.drawable.profile,"My Profile", "You Can Add And Modify Your Resume Here"));


        if(mAuth.getCurrentUser() != null)
        getUserData();

    }


    public void handleMenuVisiblity(){
        if (mAuth.getCurrentUser() != null){
            menuLoginContainer.setVisibility(View.GONE);
            menuSignUpContainer.setVisibility(View.GONE);
            menuLogoutContainer.setVisibility(View.VISIBLE);

        }else{
            menuLoginContainer.setVisibility(View.VISIBLE);
            menuSignUpContainer.setVisibility(View.VISIBLE);
            menuLogoutContainer.setVisibility(View.GONE);

        }

    }

    public void getResumes() {
        resumeQuery = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name");
        resumeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resumes.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    UserProfile userProfile = data.getValue(UserProfile.class);
                    resumes.add(userProfile);
                    mainResmueRecyclerViewAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserData(){

        if (mAuth.getCurrentUser() != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    usernameTV.setText(userProfile.getName());
                    if (!TextUtils.isEmpty(userProfile.getProfileImage())){
                        Picasso.get().load(Uri.parse(userProfile.getProfileImage())).into(profileImageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


        

    }




    @Override
    protected void onResume() {
        super.onResume();
        handleMenuVisiblity();
        getResumes();

        if(mAuth.getCurrentUser() != null)
            getUserData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMenuItemClickListener(int position) {
        if (position == 0){
            startActivity(new Intent(MainActivity.this, AlAhlyActivity.class));
        }else if(position == 1){
            startActivity(new Intent(MainActivity.this, BanqueMisrActivity.class));
        }else if (position == 2){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }

    }

    @Override
    public void onResumeItemClickListener(int position) {
        Intent intent = new Intent(MainActivity.this,ResumeActivity.class);
        UserProfile resume = resumes.get(position);
        intent.putExtra(RESUMES_EXTRA, resume);
        startActivity(intent);
    }



}
