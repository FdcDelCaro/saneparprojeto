package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Lista extends AppCompatActivity {
    ListView listaconsumo;
    ArrayAdapter<String> listaconsumovetor;
    AcessoBD acessoBD;
    Button voltarpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        voltarpage = findViewById(R.id.voltar);
        listaconsumo = findViewById(R.id.consumolist);
        acessoBD = new AcessoBD(Lista.this);

        listaconsumovetor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaconsumo.setAdapter(listaconsumovetor);

        List<String> listaConsumo = acessoBD.getUltimoConsumo();

        listaconsumovetor.addAll(listaConsumo);

        voltarpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Lista.this, Telahome.class);
                startActivity(in);


            }
        });
    }



}
