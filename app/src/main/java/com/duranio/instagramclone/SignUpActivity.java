package com.duranio.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private EditText userEditText, emailEditText, pswEditText;
    private Button logButton, signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        userEditText = findViewById(R.id.userEditText);
        emailEditText = findViewById(R.id.emailEditText);
        pswEditText = findViewById(R.id.pswEditText);
        logButton = findViewById(R.id.logButton);
        signButton = findViewById(R.id.signButton);

        if(ParseUser.getCurrentUser()!=null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        pswEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    signButton.performClick();
                }
                return false;
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailEditText.getText().toString().trim().isEmpty() ||
                        userEditText.getText().toString().trim().isEmpty()||
                        pswEditText.getText().toString().trim().isEmpty()){
                    FancyToast.makeText(SignUpActivity.this,
                            "Empty fields not Allowed",
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, true).show();
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Signing up...");
                progressDialog.show();


                ParseUser user = new ParseUser();
                user.setUsername(userEditText.getText().toString().trim());
                user.setPassword(pswEditText.getText().toString().trim());
                user.setEmail(emailEditText.getText().toString().trim());


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SignUpActivity.this,"User signed up", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                            transitionToSocialMediaActivity();
                        } else {
                            FancyToast.makeText(SignUpActivity.this,"Error", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }

    public void rootLayoutTapped(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){

        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(SignUpActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}