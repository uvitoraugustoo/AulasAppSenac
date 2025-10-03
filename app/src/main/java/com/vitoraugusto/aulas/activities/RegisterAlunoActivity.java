package com.vitoraugusto.aulas.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.UsuarioDAO;
import com.vitoraugusto.aulas.model.Aluno;


public class RegisterAlunoActivity extends AppCompatActivity {
    EditText edtNome, edtEmail, edtSenha, edtCpf;
    Button btnSalvar;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_aluno);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCpf = findViewById(R.id.edtCpf);
        btnSalvar = findViewById(R.id.btnSalvar);

        usuarioDAO = new UsuarioDAO(this);

        btnSalvar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            String cpf = edtCpf.getText().toString();

            if (usuarioDAO.cpfExiste(cpf)) {
                Toast.makeText(this, "CPF já cadastrado!", Toast.LENGTH_SHORT).show();
            } else {
                Aluno aluno = new Aluno(0, nome, email, senha, cpf);
                usuarioDAO.inserirUsuario(aluno);
                Toast.makeText(this, "Aluno cadastrado!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

