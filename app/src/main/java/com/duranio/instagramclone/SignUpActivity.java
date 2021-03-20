package com.duranio.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private TextView saveTextView;
    private EditText nameEditText, punchSpeedEditText, punchPowerEditText, kickSpeedEditText, kickPowerEditText;
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
    }
}