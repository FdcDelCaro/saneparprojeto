package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button leitura;
    Button lista;
    Button sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        leitura = findViewById(R.id.leitura);
        lista = findViewById(R.id.list);
        sair = findViewById(R.id.sair);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        leitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Menu.this, Telahome.class);
                startActivity(in);
            }
        });

        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Menu.this, Lista.class);
                startActivity(in);
            }
        });
    }
}
