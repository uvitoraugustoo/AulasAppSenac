package com.vitoraugusto.aulas.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.AulaDAO;
import com.vitoraugusto.aulas.database.UsuarioDAO;
import com.vitoraugusto.aulas.model.Aula;
import com.vitoraugusto.aulas.model.Usuario;

public class AtribuirAulaActivity extends AppCompatActivity {

    private EditText edtTitulo, edtCpfAluno;
    private Button btnAtribuir;
    private UsuarioDAO usuarioDAO;
    private AulaDAO aulaDAO;
    private int professorId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atribuir_aula);

        edtTitulo = findViewById(R.id.etTituloAula);
        edtCpfAluno = findViewById(R.id.etCpfAluno);
        btnAtribuir = findViewById(R.id.btnAtribuirAula);

        usuarioDAO = new UsuarioDAO(this);
        aulaDAO = new AulaDAO(this);

        professorId = getIntent().getIntExtra("professorId", -1);
        if (professorId == -1) {
            Toast.makeText(this, "Professor não identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnAtribuir.setOnClickListener(v -> {
            String titulo = edtTitulo.getText().toString().trim();
            String cpf = edtCpfAluno.getText().toString().trim();
            if (titulo.isEmpty() || cpf.isEmpty()) {
                Toast.makeText(this, "Preencha título e CPF", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario aluno = usuarioDAO.buscarPorCpf(cpf);
            if (aluno == null) {
                Toast.makeText(this, "Aluno não encontrado para o CPF", Toast.LENGTH_SHORT).show();
                return;
            }

            Aula aula = new Aula(0, titulo, aluno.getId(), professorId);
            long id = aulaDAO.inserirAula(aula);
            if (id > 0) {
                Toast.makeText(this, "Aula atribuída a " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao atribuir aula", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
