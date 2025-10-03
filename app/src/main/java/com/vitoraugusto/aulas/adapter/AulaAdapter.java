package com.vitoraugusto.aulas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.model.Aula;

import java.util.List;

public class AulaAdapter extends RecyclerView.Adapter<AulaAdapter.AulaViewHolder> {

    private List<Aula> aulas;

    public AulaAdapter(List<Aula> aulas) {
        this.aulas = aulas;
    }

    @NonNull
    @Override
    public AulaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aula, parent, false);
        return new AulaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AulaViewHolder holder, int position) {
        Aula aula = aulas.get(position);
        holder.txtTitulo.setText(aula.getTitulo());
        holder.txtProfessor.setText("Professor ID: " + aula.getProfessorId());
    }

    @Override
    public int getItemCount() {
        return aulas.size();
    }

    static class AulaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtProfessor;

        public AulaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloAula);
            txtProfessor = itemView.findViewById(R.id.txtProfessorAula);
        }
    }
}
