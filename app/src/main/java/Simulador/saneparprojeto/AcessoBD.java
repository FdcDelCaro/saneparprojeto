package Simulador.saneparprojeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DecimalFormat;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AcessoBD extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "s6956651515a.db";
    private static final SimpleDateFormat FORMATO_BRASILEIRO = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final SimpleDateFormat FORMATO_SQL = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public AcessoBD(@Nullable Context contexto) {
        super(contexto, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarTabelaLeituras = "CREATE TABLE leituras (id INTEGER PRIMARY KEY AUTOINCREMENT, leitura_atualizada TEXT, data DATE)";
        String criarTabelaConsumo = "CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, consumo DOUBLE, custo DOUBLE, data DATE)";
        String criarTabelaUsuario = "CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT UNIQUE, nome_usuario TEXT UNIQUE, senha TEXT)";
        String criarTabelaConsumoEquipamentos = "CREATE TABLE consumo_equipamentos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, consumo DOUBLE, custo DOUBLE)";
        String userpadrao = "INSERT INTO usuario (nome, email, nome_usuario, senha) VALUES ('Felipe', 'felipe@felipe.com', 'teste', 'teste')";
        db.execSQL(criarTabelaLeituras);
        db.execSQL(criarTabelaConsumo);
        db.execSQL(criarTabelaUsuario);
        db.execSQL(criarTabelaConsumoEquipamentos);
        db.execSQL(userpadrao);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        db.execSQL("DROP TABLE IF EXISTS leituras");
        db.execSQL("DROP TABLE IF EXISTS consumo");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS consumo_equipamentos");
        onCreate(db);
    }

    public boolean verificarUsuario(String nomeUsuario, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM usuario WHERE nome_usuario = ? AND senha = ?";
        Cursor cursor = db.rawQuery(consulta, new String[]{nomeUsuario, senha});
        boolean existeUsuario = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existeUsuario;
    }

    public long adicionarUsuario(String nome, String email, String nomeUsuario, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("email", email);
        valores.put("nome_usuario", nomeUsuario);
        valores.put("senha", senha);

        long resultado = db.insert("usuario", null, valores);
        db.close();
        return resultado;
    }

    public void salvarLeituraAtual(String leitura, String dataBrasileira) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put("leitura_atualizada", leitura);
            valores.put("data", converterParaFormatoSQL(dataBrasileira));
            db.insert("leituras", null, valores);
        } finally {
            db.close();
        }
    }

    private String converterParaFormatoSQL(String dataBrasileira) {
        try {
            Date data = FORMATO_BRASILEIRO.parse(dataBrasileira);
            return FORMATO_SQL.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String obterUltimaLeitura() {
        String leituraAnterior = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String consulta = "SELECT leitura_atualizada FROM leituras ORDER BY id DESC LIMIT 1";
            cursor = db.rawQuery(consulta, null);
            if (cursor != null && cursor.moveToFirst()) {
                leituraAnterior = cursor.getString(cursor.getColumnIndexOrThrow("leitura_atualizada"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return leituraAnterior;
    }

    public void salvarConsumo(double consumo, double custo, String data) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put("consumo", consumo);
            valores.put("custo", custo);
            valores.put("data", converterParaFormatoSQL(data));
            db.insert("consumo", null, valores);
        } finally {
            db.close();
        }
    }

    public List<String> listarConsumos() {
        List<String> listaConsumo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");

        try {
            String consulta = "SELECT consumo, custo FROM consumo ORDER BY id DESC";
            cursor = db.rawQuery(consulta, null);
            while (cursor != null && cursor.moveToNext()) {
                double consumo = cursor.getDouble(cursor.getColumnIndexOrThrow("consumo"));
                double custo = cursor.getDouble(cursor.getColumnIndexOrThrow("custo"));

                String consumoFormatado = formatoDecimal.format(consumo);
                String custoFormatado = formatoDecimal.format(custo);

                String item = "Consumo: " + consumoFormatado + " m³, Custo: R$ " + custoFormatado;
                listaConsumo.add(item);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return listaConsumo;
    }

    public void salvarConsumoEquipamento(double consumo, double custo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put("consumo", consumo);
            valores.put("custo", custo);
            db.insert("consumo_equipamentos", null, valores);
        } finally {
            db.close();
        }
    }

    public List<String> listarConsumoEquipamentos() {
        List<String> listaConsumoEquipamentos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");

        try {
            String consulta = "SELECT nome, SUM(custo) AS custo_total FROM consumo_equipamentos GROUP BY nome ORDER BY nome DESC";
            cursor = db.rawQuery(consulta, null);
            while (cursor != null && cursor.moveToNext()) {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                double custoTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("custo_total"));

                String custoTotalFormatado = formatoDecimal.format(custoTotal);

                String item = "Nome: " + nome + " Custo Total: R$ " + custoTotalFormatado;
                listaConsumoEquipamentos.add(item);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return listaConsumoEquipamentos;
    }

    public List<String> listarConsumoEquipamentosDetalhado() {
        List<String> listaDetalhada = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");

        try {
            String consulta = "SELECT nome, consumo, custo FROM consumo_equipamentos ORDER BY id DESC";
            cursor = db.rawQuery(consulta, null);

            while (cursor != null && cursor.moveToNext()) {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                double consumo = cursor.getDouble(cursor.getColumnIndexOrThrow("consumo"));
                double custo = cursor.getDouble(cursor.getColumnIndexOrThrow("custo"));

                String consumoFormatado = formatoDecimal.format(consumo);
                String custoFormatado = formatoDecimal.format(custo);

                String item = "Nome: " + nome + " Consumo: " + consumoFormatado + " m³, Custo: R$ " + custoFormatado;
                listaDetalhada.add(item);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return listaDetalhada;
    }

    public long adicionarEquipamento(String nome, double consumo, double custo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("consumo", consumo);
        valores.put("custo", custo);

        long resultado = db.insert("consumo_equipamentos", null, valores);
        db.close();
        return resultado;
    }

    public void atualizarConsumoEquipamento(String nome, double consumo, double custo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("consumo", consumo);
        valores.put("custo", custo);

        String clausulaWhere = "nome = ?";
        String[] argumentosWhere = { nome };

        db.update("consumo_equipamentos", valores, clausulaWhere, argumentosWhere);
        db.close();
    }

    public List<String> listarLeiturasPorPeriodo(String dataInicioBrasileira, String dataFimBrasileira) {
        List<String> listaLeituras = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            String dataInicioFormatada = converterParaFormatoSQL(dataInicioBrasileira);
            String dataFimFormatada = converterParaFormatoSQL(dataFimBrasileira);

            String consulta = "SELECT leitura_atualizada, data FROM leituras WHERE data BETWEEN ? AND ? ORDER BY data DESC";
            cursor = db.rawQuery(consulta, new String[]{dataInicioFormatada, dataFimFormatada});
            while (cursor != null && cursor.moveToNext()) {
                String leitura = cursor.getString(cursor.getColumnIndexOrThrow("leitura_atualizada"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));

                String item = "Leitura: " + leitura + ", Data: " + data;
                listaLeituras.add(item);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return listaLeituras;
    }

    public List<String> listarConsumoPorPeriodo(String dataInicioBrasileira, String dataFimBrasileira) {
        List<String> listaConsumo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            // Converte as datas para o formato SQL
            String dataInicioFormatada = converterParaFormatoSQL(dataInicioBrasileira);
            String dataFimFormatada = converterParaFormatoSQL(dataFimBrasileira);

            // Define a consulta SQL
            String consulta = "SELECT consumo, custo, data FROM consumo WHERE data BETWEEN ? AND ? ORDER BY data DESC";

            // Executa a consulta
            cursor = db.rawQuery(consulta, new String[]{dataInicioFormatada, dataFimFormatada});

            // Processa o resultado da consulta
            while (cursor != null && cursor.moveToNext()) {
                double consumo = cursor.getDouble(cursor.getColumnIndexOrThrow("consumo"));
                double custo = cursor.getDouble(cursor.getColumnIndexOrThrow("custo"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));

                String item = "Consumo: " + consumo + ", Custo: " + custo + ", Data: " + data;
                listaConsumo.add(item);
            }
        } finally {
            // Fecha o cursor e o banco de dados se não forem nulos
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return listaConsumo;
    }

}
