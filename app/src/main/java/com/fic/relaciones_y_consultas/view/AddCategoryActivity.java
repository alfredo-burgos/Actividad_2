package com.fic.relaciones_y_consultas.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fic.relaciones_y_consultas.R;
import com.fic.relaciones_y_consultas.controller.NoteController;
import com.fic.relaciones_y_consultas.model.Category;

import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    private NoteController noteController;
    private EditText etCategoryName;
    private Button btnSaveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        noteController = new NoteController(this);

        etCategoryName = findViewById(R.id.etCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

        btnSaveCategory.setOnClickListener(v -> {
            String name = etCategoryName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Se requiere de poner una categoria", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                noteController.insertCategory(name);

                List<Category> all = noteController.getAllCategories();
                for (Category c : all) {
                    System.out.println("Categoria en la BD: " + c.category_id + " - " + c.category_name);
                }

                runOnUiThread(() -> {
                    Toast.makeText(this, "Tu categoria fue agregada", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
