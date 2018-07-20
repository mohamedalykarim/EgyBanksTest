package mohalim.android.egybankstest;

import android.content.Intent;
import android.opengl.Visibility;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import mohalim.android.egybankstest.Adapters.MainMenuRecyclerAdapter;
import mohalim.android.egybankstest.Adapters.MainResmueRecyclerViewAdapter;
import mohalim.android.egybankstest.Models.MainMenuItem;
import mohalim.android.egybankstest.Models.Resume;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView resumeRecycler, mainMenuRecycler;
    LinearLayoutManager resumeLayoutManager, mainMenuLayoutManager;
    MainResmueRecyclerViewAdapter mainResmueRecyclerViewAdapter;
    MainMenuRecyclerAdapter mainMenuRecyclerAdapter;
    ArrayList<Resume> resumes;
    ArrayList<MainMenuItem> menuItems;

    NavigationView navigationView;
    DrawerLayout mDrawerLayout;

    FirebaseAuth mAuth;
    FirebaseUser user;

    ConstraintLayout menuLoginContainer, menuSignUpContainer, menuLogoutContainer;
    TextView usernameTV, aboutmeTV;

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

            }
        });

        updateUserInfoOnNavigation();







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
         * Handle Resume Recycler View
         */

        resumeRecycler = findViewById(R.id.resume_recycler_view);
        resumeLayoutManager = new LinearLayoutManager(this);
        resumeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        resumes = new ArrayList<>();
        mainResmueRecyclerViewAdapter = new MainResmueRecyclerViewAdapter(this,resumes);
        resumeRecycler.setLayoutManager(resumeLayoutManager);
        resumeRecycler.setAdapter(mainResmueRecyclerViewAdapter);

        resumes.add(new Resume("http://mhalabs.org/wp-content/uploads/upme/1451456913_brodie.jpg"));
        resumes.add(new Resume("http://www.attractivepartners.co.uk/wp-content/uploads/2017/06/profile.jpg"));
        resumes.add(new Resume("https://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg"));
        resumes.add(new Resume("https://static.makeuseof.com/wp-content/uploads/2015/11/perfect-profile-picture-all-about-face.jpg"));
        resumes.add(new Resume("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTapbRG10GtdPYt7x9l2KywU8yr80Os7uLCcYfU3hzpDkYFmYiqcA"));
        resumes.add(new Resume("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDWzGmDHQFB5vv8iB9uKYplW4mY4AZXsZVXrBpRFMogfpPiruWuw"));

        mainResmueRecyclerViewAdapter.notifyDataSetChanged();

        /**
         * Handle Resume Recycler View
         */
        mainMenuRecycler = findViewById(R.id.main_menu_recycler);
        mainMenuLayoutManager = new LinearLayoutManager(this);
        menuItems = new ArrayList<>();
        mainMenuRecyclerAdapter = new MainMenuRecyclerAdapter(this,menuItems);
        mainMenuRecycler.setLayoutManager(mainMenuLayoutManager);
        mainMenuRecycler.setAdapter(mainMenuRecyclerAdapter);

        menuItems.add(new MainMenuItem(R.drawable.alahly,"Alahly Test Bank", "You can simulate Al ahly Bank Test Here"));
        menuItems.add(new MainMenuItem(R.drawable.alahly,"BanqueMisr Test Bank", "You can simulate Al ahly Bank Test Here"));
        menuItems.add(new MainMenuItem(R.drawable.alahly,"Resume", "You can add and modify you resume here"));


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

    private void updateUserInfoOnNavigation(){
        if (user != null){
            usernameTV.setText(user.getDisplayName());
        }

    }



    @Override
    public void onClick(View v) {

    }



    @Override
    protected void onResume() {
        super.onResume();
        handleMenuVisiblity();
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
}
