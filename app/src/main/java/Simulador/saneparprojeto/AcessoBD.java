package Simulador.saneparprojeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DecimalFormat;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Classe para gerenciamento do banco de dados SQLite do aplicativo
public class AcessoBD extends SQLiteOpenHelper {

    // Nome do banco de dados
    private static final String NOME_BANCO = "s.db";

    // Construtor da classe
    public AcessoBD(@Nullable Context contexto) {
        super(contexto, NOME_BANCO, null, 1);
    }

    // Método chamado para criar as tabelas do banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL para criar a tabela de leituras
        String criarTabelaLeituras = "CREATE TABLE leituras (id INTEGER PRIMARY KEY AUTOINCREMENT, leitura_atualizada TEXT)";
        // SQL para criar a tabela de consumo
        String criarTabelaConsumo = "CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, consumo DOUBLE, custo DOUBLE)";
        // SQL para criar a tabela de usuários
        String criarTabelaUsuario = "CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT UNIQUE, nome_usuario TEXT UNIQUE, senha TEXT)";
        // SQL para criar a tabela de consumo de equipamentos
        String criarTabelaConsumoEquipamentos = "CREATE TABLE consumo_equipamentos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, consumo DOUBLE, custo DOUBLE)";
        // Executa os comandos SQL para criar as tabelas
        db.execSQL(criarTabelaLeituras);
        db.execSQL(criarTabelaConsumo);
        db.execSQL(criarTabelaUsuario);
        db.execSQL(criarTabelaConsumoEquipamentos);
    }

    // Método chamado quando a versão do banco de dados é atualizada
    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        // Remove tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS leituras");
        db.execSQL("DROP TABLE IF EXISTS consumo");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS consumo_equipamentos");
        // Cria novas tabelas
        onCreate(db);
    }

    // Verifica se um usuário existe com o nome de usuário e senha fornecidos
    public boolean verificarUsuario(String nomeUsuario, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        // SQL para verificar o usuário
        String consulta = "SELECT * FROM usuario WHERE nome_usuario = ? AND senha = ?";
        // Executa a consulta
        Cursor cursor = db.rawQuery(consulta, new String[]{nomeUsuario, senha});
        // Verifica se há resultados
        boolean existeUsuario = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existeUsuario;
    }

    // Adiciona um novo usuário ao banco de dados
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

    // Salva a leitura atual no banco de dados
    public void salvarLeituraAtual(String leitura) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put("leitura_atualizada", leitura);
            db.insert("leituras", null, valores);
        } finally {
            db.close();
        }
    }

    // Recupera a última leitura registrada
    public String obterUltimaLeitura() {
        String leituraAnterior = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            // SQL para obter a última leitura
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

    // Salva o consumo e custo no banco de dados
    public void salvarConsumo(double consumo, double custo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put("consumo", consumo);
            valores.put("custo", custo);
            db.insert("consumo", null, valores);
        } finally {
            db.close();
        }
    }

    // Lista todos os consumos e custos registrados
    public List<String> listarConsumos() {
        List<String> listaConsumo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");

        try {
            // SQL para obter o consumo e custo
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

    // Salva o consumo e custo dos equipamentos no banco de dados
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

    // Lista todos os consumos e custos dos equipamentos, agrupados por nome
    public List<String> listarConsumoEquipamentos() {
        List<String> listaConsumoEquipamentos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");

        try {
            // SQL para obter o custo total por equipamento
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

    // Lista todos os consumos e custos dos equipamentos
    public List<String> listarConsumoEquipamentosDetalhado() {
        List<String> listaDetalhada = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");  // Ajuste a formatação conforme necessário

        try {
            // SQL para obter todos os registros de consumo de equipamentos
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

    // Adiciona um novo equipamento ao banco de dados
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

    // Atualiza o consumo e custo de um equipamento
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
}
