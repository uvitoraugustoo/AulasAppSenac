package com.vitoraugusto.aulas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.model.Tarefa;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private List<Tarefa> tarefas;

    public TarefaAdapter(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarefa, parent, false);
        return new TarefaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.txtDescricao.setText(tarefa.getDescricao());
        holder.txtStatus.setText(tarefa.isAprovada() ? "✅ Aprovada" : "⏳ Pendente");
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    static class TarefaViewHolder extends RecyclerView.ViewHolder {
        TextView txtDescricao, txtStatus;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}
