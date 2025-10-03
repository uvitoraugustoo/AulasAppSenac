package com.vitoraugusto.aulas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vitoraugusto.aulas.model.Aula;

import java.util.ArrayList;
import java.util.List;

public class AulaDAO {
    private final DatabaseHelper dbHelper;
    private static final String TABLE = "aulas";

    public AulaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Inserir aula
    public long inserirAula(Aula aula) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", aula.getTitulo());
        values.put("alunoId", aula.getAlunoId());
        values.put("professorId", aula.getProfessorId());

        long id = db.insert(TABLE, null, values);
        db.close();
        return id;
    }

    // Listar aulas de um aluno específico
    public List<Aula> listarAulasPorAluno(int alunoId) {
        List<Aula> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE,
                null,
                "alunoId = ?",
                new String[]{String.valueOf(alunoId)},
                null,
                null,
                "id DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                int professorId = cursor.getInt(cursor.getColumnIndexOrThrow("professorId"));

                Aula aula = new Aula(id, titulo, alunoId, professorId);
                lista.add(aula);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lista;
    }

    // Listar aulas de um professor específico
    public List<Aula> listarAulasPorProfessor(int professorId) {
        List<Aula> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE,
                null,
                "professorId = ?",
                new String[]{String.valueOf(professorId)},
                null,
                null,
                "id DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                int alunoId = cursor.getInt(cursor.getColumnIndexOrThrow("alunoId"));

                Aula aula = new Aula(id, titulo, alunoId, professorId);
                lista.add(aula);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return lista;
    }
}
