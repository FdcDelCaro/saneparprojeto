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
    Button Listaconsumo;
    AcessoBD acessoBD;

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

        String leituraAnterior = acessoBD.obterUltimaLeitura();
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
                double esgoto0 = 42.85; // taxa fixa até 5m2
                double esgoto1 = 42.85 / 5; // valor da taxa de esgoto para cada m³, considerando 0 a 5m³, mais util para os calculos
                double esgoto2 = 6.63 / 5;  // mesma coisa, porém com taxa de 6 a 10m³
                double esgoto3 = 36.93 / 5; // 11 a 15m³
                double esgoto4 = 37.10 / 5; // 16 a 20m³
                double esgoto5 = 74.89 / 10; // 21 a 30m³
                double esgoto6 = 12.66 ;  // acima de 30m³

                if (n1.isEmpty() || n2.isEmpty()) {
                    return;
                }

                double num1 = Double.parseDouble(n1);
                double num2 = Double.parseDouble(n2);
                double soma = 0;
                double soma_esgoto = 0;
                double valortotal = 0;
                double consumo = 0;

                if (num1 > num2) {
                    consumo = num1 - num2;

                    if (consumo <= 5) {
                        soma = taxamin;
                        soma_esgoto = esgoto0;
                    } else if (consumo <= 10) {
                        soma = taxamin + taxa1 * (consumo - 5);
                        soma_esgoto = esgoto1 * 5 + esgoto2 * (consumo - 5);
                    } else if (consumo <= 15) {
                        soma = taxamin + taxa1 * intervcons + taxa2 * (consumo - 10);
                        soma_esgoto = esgoto1 * 5 + esgoto2 * 5 + esgoto3 * (consumo - 10);
                    } else if (consumo <= 20) {
                        soma = taxamin + taxa1 * intervcons + taxa2 * intervcons + taxa3 * (consumo - 15);
                        soma_esgoto = esgoto1 * 5 + esgoto2 * 5 + esgoto3 * 5 + esgoto4 * (consumo - 15);
                    } else if (consumo <= 30) {
                        soma = taxamin + taxa1 * intervcons + taxa2 * intervcons + taxa3 * intervcons + taxa4 * (consumo - 20);
                        soma_esgoto = esgoto1 * 5 + esgoto2 * 5 + esgoto3 * 5 + esgoto4 * 10 + esgoto5 * (consumo - 20);
                    } else {
                        soma = taxamin + taxa1 * intervcons + taxa2 * intervcons + taxa3 * intervcons + taxa4 * intervconsa30 + taxa5 * (consumo - 30);
                        soma_esgoto = esgoto1 * 5 + esgoto2 * 5 + esgoto3 * 5 + esgoto4 * 10 + esgoto5 * 10 + esgoto6 * (consumo - 30);
                    }

                    valortotal = soma + soma_esgoto;
                    acessoBD.salvarConsumo(consumo, valortotal);
                    acessoBD.salvarLeituraAtual(n1);
                    double caixas = consumo * 1000 / 500;

                    consumosimulado.setText(String.format("Você consumiu %.2f m³ \n\n Equivalente a %.1f Caixas d'agua",consumo,caixas));

                    resultado2.setText("R$ " + String.format("Total desta leitura %.2f", valortotal));
                } else {
                    consumosimulado.setText(" ");
                    resultado2.setText("Verifique os campos digitados");
                }
            }
        });
    }
}
