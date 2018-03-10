package com.practice.xiaoli.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class AddNoteActivity extends AppCompatActivity {

    int curerntNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText addNoteEditText = (EditText)findViewById(R.id.addNoteEditText);

        Intent intent = getIntent();
        curerntNoteId = intent.getIntExtra("noteId", -1);

        if(curerntNoteId != -1){
            addNoteEditText.setText(MainActivity.noteList.get(curerntNoteId));
        }else{
            MainActivity.noteList.add("");
            curerntNoteId = MainActivity.noteList.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        addNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.noteList.set(curerntNoteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.practice.xiaoli.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.noteList);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
