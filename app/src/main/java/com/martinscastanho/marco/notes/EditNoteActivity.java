package com.martinscastanho.marco.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        if(noteId == -1)
            finish();

        String note = MainActivity.notes.get(noteId);

        editText = findViewById(R.id.noteEditText);
        editText.setText(note);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.notes.set(noteId, editText.getText().toString());
        MainActivity.arrayAdapter.notifyDataSetChanged();
        try {
            MainActivity.sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
