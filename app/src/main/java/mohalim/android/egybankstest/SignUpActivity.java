package mohalim.android.egybankstest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mohalim.android.egybankstest.Models.UserProfile;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailET, passwordET, confirmPasswordET, nameET;
    Button signUpBtn;



    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;




    private final int STATUS_EMAIL_EMPTY = 1;
    private final int STATUS_PASSWORD_EMPTY = 2;
    private final int STATUS_CONFIRM_PASSWORD_EMPTY = 3;
    private final int STATUS_NAME_EMPTY = 4;
    private final int STATUS_PASSWORD_NOT_MATCH = 5;
    private final int STATUS_NOT_EMAIL = 6;


    ArrayList<Integer> statuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /**
         * Initiate the ui element
         */
        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
        confirmPasswordET = findViewById(R.id.et_confirm_password);
        nameET = findViewById(R.id.et_name);
        signUpBtn = findViewById(R.id.signup_button);
        signUpBtn.setOnClickListener(this);


        statuses = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        emailET.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

    }

    public ArrayList<Integer> validate(){
        statuses.clear();
        if (TextUtils.isEmpty(emailET.getText().toString())){
            statuses.add(STATUS_EMAIL_EMPTY);
        }

        if (TextUtils.isEmpty(passwordET.getText().toString())){
            statuses.add(STATUS_PASSWORD_EMPTY);
        }

        if (TextUtils.isEmpty(confirmPasswordET.getText().toString())){
            statuses.add(STATUS_CONFIRM_PASSWORD_EMPTY);
        }

        if (TextUtils.isEmpty(nameET.getText().toString())){
            statuses.add(STATUS_NAME_EMPTY);
        }

        if (!passwordET.getText().toString().equals(confirmPasswordET.getText().toString())){
            statuses.add(STATUS_PASSWORD_NOT_MATCH);
        }

        if (!isValidEmail(emailET.getText().toString(), emailET.getEditableText())){
            if (!TextUtils.isEmpty(emailET.getText().toString())){
                statuses.add(STATUS_NOT_EMAIL);
            }
        }


        return statuses;

    }

    private String getValidateString(){
        ArrayList<Integer> validate = validate();
        String alert = "";
        if (validate.contains(STATUS_EMAIL_EMPTY)){
            alert = alert+ getResources().getString(R.string.status_email_empty)+"\n";
        }

        if (validate.contains(STATUS_PASSWORD_EMPTY)){
            alert = alert+ getResources().getString(R.string.status_password_empty)+"\n";
        }

        if (validate.contains(STATUS_CONFIRM_PASSWORD_EMPTY)){
            alert = alert+ getResources().getString(R.string.status_confirm_password_empty)+"\n";
        }

        if (validate.contains(STATUS_NAME_EMPTY)){
            alert = alert+ getResources().getString(R.string.status_name_empty)+"\n";
        }

        if (validate.contains(STATUS_PASSWORD_NOT_MATCH)){
            alert = alert+ getResources().getString(R.string.status_password_not_match)+"\n";
        }

        if (validate.contains(STATUS_NOT_EMAIL)){
            alert = alert+ getResources().getString(R.string.status_not_email)+"\n";
        }

        if(alert.length() > 2){
            alert = alert.substring(0,alert.length()-1);
        }


        return alert;
    }


    @Override
    public void onClick(View v) {
        if (v == signUpBtn){
            String validateString = getValidateString();
            if (!TextUtils.isEmpty(validateString))
            Toast.makeText(this, ""+validateString, Toast.LENGTH_SHORT).show();
            if (TextUtils.isEmpty(validateString)){
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                final String name = nameET.getText().toString();



                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // create user with email and password
                        if (task.isSuccessful()){
                            final FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // update the user name
                                    if (task.isSuccessful()){
                                        mDatabase.child("users").child(user.getUid()).setValue(
                                                new UserProfile("",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "")
                                        )
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //add user profile to database
                                                if (task.isSuccessful()){
                                                    finish();
                                                }else {
                                                    Toast.makeText(SignUpActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else {
                                        Toast.makeText(SignUpActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(SignUpActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }

    public static boolean isValidEmail(String email, Editable editable) {
        final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(EMAIL_PATTERN) && editable.length() > 0)
        {return true;}
        else
        {return false;}
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Toast.makeText(this, getResources().getString(R.string.you_already_signed_in), Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
