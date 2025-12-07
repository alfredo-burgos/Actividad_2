package com.fic.relaciones_y_consultas.controller;

import android.content.Context;

import com.fic.relaciones_y_consultas.model.AppDatabase;
import com.fic.relaciones_y_consultas.model.Category;
import com.fic.relaciones_y_consultas.model.CategoryWithNotes;
import com.fic.relaciones_y_consultas.model.Note;
import com.fic.relaciones_y_consultas.model.NoteDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteController {

    private final NoteDao noteDao;

    public NoteController(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        noteDao = database.noteDao();
    }

    public void insertCategory(String categoryName) {
        Category category = new Category(categoryName);
        noteDao.insertCategory(category);
    }

    public List<Category> getAllCategories() {
        return noteDao.getAllCategories();
    }

    public void insertNote(String title, String content, long categoryId) {
        Note note = new Note();
        note.note_title = title;
        note.note_content = content;
        note.category_id = categoryId;

        SimpleDateFormat sdf =
                new SimpleDateFormat(AppDatabase.DATE_FORMAT, Locale.getDefault());
        note.created_at = sdf.format(new Date());

        noteDao.insertNote(note);
    }

    public List<CategoryWithNotes> getAllCategoriesWithNotes() {
        return noteDao.getAllCategoriesWithNotes();
    }

    public List<Note> searchNotes(String query) {
        return noteDao.searchNotes(query);
    }
}