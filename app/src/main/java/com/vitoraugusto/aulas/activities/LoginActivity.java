package com.vitoraugusto.aulas.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.UsuarioDAO;
import com.vitoraugusto.aulas.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnLogin, btnRegister,btnRecuperarSenha;
    private UsuarioDAO usuarioDAO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegistrar);
        btnRecuperarSenha = findViewById(R.id.btnRecuperarSenha);

        usuarioDAO = new UsuarioDAO(this);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = usuarioDAO.login(email, senha);

            if (usuario != null) {
                if ("professor".equals(usuario.getTipo())) {
                    Intent intent = new Intent(this, ProfessorDashboardActivity.class);
                    intent.putExtra("professorId", usuario.getId()); // <- passa ID do professor
                    startActivity(intent);
                    finish();
                } else if ("aluno".equals(usuario.getTipo())) {
                    Intent intent = new Intent(this, AlunoDashboardActivity.class);
                    intent.putExtra("alunoId", usuario.getId()); // <- passa ID do aluno
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(this, "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        btnRecuperarSenha.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecuperarSenhaActivity.class);
            startActivity(intent);
        });

    }
}
