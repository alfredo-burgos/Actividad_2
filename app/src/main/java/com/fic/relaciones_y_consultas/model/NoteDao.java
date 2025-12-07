package com.fic.relaciones_y_consultas.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();

    @Insert
    void insertNote(Note note);

    @Transaction
    @Query("SELECT * FROM categories")
    List<CategoryWithNotes> getAllCategoriesWithNotes();

    @Query("SELECT * FROM notes " +
            "WHERE note_title LIKE '%' || :query || '%' " +
            "   OR note_content LIKE '%' || :query || '%' " +
            "ORDER BY created_at DESC")
    List<Note> searchNotes(String query);
}