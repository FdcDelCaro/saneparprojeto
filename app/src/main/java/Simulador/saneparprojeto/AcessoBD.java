package Simulador.saneparprojeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AcessoBD extends SQLiteOpenHelper {

    private static final String NOMEBANCO = "sanepar2.db";

    public AcessoBD(@Nullable Context context) {
        super(context, NOMEBANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarTabelaLeituras = "CREATE TABLE leituras (id INTEGER PRIMARY KEY AUTOINCREMENT, leituraatualizada TEXT)";
        String criarTabelaConsumo = "CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, listaconsumo TEXT)";
        String criarTbusuario = "CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT UNIQUE, nomeuser TEXT UNIQUE, senha TEXT)";

        db.execSQL(criarTabelaLeituras);
        db.execSQL(criarTabelaConsumo);
        db.execSQL(criarTbusuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS leituras");
        db.execSQL("DROP TABLE IF EXISTS consumo");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

    public boolean verificarUsuario(String nomeUser, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM usuario WHERE nomeuser = ? AND senha = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nomeUser, senha});
        boolean existeUsuario = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existeUsuario;
    }

    public long addUsuario(String nome, String email, String nomeUser, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("email", email);
        values.put("nomeuser", nomeUser);
        values.put("senha", senha);

        long result = db.insert("usuario", null, values);
        db.close();
        return result;
    }

    public void salvarLeituraAtual(String leitura) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("leituraatualizada", leitura);
            db.insert("leituras", null, values);
        } finally {
            db.close();
        }
    }

    public String getUltimaLeitura() {
        String leituraAnterior = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT leituraatualizada FROM leituras ORDER BY id DESC LIMIT 1";
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                leituraAnterior = cursor.getString(cursor.getColumnIndexOrThrow("leituraatualizada"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return leituraAnterior;
    }

    public void salvarConsumo(String consumo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("listaconsumo", consumo);
            db.insert("consumo", null, values);
        } finally {
            db.close();
        }
    }

    public List<String> listaconsumido() {
        List<String> listaConsumo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT listaconsumo FROM consumo ORDER BY id DESC";
            cursor = db.rawQuery(query, null);
            while (cursor != null && cursor.moveToNext()) {
                String consumido = cursor.getString(cursor.getColumnIndexOrThrow("listaconsumo"));
                listaConsumo.add(consumido);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return listaConsumo;
    }
}
