package com.vitoraugusto.aulas.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.adapter.AulaAdapter;
import com.vitoraugusto.aulas.database.AulaDAO;
import com.vitoraugusto.aulas.model.Aula;

import java.util.List;

public class VerAulasAlunoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AulaAdapter adapter;
    private AulaDAO aulaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_aulas_aluno);

        recyclerView = findViewById(R.id.recyclerVerTarefas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        aulaDAO = new AulaDAO(this);

        int alunoId = getIntent().getIntExtra("alunoId", -1);

        List<Aula> aulas = aulaDAO.listarAulasPorAluno(alunoId);

        adapter = new AulaAdapter(aulas);
        recyclerView.setAdapter(adapter);
    }
}
