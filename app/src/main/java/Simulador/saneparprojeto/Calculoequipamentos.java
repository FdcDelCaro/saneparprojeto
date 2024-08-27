package Simulador.saneparprojeto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Calculoequipamentos extends AppCompatActivity {

    private EditText Leitura_inicial;
    private EditText Leitura_final;
    private EditText equipmentName; // Novo campo para o nome do equipamento
    private Button addEquipmentButton; // Novo botão para adicionar equipamento
    private Button calculateButton;
    private TextView resultText;
    private AcessoBD acessoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculoequipamentos);

        Leitura_inicial = findViewById(R.id.leiturainicial);
        Leitura_final = findViewById(R.id.final_reading);
        equipmentName = findViewById(R.id.equipment_name); // Inicializa o novo campo
        addEquipmentButton = findViewById(R.id.add_equipment_button); // Inicializa o novo botão
        calculateButton = findViewById(R.id.calculate_button);
        resultText = findViewById(R.id.result_text);
        acessoBD = new AcessoBD(this);

        addEquipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEquipment();
                calculateConsumption();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateConsumption();
            }
        });
    }

    private void addEquipment() {
        String nomeEquipamento = equipmentName.getText().toString();

        if (nomeEquipamento.isEmpty()) {
            resultText.setText("Por favor, insira o nome do equipamento.");
            return;
        }

        String inicial = Leitura_inicial.getText().toString();
        String final_l = Leitura_final.getText().toString();

        if (inicial.isEmpty() || final_l.isEmpty()) {
            resultText.setText("Por favor, insira ambas as leituras.");
            return;
        }

        try {
            double initial = Double.parseDouble(inicial);
            double finalagua = Double.parseDouble(final_l);

            if (finalagua < initial) {
                resultText.setText("A leitura final deve ser maior que a leitura inicial.");
                return;
            }

            double consumo = finalagua - initial;
            double consumo_por_litros = consumo * 1000;
            double cost_m3_ate_5m3_esgoto = 42.85 / 5;
            double cost_m3_ate_5m3 = 50.42 / 5;
            double consumo_5m3_esgotofinal = consumo * cost_m3_ate_5m3_esgoto;
            double cost1 = consumo * cost_m3_ate_5m3;
            double cost = cost1 + consumo_5m3_esgotofinal;

            DecimalFormat df = new DecimalFormat("#.##");

            String consumo_eq = "Consumo: " + df.format(consumo_por_litros) + " litros\nCusto: R$ " + df.format(cost);
            resultText.setText(consumo_eq);

            // Exibir alerta se o consumo ultrapassar 45 litros
            if (consumo_por_litros > 45) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Calculoequipamentos.this);
                builder.setTitle("Alerta de Consumo")
                        .setMessage("O consumo de " + df.format(consumo_por_litros) + " litros ultrapassou o limite de 45 litros.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Ação ao pressionar OK (pode ser deixado em branco)
                            }
                        })
                        .show();
            }

            long id = acessoBD.adicionarEquipamento(nomeEquipamento, consumo_por_litros, cost);

            if (id == -1) {
                resultText.setText("Erro ao adicionar equipamento.");
            } else {
                resultText.setText("Equipamento adicionado com sucesso.\n" + consumo_eq);
            }

        } catch (NumberFormatException e) {
            resultText.setText("Por favor, insira valores numéricos válidos.");
        }
    }

    private void calculateConsumption() {
        String inicial = Leitura_inicial.getText().toString();
        String final_l = Leitura_final.getText().toString();

        if (inicial.isEmpty() || final_l.isEmpty()) {
            resultText.setText("Por favor, insira ambas as leituras.");
            return;
        }

        try {
            double initial = Double.parseDouble(inicial);
            double finalagua = Double.parseDouble(final_l);

            if (finalagua < initial) {
                resultText.setText("A leitura final deve ser maior que a leitura inicial.");
                return;
            }

            double consumption = finalagua - initial;
            double consumptionInLiters = consumption * 1000;
            double cost_m3_ate_5m3_esgoto = 42.85 / 5;
            double cost_m3_ate_5m3 = 50.42 / 5;
            double consumo_5m3_esgotofinal = consumption * cost_m3_ate_5m3_esgoto;
            double cost1 = consumption * cost_m3_ate_5m3;
            double cost = cost1 + consumo_5m3_esgotofinal;

            DecimalFormat df = new DecimalFormat("#.##");

            String consumo_eq = "Consumo: " + df.format(consumptionInLiters) + " litros\nCusto: R$ " + df.format(cost);
            resultText.setText(consumo_eq);

            // Exibir alerta se o consumo ultrapassar 45 litros
            AlertDialog.Builder builder = new AlertDialog.Builder(Calculoequipamentos.this);

            // Cria uma TextView para a mensagem
            TextView messageView = new TextView(Calculoequipamentos.this);
            messageView.setText("O consumo de " + df.format(consumptionInLiters) + " litros ultrapassou os 45 litros. Se ligue nessa dica: \n" +
                    "Banho de ducha por 15 minutos, com o registro meio aberto, consome 135 litros de água. Se você fechar o registro ao se ensaboar, e reduzir o tempo do banho para 5 minutos, seu consumo cai para 45 litros. A redução é de 90 litros de água, o equivalente a 360 copos de água com 250 ml.");
            messageView.setPadding(20, 20, 20, 20);

            // Adiciona a mensagem em um ScrollView
            ScrollView scrollView = new ScrollView(Calculoequipamentos.this);
            scrollView.addView(messageView);

            builder.setTitle("Alerta de Consumo")
                    .setView(scrollView)  // Define o ScrollView como a visualização do diálogo
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Ação ao pressionar OK (pode ser deixado em branco)
                        }
                    })
                    .show();
            // Atualizar o consumo do equipamento
            String nomeEquipamento = equipmentName.getText().toString();
            if (!nomeEquipamento.isEmpty()) {
                acessoBD.atualizarConsumoEquipamento(nomeEquipamento, consumptionInLiters, cost);
            }

        } catch (NumberFormatException e) {
            resultText.setText("Por favor, insira valores numéricos válidos.");
        }
    }
}
