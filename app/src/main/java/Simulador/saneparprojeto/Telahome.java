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
    TextView teste2;
    AcessoBD acessoBD;

    Button Listaconsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        leitura_atual = findViewById(R.id.leitura1);
        leitura_anterior = findViewById(R.id.leitura2);
        consumosimulado = findViewById(R.id.resultconsum);
        resultado2 = findViewById(R.id.resultadofinal);
        Calcular = findViewById(R.id.Calcular);
        Listaconsumo = findViewById(R.id.listaconsumo);

        acessoBD = new AcessoBD(this);

        String leituraAnterior = acessoBD.getUltimaLeitura();
        leitura_anterior.setText(leituraAnterior);

        Listaconsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Telahome.this, Lista.class);
                startActivity(in);
            }
        });

        Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1 = leitura_atual.getText().toString();
                String n2 = leitura_anterior.getText().toString();
                double intervcons = 5; // intervalo de taxas de consumo em metros cúbicos
                double intervconsa30 = 10; // intervalo de taxas de consumo em metros cúbicos acima de 30

                // Valores atualizados
                double taxamin = 50.42;
                double taxa1 = 1.56; // de 6 a 10m³
                double taxa2 = 8.69; // de 11 a 15m³
                double taxa3 = 8.73; // de 16 a 20m³
                double taxa4 = 8.81; // de 21 a 30m³
                double taxa5 = 14.90; // acima de 30m³

                // Valores atualizados do esgoto (85% do valor da água)
                double esgoto1 = 42.85 / 5; // valor da taxa de esgoto para cada m³, considerando 0 a 5m³.
                double esgoto2 = 6.63 / 5;  // mesma coisa, porém com taxa de 6 a 10m³
                double esgoto3 = 36.93 / 5; // 11 a 15m³
                double esgoto4 = 37.10 / 5; // 16 a 20m³
                double esgoto5 = 74.88 / 10; // 21 a 30m³
                double esgoto6 = 12.66; // acima de 30m³, cada taxa de esgoto por metro cúbico

                double preco;
                double num1 = Double.parseDouble(n1);
                double num2 = Double.parseDouble(n2);
                double n3 = num1 - num2;
                double consumo = n3;

                consumosimulado.setText(String.valueOf("O seu consumo foi: " + consumo + "m³" + "\n" + "1m³ equivale a uma caixa d'água de mil litros"));

                acessoBD.salvarLeituraAtual(n1); // abaixo comentario
                // manda o valor para a funcao na classe Acessobd.java
                // que fara as tratativas de adicionar no bd

                acessoBD.salvarConsumo(String.valueOf(consumo));// abaixo comentario
                // manda o valor para a funcao na classe Acessobd.java
                // que fara as tratativas de adicionar no bd

                if (consumo <= 5) {
                    preco = taxamin + esgoto1 * intervcons;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));

                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido", consumo);

                    startActivity(intent);
                } else if (consumo <= 10) {
                    preco = taxamin + (consumo - intervcons) * taxa1 + esgoto1 * intervcons + (consumo - intervcons) * esgoto2;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido", consumo);

                    startActivity(intent);
                    startActivity(intent);
                } else if (consumo <= 15) {
                    preco = taxamin + intervcons * taxa1 + (consumo - 10) * taxa2 + esgoto1 * intervcons + esgoto2 * intervcons + (consumo - 10) * esgoto3;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido", consumo);

                    startActivity(intent);
                } else if (consumo <= 20) {
                    preco = taxamin + intervcons * taxa1 + intervcons * taxa2 + (consumo - 15) * taxa3 + esgoto1 * intervcons + esgoto2 * intervcons + esgoto3 * intervcons + (consumo - 15) * esgoto4;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido", consumo);

                    startActivity(intent);
                } else if (consumo <= 30) {
                    preco = taxamin + intervcons * taxa1 + intervcons * taxa2 + intervcons * taxa3 + (consumo - 20) * taxa4 + esgoto1 * intervcons + esgoto2 * intervcons + esgoto3 * intervcons + esgoto4 * intervcons + (consumo - 20) * esgoto5;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido", consumo);

                    startActivity(intent);
                } else if (consumo > 30) {
                    preco = taxamin + intervcons * taxa1 + intervcons * taxa2 + intervcons * taxa3 + intervconsa30 * taxa4 + (consumo - 30) * taxa5 + esgoto1 * intervcons + esgoto2 * intervcons + esgoto3 * intervcons + esgoto4 * intervcons + esgoto5 * intervconsa30 + (consumo - 30) * esgoto6;
                    resultado2.setText(String.valueOf("O preço do consumo é: R$" + preco));
                    Intent intent = new Intent(Telahome.this, Resultadocalc.class);
                    intent.putExtra("resultadofinal", preco);
                    intent.putExtra("consumido", consumo);

                    startActivity(intent);
                }
            }
        });
    }
}
