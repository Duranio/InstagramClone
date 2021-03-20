package com.duranio.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private TextView saveTextView;
    private EditText nameEditText, punchSpeedEditText, punchPowerEditText, kickSpeedEditText, kickPowerEditText;
    private TextView dataTextView;
    private Button getAllDataButton;
    private String allKickBoxers;
    private Button toLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        saveTextView = findViewById(R.id.saveTextView);
        nameEditText = findViewById(R.id.nameEditText);
        punchSpeedEditText = findViewById(R.id.punchSpeedEditText);
        punchPowerEditText = findViewById(R.id.punchPowerEditText);
        kickSpeedEditText = findViewById(R.id.kickSpeedEditText);
        kickPowerEditText = findViewById(R.id.kickPowerEditText);
        dataTextView = findViewById(R.id.dataTextView);
        getAllDataButton = findViewById(R.id.getAllDataButton);
        toLoginButton = findViewById(R.id.toLoginButton);

        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject kickBoxer = new ParseObject("KickBoxer");
                kickBoxer.put("name", nameEditText.getText().toString());
                try {
                    kickBoxer.put("punchSpeed", Integer.parseInt(punchSpeedEditText.getText().toString()));
                    kickBoxer.put("punchPower", Integer.parseInt(punchPowerEditText.getText().toString()));
                    kickBoxer.put("kickSpeed", Integer.parseInt(kickSpeedEditText.getText().toString()));
                    kickBoxer.put("kickPower", Integer.parseInt(kickPowerEditText.getText().toString()));
                }catch (NumberFormatException e){
                    FancyToast.makeText(SignUpActivity.this,"value not allowed",FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
                }
                kickBoxer.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){
                            FancyToast.makeText(SignUpActivity.this,"KickBoxer saved",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            nameEditText.setText("");
                            punchPowerEditText.setText("");
                            punchSpeedEditText.setText("");
                            kickPowerEditText.setText("");
                            kickSpeedEditText.setText("");
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dataTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("tbgm6E5AQT", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e == null){
                            dataTextView.setText(object.get("name").toString());
                        }
                    }
                });
            }
        });

        getAllDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(objects!=null&&objects.size()>0&&e==null){
                            FancyToast.makeText(SignUpActivity.this,"Data loaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            for(ParseObject obj: objects){
                                allKickBoxers += obj.get("name").toString() + " ";
                            }
                            dataTextView.setText(allKickBoxers);
                        } else {
                            FancyToast.makeText(SignUpActivity.this,"Error", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}