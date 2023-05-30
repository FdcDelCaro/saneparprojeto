package Simulador.saneparprojeto;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AcessoBD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fatura.db";


    public AcessoBD(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela para armazenar as leituras
        String createTableQuery = "CREATE TABLE leituras (id INTEGER PRIMARY KEY AUTOINCREMENT, leituraatualizada TEXT)";
        db.execSQL(createTableQuery);

        String createTableQuery2 = "CREATE TABLE consumo (id INTEGER PRIMARY KEY AUTOINCREMENT, listaconsumo INTEGER)";
        db.execSQL(createTableQuery2);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
