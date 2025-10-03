package com.vitoraugusto.aulas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;

public class ProfessorDashboardActivity extends AppCompatActivity {

    private int professorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);


        professorId = getIntent().getIntExtra("professorId", -1);

        // Referências dos botões
        Button btnCadastrarAluno = findViewById(R.id.btnCadastrarAluno);
        Button btnEditarAluno = findViewById(R.id.btnEditarAluno);
        Button btnAtribuirAula = findViewById(R.id.btnAtribuirAula);
        Button btnCriarTarefa = findViewById(R.id.btnCriarTarefa);
        Button btnAprovarTarefa = findViewById(R.id.btnAprovarTarefa);
        Button btnListarAlunos = findViewById(R.id.btnListarAlunos);

        // Ações dos botões
        btnCadastrarAluno.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        btnEditarAluno.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditAlunoActivity.class);
            startActivity(intent);
        });

        btnAtribuirAula.setOnClickListener(v -> {
            Intent intent = new Intent(this, AtribuirAulaActivity.class);
            intent.putExtra("professorId", professorId); // passa id do professor
            startActivity(intent);
        });

        btnCriarTarefa.setOnClickListener(v -> {
            Intent intent = new Intent(this, CriarTarefaActivity.class);
            intent.putExtra("professorId", professorId); // passa id do professor
            startActivity(intent);
        });

        btnAprovarTarefa.setOnClickListener(v -> {
            Toast.makeText(this, "Cliquei no botão Aprovar Tarefa!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AprovarTarefaActivity.class);
            intent.putExtra("professorId", professorId);
            startActivity(intent);
        });

        btnListarAlunos.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListAlunosActivity.class);
            startActivity(intent);
        });
    }
}
