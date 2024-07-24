package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class Lista extends AppCompatActivity {
    ListView listaconsumo;
    ListView listaEquipamentos;
    ListView listaEquipamentos2;
    ArrayAdapter<String> listaconsumoVetor;
    ArrayAdapter<String> listaEquipamentosVetor;
    ArrayAdapter<String> listaEquipamentosVetor2;
    AcessoBD acessoBD;
    Button voltarpage;
    Button consultar;
    EditText dataInicio;
    EditText dataFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        voltarpage = findViewById(R.id.voltar);
        listaconsumo = findViewById(R.id.consumo_list);
        listaEquipamentos = findViewById(R.id.equipamentos_list);
        listaEquipamentos2 = findViewById(R.id.equipamentos_list2);
        dataInicio = findViewById(R.id.data_inicio);
        dataFim = findViewById(R.id.data_fim);
        consultar = findViewById(R.id.consultar);

        acessoBD = new AcessoBD(Lista.this);

        listaconsumoVetor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaEquipamentosVetor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaEquipamentosVetor2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listaconsumo.setAdapter(listaconsumoVetor);
        listaEquipamentos.setAdapter(listaEquipamentosVetor);
        listaEquipamentos2.setAdapter(listaEquipamentosVetor2);

        // Carregar os dados iniciais
        carregarDados();

        voltarpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Lista.this, Telahome.class);
                startActivity(in);
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inicio = dataInicio.getText().toString();
                String fim = dataFim.getText().toString();

                List<String> listaConsumoPorPeriodo = acessoBD.listarConsumoPorPeriodo(inicio, fim);

                // Atualiza a ListView com o consumo filtrado por data
                if (listaConsumoPorPeriodo != null) {
                    listaEquipamentosVetor2.clear(); // Usar o ArrayAdapter para limpar
                    listaEquipamentosVetor2.addAll(listaConsumoPorPeriodo); // Adicionar novos itens
                }
            }
        });
    }

    private void carregarDados() {
        List<String> listaConsumo = acessoBD.listarConsumos();
        List<String> listaConsumoEquipamentos = acessoBD.listarConsumoEquipamentos();
        List<String> listaConsumoEquipamentos2 = acessoBD.listarConsumoEquipamentosDetalhado();

        if (listaConsumo != null) {
            listaconsumoVetor.clear();
            listaconsumoVetor.addAll(listaConsumo);
        }

        if (listaConsumoEquipamentos != null) {
            listaEquipamentosVetor.clear();
            listaEquipamentosVetor.addAll(listaConsumoEquipamentos);
        }

        if (listaConsumoEquipamentos2 != null) {
            listaEquipamentosVetor2.clear();
            listaEquipamentosVetor2.addAll(listaConsumoEquipamentos2);
        }
    }
}