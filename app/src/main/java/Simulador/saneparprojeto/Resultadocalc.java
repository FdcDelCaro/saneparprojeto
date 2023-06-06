package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;







import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Resultadocalc extends AppCompatActivity {
    Button voltarpage;
    ListView listaconsumo;
    ArrayAdapter<String> listaconsumovetor;
    AcessoBD acessoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_tela);
        voltarpage = findViewById(R.id.voltar);
        listaconsumo = findViewById(R.id.consumolist);

        acessoBD = new AcessoBD(Resultadocalc.this);

        listaconsumovetor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaconsumo.setAdapter(listaconsumovetor);

        List<String> listaConsumo = acessoBD.getUltimoConsumo();

        listaconsumovetor.addAll(listaConsumo);


        // recuperando o resultado da intent da pagina principal calculator

        double resultado = getIntent().getDoubleExtra("resultadofinal", 0);
        double resultadocons = getIntent().getDoubleExtra("consumido", 0);
        //exibir o resultado em um textview

        TextView Resultado = findViewById(R.id.resultado);
        Resultado.setText("O valor de sua fatura: " + resultado);

        TextView Resultado2 = findViewById(R.id.resultadoconsumido);
        Resultado2.setText("Você consumiu: " + resultadocons + "m3" + "\n1 metro cubico equivale a 500 garrafas pets de 2 litro.\n" +
                "abaixo a sua lista de consumo em m3");


        voltarpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Resultadocalc.this, Telahome.class);
                startActivity(in);
            }
        });
    }



}

