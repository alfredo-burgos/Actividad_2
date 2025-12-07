package com.fic.relaciones_y_consultas.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "notes",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "category_id",
                onDelete = CASCADE
        ),
        indices = { @Index("category_id") }
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    public long note_id;

    @ColumnInfo(name = "note_title")
    public String note_title;

    @ColumnInfo(name = "note_content")
    public String note_content;

    @ColumnInfo(name = "created_at")
    public String created_at;

    @ColumnInfo(name = "category_id")
    public long category_id;
}