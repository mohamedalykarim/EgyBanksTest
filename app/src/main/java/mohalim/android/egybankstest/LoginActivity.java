package mohalim.android.egybankstest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailET, passwordET;
    Button loginBtn;

    private final int STATUS_EMAIL_EMPTY = 1;
    private final int STATUS_PASSWORD_EMPTY = 2;

    ProgressBar progressBar;


    ArrayList<Integer> statuses;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        progressBar = findViewById(R.id.login_progress);

        statuses = new ArrayList<>();

    }

    public ArrayList<Integer> validate(){
        statuses.clear();
        if (TextUtils.isEmpty(emailET.getText().toString())){
            statuses.add(STATUS_EMAIL_EMPTY);
        }

        if (TextUtils.isEmpty(passwordET.getText().toString())){
            statuses.add(STATUS_PASSWORD_EMPTY);
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

        if(alert.length() > 2){
            alert = alert.substring(0,alert.length()-1);
        }


        return alert;
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn){
            String validateString = getValidateString();
            if (!TextUtils.isEmpty(validateString))
            Toast.makeText(this, ""+validateString, Toast.LENGTH_SHORT).show();


            if (TextUtils.isEmpty(validateString)){
                progressBar.setVisibility(View.VISIBLE);
                loginBtn.setEnabled(false);


                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()){
                            finishAfterTransition();
                        }else{
                            Toast.makeText(LoginActivity.this, R.string.need_credintial,
                                    Toast.LENGTH_SHORT).show();
                            loginBtn.setEnabled(true);
                        }

                    }
                });




            }
        }
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
