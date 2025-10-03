package com.vitoraugusto.aulas.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.TarefaDAO;
import com.vitoraugusto.aulas.database.UsuarioDAO;
import com.vitoraugusto.aulas.model.Aluno;
import com.vitoraugusto.aulas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class CriarTarefaActivity extends AppCompatActivity {

    private EditText edtDescricao;
    private Spinner spinnerAlunos;
    private Button btnSalvar;

    private UsuarioDAO usuarioDAO;
    private TarefaDAO tarefaDAO;

    private List<Aluno> listaAlunos;
    private int alunoSelecionadoId = -1; // ID real do aluno selecionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_tarefa);

        edtDescricao = findViewById(R.id.edtDescricao);
        spinnerAlunos = findViewById(R.id.spinnerAlunos);
        btnSalvar = findViewById(R.id.btnSalvar);

        usuarioDAO = new UsuarioDAO(this);
        tarefaDAO = new TarefaDAO(this);

        carregarSpinnerAlunos();

        btnSalvar.setOnClickListener(v -> salvarTarefa());
    }

    private void carregarSpinnerAlunos() {
        listaAlunos = usuarioDAO.listarAlunos();

        if (listaAlunos.isEmpty()) {
            Toast.makeText(this, "Nenhum aluno cadastrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Exibir CPFs no spinner
        List<String> cpfs = new ArrayList<>();
        for (Aluno aluno : listaAlunos) {
            cpfs.add(aluno.getCpf());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cpfs
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlunos.setAdapter(adapter);

        spinnerAlunos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alunoSelecionadoId = listaAlunos.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                alunoSelecionadoId = -1;
            }
        });
    }

    private void salvarTarefa() {
        String descricao = edtDescricao.getText().toString().trim();

        if (descricao.isEmpty() || alunoSelecionadoId == -1) {
            Toast.makeText(this, "Preencha a descrição e selecione um aluno!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aqui o professorId idealmente vem do login do professor
        int professorId = 1; // temporário

        // Criar objeto tarefa
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(descricao);
        tarefa.setAlunoId(alunoSelecionadoId);
        tarefa.setProfessorId(professorId);
        tarefa.setStatus("pendente");

        long id = tarefaDAO.criarTarefa(tarefa);

        if (id > 0) {
            Toast.makeText(this, "Tarefa criada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao criar tarefa!", Toast.LENGTH_SHORT).show();
        }
    }
}
