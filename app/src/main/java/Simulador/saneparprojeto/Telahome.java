package Simulador.saneparprojeto;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Telahome extends AppCompatActivity {

    EditText leitura_atual;

    EditText leitura_anterior;

    TextView consumosimulado;


    TextView resultado2;

    Button Calcular;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        leitura_atual = findViewById(R.id.leitura1);
        leitura_anterior = findViewById(R.id.leitura2);
        consumosimulado = findViewById(R.id.resultconsum);
        resultado2 = findViewById(R.id.resultadofinal);
        Calcular = findViewById(R.id.Calcular);

        if (savedInstanceState != null) {
            consumosimulado.setText(savedInstanceState.getString("comsumom3"));
            resultado2.setText(savedInstanceState.getString("resultado2"));
        }


        Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1 = leitura_atual.getText().toString();
                String n2 = leitura_anterior.getText().toString();
                double intervcons = 5; // intervalo de taxas de consumo em metros cubicos
                double intervconsa30 = 10;//intervalo de taxas de consumo em metros cubicos acima de 30

                double taxamin = 45.25;
                double taxa1 = 1.4; //de 5 a 10m3
                double taxa2 = 7.8; //de 6 a 15m3
                double taxa3 = 7.84; //de 16 a 20
                double taxa4 = 7.90; //de 21 a 30m3
                double taxa5 = 13.37; // acima de 30

                double esgoto1 = 38.46 / 5; // valor da taxa de esgoto para cada m³, considerando 0 a 5m3.
                double esgoto2 = 5.95 / 5;  // mesma coisa porem com taxa de 6 a 10m3
                double esgoto3 = 33.15 / 5; // 11 a 15
                double esgoto4 = 33.32 / 5; // 16 a 20
                double esgoto5 = 67.15 / 10; // 21 a 30
                double esgoto6 = 11.36; // acima de 30 cada taxa de esgoto por metro cubico

                double preco;
                double num1 = Float.parseFloat(n1);
                double num2 = Float.parseFloat(n2);
                double n3 = num1 - num2;
                double consumo = n3;


                consumosimulado.setText(String.valueOf("o seu consumo foi: " + consumo+"m3"+"\n"+"1m³ equivale a uma caixa d'água de mil litros"));



                if (consumo <= 5) {
                    preco = taxamin + esgoto1 * intervcons;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));

                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido",consumo);

                    startActivity(intent);

                } else if (consumo <= 10) {
                    preco = taxamin + (consumo - intervcons) * taxa1 + esgoto1 * intervcons + (consumo - intervcons) * esgoto2;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido",consumo);

                    startActivity(intent);

                } else if (consumo <= 15) {
                    preco = taxamin + intervcons * taxa1 + (consumo - 10) * taxa2 + esgoto1 * intervcons + esgoto2 * intervcons + (consumo - 10) * esgoto3;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido",consumo);

                    startActivity(intent);

                } else if (consumo <= 20) {
                    preco = taxamin + intervcons * taxa1 + intervcons * taxa2 + (consumo - 15) * taxa3 + esgoto1 * intervcons + esgoto2 * intervcons + esgoto3 * intervcons + (consumo - 15) * esgoto4;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido",consumo);

                    startActivity(intent);

                } else if (consumo <= 30) {
                    preco = taxamin + intervcons * taxa1 + intervcons * taxa2 + intervcons * taxa3 + (consumo - 20) * taxa4 + esgoto1 * intervcons + esgoto2 * intervcons + esgoto3 * intervcons + esgoto4 * intervcons + (consumo - 20) * esgoto5;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido",consumo);

                    startActivity(intent);

                } else if (consumo > 30) {
                    preco = taxamin + intervcons * taxa1 + intervcons * taxa2 + intervcons * taxa3 + intervconsa30 * taxa4 + (consumo - 30) * taxa5 + esgoto1 * intervcons + esgoto2 * intervcons + esgoto3 * intervcons + esgoto4 * intervcons + esgoto5 * intervconsa30 + (consumo - 30) * esgoto6;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido",consumo);

                    startActivity(intent);

                }


            }



        });
    }
}


