package com.vitoraugusto.aulas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.UsuarioDAO;
import com.vitoraugusto.aulas.model.Aluno;
import com.vitoraugusto.aulas.model.Professor;
import com.vitoraugusto.aulas.model.Usuario;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha, edtCpf, edtArea;
    private RadioGroup rgTipo;
    private RadioButton rbAluno, rbProfessor;
    private Button btnRegistrar, btnLogin;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usuarioDAO = new UsuarioDAO(this);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtCpf = findViewById(R.id.edtCpf);
        edtArea = findViewById(R.id.edtArea);

        rgTipo = findViewById(R.id.rgTipo);
        rbAluno = findViewById(R.id.rbAluno);
        rbProfessor = findViewById(R.id.rbProfessor);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnLogin = findViewById(R.id.btnLogin);

        rgTipo.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbAluno) {
                edtCpf.setVisibility(View.VISIBLE);
                edtArea.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbProfessor) {
                edtCpf.setVisibility(View.GONE);
                edtArea.setVisibility(View.VISIBLE);
            }
        });

        btnRegistrar.setOnClickListener(v -> registrarUsuario());

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private boolean emailValidoParaTipo(String email, boolean isAluno) {
        if (email == null) return false;
        email = email.toLowerCase().trim();
        if (isAluno) {
            return email.endsWith("@aluno.com") || email.endsWith("@aluno.br");
        } else {
            return email.endsWith("@professor.com") || email.endsWith("@professor.br");
        }
    }

    private void registrarUsuario() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();
        String area = edtArea.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rbAluno.isChecked()) {
            if (!emailValidoParaTipo(email, true)) {
                Toast.makeText(this, "E-mail inválido para aluno (use @aluno.*)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(cpf)) {
                Toast.makeText(this, "Informe o CPF do aluno!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (usuarioDAO.cpfExiste(cpf)) {
                Toast.makeText(this, "CPF já registrado!", Toast.LENGTH_SHORT).show();
                return;
            }
            Aluno aluno = new Aluno(0, nome, email, senha, cpf);
            long id = usuarioDAO.inserirUsuario(aluno);
            if (id > 0) {
                Toast.makeText(this, "Aluno registrado!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Erro ao registrar aluno", Toast.LENGTH_SHORT).show();
            }

        } else if (rbProfessor.isChecked()) {
            if (!emailValidoParaTipo(email, false)) {
                Toast.makeText(this, "E-mail inválido para professor (use @professor.*)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(area)) {
                Toast.makeText(this, "Informe a área do professor!", Toast.LENGTH_SHORT).show();
                return;
            }
            Professor professor = new Professor(0, nome, email, senha, area);
            long id = usuarioDAO.inserirUsuario(professor);
            if (id > 0) {
                Toast.makeText(this, "Professor registrado!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Erro ao registrar professor", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Selecione se é Aluno ou Professor!", Toast.LENGTH_SHORT).show();
        }
    }
}
