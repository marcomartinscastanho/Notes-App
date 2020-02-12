package com.martinscastanho.marco.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;

public class EditNoteActivity extends AppCompatActivity {
    int noteId;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId == -1){
            // Add a new note
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() -1;
        }

        String note = MainActivity.notes.get(noteId);

        editText = findViewById(R.id.noteEditText);
        editText.setText(note);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            MainActivity.sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
