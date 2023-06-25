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
        // Criação da tabela leituras
        String criarTabelaLeituras = "CREATE TABLE leituras (id INTEGER PRIMARY KEY AUTOINCREMENT, leituraatualizada TEXT)";
        // Criação da tabela consumo
        String criarTabelaConsumo = "CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, listaconsumo TEXT)";

        // Executa os sqls para criar as tabelas no banco de dados
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
            // captura o valor da coluna leituraatualizada do cursor, com o // ID em ordem decrescente com o limite de, retornando assim o ultimo valor cadastrado
            leituraAnterior = cursor.getString(cursor.getColumnIndex("leituraatualizada"));
        }
        cursor.close();
        db.close();
        return leituraAnterior; // retorna o resutado do bd nessa string que sera chamada pela funcao em outra classe, na Telahome.java
    }

    public void salvarLeituraAtual(String leitura) {
        //essa funcao pega o valor obtido na classe Telahome, da minha leitura atual que chamei de (n1)
        SQLiteDatabase db = getWritableDatabase(); // metodo de escrita no bd
        // abaixo insere o valor recebido da Telahome.java na leitura em string e executa o sql com esse valor obtido.
        String sql = "INSERT INTO leituras (leituraatualizada) VALUES ('" + leitura + "')";
        db.execSQL(sql);

        db.close();
    }

    public void salvarConsumo(String consumo) {
        // mesma coisa da funcao de cima, porem salvando um valor vindo da classe Telahome.java,
        // mas em outra tabela, que sera minha lista de consumo.
        SQLiteDatabase db = getWritableDatabase();

        String insertQuery = "INSERT INTO consumo (listaconsumo) VALUES ('" + consumo + "')";
        db.execSQL(insertQuery);

        db.close();

    }

    public List<String> listaconsumido() {
        String metros = "m3";
        List<String> listaConsumo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT id, " +
                "listaconsumo FROM " +
                "consumo ORDER BY id DESC;";
        // aqui pesquisa os dados da tabela consumo,
        // trazendo id e a coluna lista em ordem descrescente,
        // assim me mostra o do mais recente
        // para o mais antigo nos consumos.
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                String consumido =
                        cursor.getString
                        (cursor.getColumnIndex
                                ("listaconsumo"));
                // Obtém o valor da coluna listaconsumo do cursor

                listaConsumo.add(consumido);
                // Adiciona o valor de consumo à lista
            }
            cursor.close();
        }
        db.close();

        return listaConsumo;
        //retorna esse valor para eu
        // poder adicionar em outra classe.
        // nesse caso na minha de resultados em um list view

    }


}
