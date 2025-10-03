package com.vitoraugusto.aulas.activities;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.vitoraugusto.aulas.R;
import com.vitoraugusto.aulas.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAlunosActivity extends AppCompatActivity {

    private ListView listAlunos;
    private ArrayList<HashMap<String, String>> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alunos);

        listAlunos = findViewById(R.id.listAlunos);

        carregarAlunos();

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                lista,
                R.layout.item_aluno,
                new String[]{"nome", "email"},
                new int[]{R.id.tvNomeAluno, R.id.tvEmailAluno}
        );

        listAlunos.setAdapter(adapter);

        listAlunos.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            HashMap<String, String> aluno = lista.get(position);
            int alunoId = Integer.parseInt(aluno.get("id"));

            Intent intent = new Intent(ListAlunosActivity.this, EditAlunoActivity.class);
            intent.putExtra("alunoId", alunoId);
            startActivity(intent);
        });
    }

    private void carregarAlunos() {
        lista.clear();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, nome, email FROM usuarios WHERE tipo = 'aluno'", null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> aluno = new HashMap<>();
                aluno.put("id", cursor.getString(0));
                aluno.put("nome", cursor.getString(1));
                aluno.put("email", cursor.getString(2));
                lista.add(aluno);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
}

