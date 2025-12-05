package com.fic.relaciones_y_consultas.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fic.relaciones_y_consultas.R;
import com.fic.relaciones_y_consultas.controller.NoteController;
import com.fic.relaciones_y_consultas.model.Category;

import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    private NoteController noteController;
    private Spinner spCategory;
    private EditText etTitle;
    private EditText etContent;
    private Button btnSaveNote;

    // lista de categorías cargadas de la BD
    private final List<Category> categoryList = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteController = new NoteController(this);

        spCategory   = findViewById(R.id.spCategory);
        etTitle      = findViewById(R.id.etTitle);
        etContent    = findViewById(R.id.etContent);
        btnSaveNote  = findViewById(R.id.btnSaveNote);

        setupSpinner();
        setupButton();
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<>()
        );
        spCategory.setAdapter(spinnerAdapter);

        // cargar categorías desde Room
        new Thread(() -> {
            List<Category> result = noteController.getAllCategories();
            categoryList.clear();
            categoryList.addAll(result);

            List<String> names = new ArrayList<>();
            for (Category c : categoryList) {
                names.add(c.categoryName);
            }

            runOnUiThread(() -> {
                spinnerAdapter.clear();
                spinnerAdapter.addAll(names);
                spinnerAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void setupButton() {
        btnSaveNote.setOnClickListener(v -> {

            // AQUÍ SE DECLARAN LAS VARIABLES QUE TE SALÍAN ROJAS
            String title   = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Llena título y contenido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (categoryList.isEmpty()) {
                Toast.makeText(this, "Primero crea una categoría", Toast.LENGTH_SHORT).show();
                return;
            }

            int position = spCategory.getSelectedItemPosition();
            Category selected = categoryList.get(position);

            // HILO PARA INSERTAR EN BD + DEBUG
            new Thread(() -> {
                long id = noteController.insertNote(
                        title,
                        content,
                        selected.categoryId
                );

                System.out.println("Note inserted with id = " + id);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
