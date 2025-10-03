package com.vitoraugusto.aulas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vitoraugusto.aulas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private final DatabaseHelper dbHelper;

    public TarefaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // criar
    public long criarTarefa(Tarefa tarefa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descricao", tarefa.getDescricao());
        values.put("alunoId", tarefa.getAlunoId());
        values.put("professorId", tarefa.getProfessorId());
        values.put("status", tarefa.getStatus());
        long id = db.insert("tarefas", null, values);
        db.close();
        return id;
    }

    // listar por aluno (sem nome)
    public List<Tarefa> listarTarefasPorAluno(int alunoId) {
        List<Tarefa> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tarefas", null, "alunoId = ?", new String[]{String.valueOf(alunoId)}, null, null, "id DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tarefa t = new Tarefa();
                t.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                t.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                t.setAlunoId(cursor.getInt(cursor.getColumnIndexOrThrow("alunoId")));
                t.setProfessorId(cursor.getInt(cursor.getColumnIndexOrThrow("professorId")));
                t.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                lista.add(t);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    // listar tarefas com nome do aluno (JOIN) - útil para aprovar
    public List<Tarefa> listarTarefasComAluno() {
        List<Tarefa> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q = "SELECT t.id, t.descricao, t.alunoId, t.professorId, t.status, u.nome AS nomeAluno " +
                "FROM tarefas t JOIN usuarios u ON t.alunoId = u.id ORDER BY t.id DESC";
        Cursor cursor = db.rawQuery(q, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tarefa t = new Tarefa();
                t.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                t.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
                t.setAlunoId(cursor.getInt(cursor.getColumnIndexOrThrow("alunoId")));
                t.setProfessorId(cursor.getInt(cursor.getColumnIndexOrThrow("professorId")));
                t.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                t.setNomeAluno(cursor.getString(cursor.getColumnIndexOrThrow("nomeAluno")));
                lista.add(t);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    // buscar por id (com nome do aluno)
    public Tarefa buscarPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q = "SELECT t.id, t.descricao, t.alunoId, t.professorId, t.status, u.nome AS nomeAluno " +
                "FROM tarefas t JOIN usuarios u ON t.alunoId = u.id WHERE t.id = ?";
        Cursor cursor = db.rawQuery(q, new String[]{String.valueOf(id)});
        Tarefa t = null;
        if (cursor != null && cursor.moveToFirst()) {
            t = new Tarefa();
            t.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            t.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            t.setAlunoId(cursor.getInt(cursor.getColumnIndexOrThrow("alunoId")));
            t.setProfessorId(cursor.getInt(cursor.getColumnIndexOrThrow("professorId")));
            t.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            t.setNomeAluno(cursor.getString(cursor.getColumnIndexOrThrow("nomeAluno")));
            cursor.close();
        }
        db.close();
        return t;
    }

    // atualizar só status
    public boolean atualizarStatus(int idTarefa, String novoStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("status", novoStatus);
        int rows = db.update("tarefas", v, "id = ?", new String[]{String.valueOf(idTarefa)});
        db.close();
        return rows > 0;
    }
}
