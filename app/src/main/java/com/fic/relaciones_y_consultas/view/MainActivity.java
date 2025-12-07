package com.fic.relaciones_y_consultas.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.relaciones_y_consultas.R;
import com.fic.relaciones_y_consultas.controller.NoteController;
import com.fic.relaciones_y_consultas.model.CategoryWithNotes;
import com.fic.relaciones_y_consultas.model.Note;
import com.fic.relaciones_y_consultas.view.note.CategoryAdapter;
import com.fic.relaciones_y_consultas.view.note.NoteAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteController noteController;
    private CategoryAdapter categoryAdapter;
    private NoteAdapter noteAdapter;

    private RecyclerView rvList;
    private EditText etSearch;
    private Button btnAddNote;
    private Button btnAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteController = new NoteController(this);

        rvList         = findViewById(R.id.rvCategories);
        etSearch       = findViewById(R.id.etSearch);
        btnAddNote     = findViewById(R.id.btnAddNote);
        btnAddCategory = findViewById(R.id.btnAddCategory);

        categoryAdapter = new CategoryAdapter();
        noteAdapter     = new NoteAdapter();

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(categoryAdapter);

        loadCategories();

        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });

        btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                applyCurrentFilter();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        applyCurrentFilter();
    }

    private void applyCurrentFilter() {
        String query = etSearch.getText().toString().trim();

        if (query.isEmpty()) {
            loadCategories();
        } else {
            loadSearchResults(query);
        }
    }


    private void loadCategories() {
        new Thread(() -> {
            List<CategoryWithNotes> data = noteController.getAllCategoriesWithNotes();
            runOnUiThread(() -> {
                rvList.setAdapter(categoryAdapter);
                categoryAdapter.submitList(data);
            });
        }).start();
    }

    private void loadSearchResults(String query) {
        new Thread(() -> {
            List<Note> notes = noteController.searchNotes(query);
            runOnUiThread(() -> {
                rvList.setAdapter(noteAdapter);
                noteAdapter.submitList(notes);
            });
        }).start();
    }
}