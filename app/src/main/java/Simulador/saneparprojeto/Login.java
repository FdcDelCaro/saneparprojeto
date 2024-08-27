package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity"; // Tag para logs
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonfatura;
    private Button buttonhidrom;
    private Button Register;
    private ProgressBar progresso;
    private CheckBox checkbox;
    private AcessoBD acessoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialização dos componentes da interface
        acessoBD = new AcessoBD(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        Register = findViewById(R.id.Register);
        buttonfatura = findViewById(R.id.fatura);
        buttonhidrom = findViewById(R.id.Hidro);  // Corrigido: adicionado ponto e vírgula
        progresso = findViewById(R.id.progressobar); // Verifique se o ID está correto no layout
        checkbox = findViewById(R.id.checkbox);

        // Configuração do botão de registro
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this, Registro.class);
                startActivity(in);
            }
        });

        // Configuração do botão para abrir a fatura
        buttonfatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this, Fatura.class);
                startActivity(in);
            }
        });

        // Configuração do botão para abrir o hidrômetro
        buttonhidrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this, Hidro.class); // Verifique se essa é a classe correta
                startActivity(in);
            }
        });

        // Configuração do botão de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String senha = editTextPassword.getText().toString().trim();

                Log.d(TAG, "Botão de login clicado.");
                Log.d(TAG, "Username: " + username);
                Log.d(TAG, "Senha: " + senha);

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(senha)) {
                    progresso.setVisibility(View.VISIBLE);
                    boolean usuarioValido = acessoBD.verificarUsuario(username, senha);
                    progresso.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Usuário válido: " + usuarioValido);

                    if (usuarioValido) {
                        mostrarDicasSustentabilidade();
                    } else {
                        Toast.makeText(Login.this, "Conta não encontrada, por favor registre-se", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Conta não encontrada.");
                    }
                } else {
                    Toast.makeText(Login.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Campos vazios.");
                }
            }
        });

        // Configuração do checkbox para mostrar/esconder senha
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /**
     * Método para exibir as dicas de sustentabilidade e economia de água.
     */
    private void mostrarDicasSustentabilidade() {
        Log.d(TAG, "Exibindo dicas de sustentabilidade.");

        // Criação do AlertDialog com as dicas
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Dicas de Sustentabilidade e Economia de Água")
                .setMessage(
                        "Aqui estão algumas dicas para economizar água e ajudar o meio ambiente:\n\n" +
                                "1. **Feche o registro** enquanto ensaboa-se durante o banho.\n\n" +
                                "2. **Tome banhos mais curtos** para reduzir o consumo de água.\n\n" +
                                "3. **Recolha água da chuva** para regar plantas.\n\n" +
                                "4. **Utilize vassouras** em vez de mangueira para limpar áreas externas.\n\n" +
                                "5. **Verifique e repare vazamentos** em torneiras e canos.\n\n" +
                                "6. **Use eletrodomésticos com eficiência energética**.\n\n" +
                                "7. **Reutilize água** sempre que possível.\n\n" +
                                "8. **Reduza o uso de produtos descartáveis** para diminuir o lixo."
                )
                .setCancelable(false) // Impede que o diálogo seja cancelado sem interação
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Usuário aceitou as dicas. Abrindo tela principal.");
                        abrirTelaPrincipal();
                    }
                })
                .show();
    }

    /**
     * Método para abrir a tela principal (Menu).
     */
    private void abrirTelaPrincipal() {
        Log.d(TAG, "Abrindo tela principal.");
        Intent intent = new Intent(Login.this, Menu.class);
        startActivity(intent);
        finish(); // Finaliza a atividade atual para que o usuário não volte para ela ao pressionar "Voltar"
    }

    /**
     * Método opcional para navegação para a tela de registro, caso seja chamado de outra parte do código.
     */
    public void goToRegister(View view) {
        Log.d(TAG, "Indo para a tela de registro.");
        Intent intent = new Intent(this, Calculoequipamentos.class);
        startActivity(intent);
    }
}
