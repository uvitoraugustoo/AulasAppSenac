package com.vitoraugusto.aulas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.activities.AprovarTarefaActivity;
import com.vitoraugusto.aulas.database.TarefaDAO;
import com.vitoraugusto.aulas.model.Tarefa;

import java.util.List;

public class AprovarTarefaAdapter extends RecyclerView.Adapter<AprovarTarefaAdapter.VH> {

    private final Context ctx;
    private final List<Tarefa> lista;
    private final TarefaDAO tarefaDAO;

    public AprovarTarefaAdapter(Context ctx, List<Tarefa> lista) {
        this.ctx = ctx;
        this.lista = lista;
        this.tarefaDAO = new TarefaDAO(ctx);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_aprovar_tarefa, parent, false);
        return new VH(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Tarefa t = lista.get(position);
        holder.txtDescricao.setText(t.getDescricao());
        holder.txtAluno.setText("Aluno: " + (t.getNomeAluno() != null ? t.getNomeAluno() : String.valueOf(t.getAlunoId())));
        holder.txtStatus.setText("Status: " + t.getStatus());

        holder.btnAbrir.setOnClickListener(v -> {
            Intent it = new Intent(ctx, AprovarTarefaActivity.class);
            it.putExtra("tarefaId", t.getId());
            ctx.startActivity(it);
        });

        // opcional: aprovar direto no item
        holder.btnAprovar.setOnClickListener(v -> {
            boolean ok = tarefaDAO.atualizarStatus(t.getId(), "aprovada");
            if (ok) {
                Toast.makeText(ctx, "Aprovada", Toast.LENGTH_SHORT).show();
                t.setStatus("aprovada");
                notifyItemChanged(position);
            }
        });

        holder.btnReprovar.setOnClickListener(v -> {
            boolean ok = tarefaDAO.atualizarStatus(t.getId(), "reprovada");
            if (ok) {
                Toast.makeText(ctx, "Reprovada", Toast.LENGTH_SHORT).show();
                t.setStatus("reprovada");
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtDescricao, txtAluno, txtStatus;
        Button btnAprovar, btnReprovar, btnAbrir;

        VH(@NonNull View itemView) {
            super(itemView);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoTarefa);
            txtAluno = itemView.findViewById(R.id.txtAlunoTarefa);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnAprovar = itemView.findViewById(R.id.btnAprovar);
            btnReprovar = itemView.findViewById(R.id.btnReprovar);

        }
    }
}
