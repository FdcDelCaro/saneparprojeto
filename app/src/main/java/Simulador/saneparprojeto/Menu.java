package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button leitura;
    Button lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        leitura=findViewById(R.id.leitura);
        lista=findViewById(R.id.list);

        leitura.setOnClickListener(new View.OnClickListener() {
            //botao que do menu que ira enviar desta tela para a telahome, que é minha tela de calculo
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Menu.this, Telahome.class);
                startActivity(in);
            }
        });

        lista.setOnClickListener(new View.OnClickListener() {
            //Botao que envia para minha lista de consumo, que esta na classe LISTA.JAVA
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Menu.this, Lista.class);
                startActivity(in);
            }
        });
    }
}