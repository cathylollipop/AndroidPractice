/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  Boolean signupModeActive = true;
  TextView signupModeTextView;

  public void onClick(View view){
    Button signupButton = (Button)findViewById(R.id.signupButton);

    if(view.getId() == R.id.signupModeTextView){
      if(signupModeActive){
        signupButton.setText("Login");
        signupModeTextView.setText("Or, Signup");
        signupModeActive = false;
      }else{
        signupButton.setText("Signup");
        signupModeTextView.setText("Or, Login");
        signupModeActive = true;
      }

    }
  }

  public void signup(View view){
    EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);
    EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);

    if(usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){
      Toast.makeText(getApplicationContext(), "Username and password are required.", Toast.LENGTH_LONG).show();
    }else{
      if(signupModeActive){
        Log.i("location","signup");
        final ParseUser user = new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if(e == null){
              Log.i("SignUp", "Successful");
            }else{
              Toast.makeText(getApplicationContext(), "User Sign Up Failed.", Toast.LENGTH_LONG).show();
            }
          }
        });
      }else{
        Log.i("location","login");
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if(e == null && user != null){
              Log.i("Login", "Successful");
            }else{
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
          }
        });
      }

    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    signupModeTextView = (TextView)findViewById(R.id.signupModeTextView);
    signupModeTextView.setOnClickListener(this);
    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}