package Simulador.saneparprojeto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Calculoequipamentos extends AppCompatActivity {

    private EditText initialReading;
    private EditText finalReading;
    private Button calculateButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculoequipamentos);

        initialReading = findViewById(R.id.leiturainicial);
        finalReading = findViewById(R.id.final_reading);
        calculateButton = findViewById(R.id.calculate_button);
        resultText = findViewById(R.id.result_text);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateConsumption();
            }
        });
    }

    private void calculateConsumption() {
        String initialReadingStr = initialReading.getText().toString();
        String finalReadingStr = finalReading.getText().toString();

        // Check if the input is valid
        if (initialReadingStr.isEmpty() || finalReadingStr.isEmpty()) {
            resultText.setText("Por favor, insira ambas as leituras.");
            return;
        }

        double initial = Double.parseDouble(initialReadingStr);
        double end = Double.parseDouble(finalReadingStr);

        // Check if final reading is greater than initial reading
        if (end < initial) {
            resultText.setText("A leitura final deve ser maior que a leitura inicial.");
            return;
        }

        double consumption = end - initial;  // em m³
        double consumptionInLiters = consumption * 1000;  // Convertendo para litros

        // Cálculo do custo
        double costPerM3 = 50.42 / 5;  // R$ 10,084 por m³
        double cost = consumption * costPerM3;  // Custo total

        // Formatação dos resultados
        DecimalFormat df = new DecimalFormat("#.##");

        String result = "Consumo: " + df.format(consumptionInLiters) + " litros\nCusto: R$ " + df.format(cost);
        resultText.setText(result);
    }
}