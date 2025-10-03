package com.vitoraugusto.aulas.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.TarefaDAO;
import com.vitoraugusto.aulas.model.Tarefa;

public class AprovarTarefaActivity extends AppCompatActivity {

    private TextView txtDescricao, txtAluno;
    private Button btnAprovar, btnReprovar;
    private TarefaDAO tarefaDAO;
    private int tarefaId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovar_tarefa);

        txtDescricao = findViewById(R.id.txtDescricaoTarefa);
        txtAluno = findViewById(R.id.txtAlunoTarefa);
        btnAprovar = findViewById(R.id.btnAprovar);
        btnReprovar = findViewById(R.id.btnReprovar);

        tarefaDAO = new TarefaDAO(this);

        tarefaId = getIntent().getIntExtra("tarefaId", -1);
        if (tarefaId == -1) { finish(); return; }

        Tarefa t = tarefaDAO.buscarPorId(tarefaId);
        if (t != null) {
            txtDescricao.setText(t.getDescricao());
            txtAluno.setText("Aluno ID: " + t.getAlunoId());
        }

        btnAprovar.setOnClickListener(v -> {
            boolean ok = tarefaDAO.atualizarStatus(tarefaId, "aprovada");
            Toast.makeText(this, ok ? "Aprovada" : "Erro", Toast.LENGTH_SHORT).show();
            if (ok) finish();
        });

        btnReprovar.setOnClickListener(v -> {
            boolean ok = tarefaDAO.atualizarStatus(tarefaId, "reprovada");
            Toast.makeText(this, ok ? "Reprovada" : "Erro", Toast.LENGTH_SHORT).show();
            if (ok) finish();
        });
    }
}
