package com.vitoraugusto.aulas.activities;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;


public class AlunoDashboardActivity extends AppCompatActivity {
    Button btnVerAulas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_dashboard);

        btnVerAulas = findViewById(R.id.btnVerAulas);
        btnVerAulas.setOnClickListener(v -> startActivity(new Intent(this, VerAulasAlunoActivity.class)));
    }
}

