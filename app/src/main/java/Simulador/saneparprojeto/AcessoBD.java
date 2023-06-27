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

    public AcessoBD(@Nullable Context context) {super(context, NOMEBANCO, null, 1);}
    //É chamado na primeira vez que o banco de Dados(BD) é acessado.
    //Usado também para a criação do banco de dados
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

    //Atualiza a versão do BD.
    //Permite que usuários antigos e novos usem a aplicação mesmo com o BD sofrendo manutenção
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvarLeituraAtual(String leitura) {
        //essa funcao pega o valor obtido na classe Telahome, da minha leitura atual que chamei de (n1)
        SQLiteDatabase db = getWritableDatabase(); // metodo de escrita no bd
        // abaixo insere o valor recebido da Telahome.java na leitura em string e executa o sql com esse valor obtido.
        String sql = "INSERT INTO leituras (leituraatualizada) VALUES ('" + leitura + "')";
        db.execSQL(sql);

        db.close();
    }

    public String getUltimaLeitura() {
        String leituraAnterior = "";
        SQLiteDatabase db = getReadableDatabase(); // LEITURA DE BANCO DADOS
        String query = "SELECT leituraatualizada FROM leituras ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null); //consulta o banco de dados atravez da variavel querry e armazena o resultado no objeto Cursor
        if (cursor != null && cursor.moveToFirst()) {// objeto Cursor nao é nulo? resultou em algo?
            // moveTofirst -- retorna true no caso de haver registro(s( proveniente(s) da consulta
            // captura o valor da coluna leituraatualizada do cursor, com o // ID em ordem decrescente com o limite de, retornando assim o ultimo valor cadastrado
            leituraAnterior = cursor.getString(cursor.getColumnIndex("leituraatualizada"));//obtem o dado da coluna leitura atualizada como uma sring e a variavel leituraAnterior recebe essa consulta em string
        }
        cursor.close(); // encerra o cursor
        db.close();// encerra conexao bd.
        return leituraAnterior; // retorna o resutado do bd nessa string que sera chamada pela funcao em outra classe, na Telahome.java
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
                // Obtém o valor da coluna listaconsumo do cursor em string

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
