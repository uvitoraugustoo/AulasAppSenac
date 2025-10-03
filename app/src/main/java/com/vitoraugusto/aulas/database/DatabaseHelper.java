package com.vitoraugusto.aulas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "aulas.db";
    private static final int DATABASE_VERSION = 3; // incremente se mudar schema

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // usuarios
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "senha TEXT NOT NULL, " +
                "tipo TEXT NOT NULL, " +
                "cpf TEXT, " +
                "area TEXT" +
                ")");

        // aulas (se usar)
        db.execSQL("CREATE TABLE IF NOT EXISTS aulas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "alunoId INTEGER NOT NULL, " +
                "professorId INTEGER NOT NULL, " +
                "FOREIGN KEY(alunoId) REFERENCES usuarios(id), " +
                "FOREIGN KEY(professorId) REFERENCES usuarios(id)" +
                ")");

        // tarefas com campo status
        db.execSQL("CREATE TABLE IF NOT EXISTS tarefas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT NOT NULL, " +
                "alunoId INTEGER NOT NULL, " +
                "professorId INTEGER NOT NULL, " +
                "status TEXT NOT NULL DEFAULT 'pendente', " +
                "FOREIGN KEY(alunoId) REFERENCES usuarios(id), " +
                "FOREIGN KEY(professorId) REFERENCES usuarios(id)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se mudança breaking: drop e recria
        db.execSQL("DROP TABLE IF EXISTS tarefas");
        db.execSQL("DROP TABLE IF EXISTS aulas");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
