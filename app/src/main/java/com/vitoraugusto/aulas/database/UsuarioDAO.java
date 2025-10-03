package com.vitoraugusto.aulas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vitoraugusto.aulas.model.Aluno;
import com.vitoraugusto.aulas.model.Professor;
import com.vitoraugusto.aulas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // -----------------------
    // Inserir usuário (Aluno ou Professor)
    // -----------------------
    public long inserirUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("tipo", usuario.getTipo());

        // salva cpf ou area dependendo do tipo
        if (usuario instanceof Aluno) {
            values.put("cpf", ((Aluno) usuario).getCpf());
            values.putNull("area");
        } else if (usuario instanceof Professor) {
            values.put("area", ((Professor) usuario).getArea());
            values.putNull("cpf");
        } else {
            values.putNull("cpf");
            values.putNull("area");
        }

        long id = db.insert("usuarios", null, values);
        db.close();
        return id;
    }

    // -----------------------
    // Login (email + senha)
    // -----------------------
    public Usuario login(String email, String senha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("usuarios", null,
                "email = ? AND senha = ?",
                new String[]{email, senha}, null, null, null);

        Usuario u = null;
        if (cursor != null && cursor.moveToFirst()) {
            u = cursorToUsuario(cursor);
            cursor.close();
        }
        if (cursor != null && !cursor.isClosed()) cursor.close();
        db.close();
        return u;
    }

    // -----------------------
    // Verifica se CPF já existe (usado no registro)
    // -----------------------
    public boolean cpfExiste(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) return false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("usuarios", new String[]{"id"}, "cpf = ?", new String[]{cpf}, null, null, null);
        boolean existe = cursor != null && cursor.moveToFirst();
        if (cursor != null) cursor.close();
        db.close();
        return existe;
    }

    // -----------------------
    // Buscar por CPF
    // -----------------------
    public Usuario buscarPorCpf(String cpf) {
        if (cpf == null) return null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("usuarios", null, "cpf = ?", new String[]{cpf}, null, null, null);
        Usuario u = null;
        if (cursor != null && cursor.moveToFirst()) {
            u = cursorToUsuario(cursor);
            cursor.close();
        }
        if (cursor != null && !cursor.isClosed()) cursor.close();
        db.close();
        return u;
    }

    // -----------------------
    // Buscar por ID
    // -----------------------
    public Usuario buscarPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("usuarios", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Usuario u = null;
        if (cursor != null && cursor.moveToFirst()) {
            u = cursorToUsuario(cursor);
            cursor.close();
        }
        if (cursor != null && !cursor.isClosed()) cursor.close();
        db.close();
        return u;
    }

    // -----------------------
    // Listar apenas Alunos
    // -----------------------
    public List<Aluno> listarAlunos() {
        List<Aluno> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE tipo = 'aluno' ORDER BY nome ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"));
                String cpf = null;
                try { cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")); } catch (Exception ignored) {}
                Aluno a = new Aluno(id, nome, email, senha, cpf);
                lista.add(a);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    // -----------------------
    // Listar apenas Professores
    // -----------------------
    public List<Professor> listarProfessores() {
        List<Professor> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE tipo = 'professor' ORDER BY nome ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"));
                String area = null;
                try { area = cursor.getString(cursor.getColumnIndexOrThrow("area")); } catch (Exception ignored) {}
                Professor p = new Professor(id, nome, email, senha, area);
                lista.add(p);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    // -----------------------
    // Atualizar usuário (usado no EditAlunoActivity)
    // -----------------------
    public boolean atualizarUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("tipo", usuario.getTipo());

        if (usuario instanceof Aluno) {
            values.put("cpf", ((Aluno) usuario).getCpf());
            values.putNull("area");
        } else if (usuario instanceof Professor) {
            values.put("area", ((Professor) usuario).getArea());
            values.putNull("cpf");
        }

        int rows = db.update("usuarios", values, "id = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
        return rows > 0;
    }

    // -----------------------
    // Redefinir senha por email
    // -----------------------
    public boolean redefinirSenha(String email, String novaSenha) {
        if (email == null || email.trim().isEmpty()) return false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("senha", novaSenha);
        int linhasAfetadas = db.update("usuarios", values, "email = ?", new String[]{email});
        db.close();
        return linhasAfetadas > 0;
    }

    // -----------------------
    // Converte cursor para um objeto Usuario (Aluno / Professor quando aplicável)
    // -----------------------
    private Usuario cursorToUsuario(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"));
        String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));

        if ("aluno".equalsIgnoreCase(tipo)) {
            String cpf = null;
            try { cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf")); } catch (Exception ignored) {}
            return new Aluno(id, nome, email, senha, cpf);
        } else if ("professor".equalsIgnoreCase(tipo)) {
            String area = null;
            try { area = cursor.getString(cursor.getColumnIndexOrThrow("area")); } catch (Exception ignored) {}
            return new Professor(id, nome, email, senha, area);
        } else {
            // Se a sua classe Usuario tiver um construtor (id,nome,email,senha,tipo) use-o.
            // Caso contrário, adapte para usar os setters disponíveis na sua classe Usuario.
            return new Usuario(id, nome, email, senha, tipo);
        }
    }
}
