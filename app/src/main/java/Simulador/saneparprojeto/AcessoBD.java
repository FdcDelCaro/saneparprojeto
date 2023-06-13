package Simulador.saneparprojeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AcessoBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sanepar2.db";

    public AcessoBD(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarTabelaLeituras = "CREATE TABLE leituras (id INTEGER PRIMARY KEY AUTOINCREMENT, leituraatualizada TEXT)";
        String criarTabelaConsumo = "CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, listaconsumo TEXT)";

        db.execSQL(criarTabelaLeituras);
        db.execSQL(criarTabelaConsumo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getUltimaLeitura() {
        String leituraAnterior = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT leituraatualizada FROM leituras ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            leituraAnterior = cursor.getString(cursor.getColumnIndex("leituraatualizada"));
        }
        cursor.close();
        db.close();
        return leituraAnterior;
    }

    public void salvarLeituraAtual(String leitura) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("leituraatualizada", leitura);
        db.insert("leituras", null, values);
        db.close();
    }

    public List<String> getUltimoConsumo() {
        String metros = "m3";
        List<String> listaConsumo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT listaconsumo FROM consumo";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String consumido = cursor.getString(cursor.getColumnIndex("listaconsumo"));
                listaConsumo.add(consumido);
            }
            cursor.close();
        }
        db.close();

        return listaConsumo;

    }



    public void salvarConsumo(String consumo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("listaconsumo", consumo);
        db.insert("consumo", null, values);
        db.close();
    }
}