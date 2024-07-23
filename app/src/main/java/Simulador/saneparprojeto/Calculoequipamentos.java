package Simulador.saneparprojeto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
