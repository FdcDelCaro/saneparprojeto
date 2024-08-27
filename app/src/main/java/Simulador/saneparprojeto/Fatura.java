package Simulador.saneparprojeto;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Fatura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatura);
    }

    public void showTip1(View view) {
        Toast.makeText(this, "Telefone da Sanepar: 0800-200-0115", Toast.LENGTH_SHORT).show();
    }

    public void showTip2(View view) {
        Toast.makeText(this, "NOME DO CLIENTE: nome do responsável pela conta", Toast.LENGTH_SHORT).show();
    }

    public void showTip3(View view) {
        Toast.makeText(this, "MATRÍCULA: número que identifica a ligação de água/esgoto de seu imóvel", Toast.LENGTH_SHORT).show();
    }

    public void showTip4(View view) {
        Toast.makeText(this, "ENDEREÇO: nome da rua, número do imóvel e complemento de localização", Toast.LENGTH_SHORT).show();
    }

    public void showTip5(View view) {
        Toast.makeText(this, "Nº LADO - Nº FRENTE: número do imóvel ao lado e em frente onde está localizada a ligação", Toast.LENGTH_SHORT).show();
    }

    public void showTip6(View view) {
        Toast.makeText(this, "CEP: Código de Endereçamento Postal", Toast.LENGTH_SHORT).show();
    }

    public void showTip7(View view) {
        Toast.makeText(this, "LOCAL: município onde se localiza a ligação", Toast.LENGTH_SHORT).show();
    }

    public void showTip8(View view) {
        Toast.makeText(this, "ROTEIRO DE LEITURA: sequência de números codificados para a execução da leitura", Toast.LENGTH_SHORT).show();
    }

    public void showTip9(View view) {
        Toast.makeText(this, "HIDRÔMETRO: código que identifica o número do hidrômetro da ligação de seu imóvel", Toast.LENGTH_SHORT).show();
    }

    public void showTip10(View view) {
        Toast.makeText(this, "CAT – RES – COM – IND – UTP – POP: identifica o tipo de ocupação, se residencial, comercial, industrial, utilidade pública ou poder público, e a quantidade de imóveis abastecidos", Toast.LENGTH_SHORT).show();
    }

    public void showTip11(View view) {
        Toast.makeText(this, "QUALIDADE DA ÁGUA DISTRIBUÍDA: mostra o número de amostras coletadas no mês, identificando as exigidas, realizadas e que atenderam à legislação, de acordo com os parâmetros estabelecidos pelo Ministério da Saúde, descrevendo a conclusão geral das análises", Toast.LENGTH_SHORT).show();
    }

    public void showTip12(View view) {
        Toast.makeText(this, "HISTÓRICO DE PAGAMENTOS: declaração de quitação anual de débitos, lista as contas que foram pagas no ano anterior e no atual", Toast.LENGTH_SHORT).show();
    }

    public void showTip13(View view) {
        Toast.makeText(this, "Campo de descrição dos serviços que estão sendo cobrados, parcelas e valores, podendo ainda conter serviços não pertencentes à Sanepar, como a taxa de coleta de lixo – que é repassada à Prefeitura Municipal – e o descritivo dos valores de água/esgoto a serem pagos, de acordo com a categoria e número de economias", Toast.LENGTH_SHORT).show();
    }

    public void showTip14(View view) {
        Toast.makeText(this, "HISTÓRICO DE CONSUMO/m³: mostra o consumo medido dos últimos 11 meses, podendo estar identificado com R – refaturado (contas refaturadas devido a problemas de consumo) ou A – atribuído (contas com ausência de leitura por impossibilidade da mesma)", Toast.LENGTH_SHORT).show();
    }

    public void showTip15(View view) {
        Toast.makeText(this, "DIAS DE CONSUMO: mostra a quantidade de dias entre a data da leitura do mês anterior e a data da leitura do mês atual", Toast.LENGTH_SHORT).show();
    }

    public void showTip16(View view) {
        Toast.makeText(this, "DATA LEITURA", Toast.LENGTH_SHORT).show();
    }

    public void showTip17(View view) {
        Toast.makeText(this, "LEITURA ANTERIOR", Toast.LENGTH_SHORT).show();
    }

    public void showTip18(View view) {
        Toast.makeText(this, "LEITURA ATUAL", Toast.LENGTH_SHORT).show();
    }

    public void showTip19(View view) {
        Toast.makeText(this, "CONSUMO/m³: mostra o volume em metros cúbicos medido, podendo ser representado pelo consumo real ou atribuído", Toast.LENGTH_SHORT).show();
    }

    public void showTip20(View view) {
        Toast.makeText(this, "REFERÊNCIA: mostra o mês e o ano a que se refere a conta", Toast.LENGTH_SHORT).show();
    }

    public void showTip21(View view) {
        Toast.makeText(this, "MOTIVO DA AUSÊNCIA DE LEITURA: mostra o motivo pelo qual, excepcionalmente, não houve leitura", Toast.LENGTH_SHORT).show();
    }

    public void showTip22(View view) {
        Toast.makeText(this, "MÉDIA DE CONSUMO/M³ ÚLTIMOS 5 MESES", Toast.LENGTH_SHORT).show();
    }

    public void showTip23(View view) {
        Toast.makeText(this, "VENCIMENTO: data do vencimento da conta", Toast.LENGTH_SHORT).show();
    }

    public void showTip24(View view) {
        Toast.makeText(this, "PREVISÃO PRÓXIMA LEITURA: data em que está prevista a leitura no mês seguinte", Toast.LENGTH_SHORT).show();
    }

    public void showTip25(View view) {
        Toast.makeText(this, "ÁGUA: valor do serviço de abastecimento de água", Toast.LENGTH_SHORT).show();
    }

    public void showTip26(View view) {
        Toast.makeText(this, "ESGOTO: valor do serviço de esgotamento sanitário", Toast.LENGTH_SHORT).show();
    }

    public void showTip27(View view) {
        Toast.makeText(this, "SERVIÇOS: mostra o valor dos demais serviços faturados na conta", Toast.LENGTH_SHORT).show();
    }

    public void showTip28(View view) {
        Toast.makeText(this, "TOTAL: somatória dos valores de água, esgoto e serviços faturados", Toast.LENGTH_SHORT).show();
    }

    public void showTip29(View view) {
        Toast.makeText(this, "Campo destinado a mensagens informativas aos clientes", Toast.LENGTH_SHORT).show();
    }

    public void showTip30(View view) {
        Toast.makeText(this, "Campo destinado ao código de barras utilizado para o pagamento da conta", Toast.LENGTH_SHORT).show();
    }

    public void showTip31(View view) {
        Toast.makeText(this, "Campo destinado ao QR Code para pagamento via Pix", Toast.LENGTH_SHORT).show();
    }
}
