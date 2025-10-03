package com.vitoraugusto.aulas.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.UsuarioDAO;
import com.vitoraugusto.aulas.model.Aluno;
import com.vitoraugusto.aulas.model.Usuario;

public class EditAlunoActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha, edtCpf;
    private Button btnSalvar;
    private UsuarioDAO usuarioDAO;
    private int alunoId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aluno);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCpf = findViewById(R.id.edtCpf);
        btnSalvar = findViewById(R.id.btnSalvar);

        usuarioDAO = new UsuarioDAO(this);

        alunoId = getIntent().getIntExtra("alunoId", -1);
        if (alunoId == -1) {
            Toast.makeText(this, "Aluno não identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        carregarAluno();

        btnSalvar.setOnClickListener(v -> salvar());
    }

    private void carregarAluno() {
        Usuario u = usuarioDAO.buscarPorId(alunoId);
        if (u instanceof Aluno) {
            Aluno a = (Aluno) u;
            edtNome.setText(a.getNome());
            edtEmail.setText(a.getEmail());
            edtSenha.setText(a.getSenha());
            edtCpf.setText(a.getCpf());
        } else {
            Toast.makeText(this, "Usuário não é aluno", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void salvar() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Aluno aluno = new Aluno(alunoId, nome, email, senha, cpf);
        boolean ok = usuarioDAO.atualizarUsuario(aluno);
        if (ok) {
            Toast.makeText(this, "Aluno atualizado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
        }
    }
}
