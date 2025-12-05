package com.fic.relaciones_y_consultas.controller;

import android.content.Context;

import com.fic.relaciones_y_consultas.model.AppDatabase;
import com.fic.relaciones_y_consultas.model.Category;
import com.fic.relaciones_y_consultas.model.CategoryWithNotes;
import com.fic.relaciones_y_consultas.model.Note;
import com.fic.relaciones_y_consultas.model.NoteDao;

import java.util.List;

public class NoteController {

    private final NoteDao noteDao;

    public NoteController(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        noteDao = database.noteDao();
    }

    // MODEL operations (sin tocar UI, solo lógica)

    public long insertCategory(String categoryName) {
        Category category = new Category(categoryName);
        return noteDao.insertCategory(category);
    }

    public long insertNote(String noteTitle, String noteContent, long categoryId) {
        long createdAt = System.currentTimeMillis();
        Note note = new Note(noteTitle, noteContent, createdAt, categoryId);
        return noteDao.insertNote(note);
    }

    public List<CategoryWithNotes> getAllCategoriesWithNotes() {
        return noteDao.getAllCategoriesWithNotes();
    }

    public List<Note> getNotesByCategory(long categoryId) {
        return noteDao.getNotesByCategory(categoryId);
    }

    public List<Note> searchNotes(String query) {
        return noteDao.searchNotes(query);
    }

    public List<Category> getAllCategories() {
        return noteDao.getAllCategories();
    }

}
