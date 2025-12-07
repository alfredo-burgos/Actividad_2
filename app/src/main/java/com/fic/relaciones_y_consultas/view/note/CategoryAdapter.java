package com.fic.relaciones_y_consultas.view.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.relaciones_y_consultas.R;
import com.fic.relaciones_y_consultas.model.CategoryWithNotes;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<CategoryWithNotes> categoryList = new ArrayList<>();

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        RecyclerView rvNotes;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rvNotes = itemView.findViewById(R.id.rvNotes);
        }
    }

    public void submitList(List<CategoryWithNotes> categories) {
        categoryList.clear();
        if (categories != null) {
            categoryList.addAll(categories);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryWithNotes item = categoryList.get(position);

        holder.tvCategoryName.setText(item.category.category_name);

        NoteAdapter noteAdapter = new NoteAdapter();
        holder.rvNotes.setLayoutManager(new LinearLayoutManager(
                holder.itemView.getContext()
        ));
        holder.rvNotes.setAdapter(noteAdapter);
        noteAdapter.submitList(item.notes);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

