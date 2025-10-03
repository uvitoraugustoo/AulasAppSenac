package com.vitoraugusto.aulas.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.UsuarioDAO;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText edtEmailRecuperar;
    private Button btnEnviarRecuperacao;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        edtEmailRecuperar = findViewById(R.id.edtEmailRecuperar);
        btnEnviarRecuperacao = findViewById(R.id.btnEnviarRecuperacao);

        usuarioDAO = new UsuarioDAO(this);

        btnEnviarRecuperacao.setOnClickListener(v -> {
            String email = edtEmailRecuperar.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Digite seu e-mail!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Define nova senha padrão
            String novaSenha = "123456";

            boolean sucesso = usuarioDAO.redefinirSenha(email, novaSenha);

            if (sucesso) {
                Toast.makeText(this, "Senha redefinida para: " + novaSenha, Toast.LENGTH_LONG).show();
                finish(); // volta para login
            } else {
                Toast.makeText(this, "E-mail não encontrado!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
