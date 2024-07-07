package Simulador.saneparprojeto;

import androidx.appcompat.app.AppCompatActivity;

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
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity"; // Tag para logs
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button Register;
    private ProgressBar progresso;
    private CheckBox checkbox;
    private AcessoBD acessoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        acessoBD = new AcessoBD(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        Register = findViewById(R.id.Register);
        progresso = findViewById(R.id.progressobar); // Certifique-se de que o ID está correto
        checkbox = findViewById(R.id.checkbox);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this, Registro.class);
                startActivity(in);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String senha = editTextPassword.getText().toString();

                Log.d(TAG, "Botão de login clicado."); // Adiciona log
                Log.d(TAG, "Username: " + username); // Adiciona log
                Log.d(TAG, "Senha: " + senha); // Adiciona log

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(senha)) {
                    progresso.setVisibility(View.VISIBLE);
                    boolean usuarioValido = acessoBD.verificarUsuario(username, senha);
                    progresso.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Usuario válido: " + usuarioValido); // Adiciona log

                    if (usuarioValido) {
                        abrirTelaPrincipal();
                    } else {
                        Toast.makeText(Login.this, "Conta não encontrada, por favor registre-se", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Conta não encontrada."); // Adiciona log
                    }
                } else {
                    Toast.makeText(Login.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Campos vazios."); // Adiciona log
                }
            }
        });

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

    private void abrirTelaPrincipal() {
        Log.d(TAG, "Abrindo tela principal."); // Adiciona log
        Intent intent = new Intent(Login.this, Menu.class);
        startActivity(intent);
        finish(); // Fecha a atividade atual
    }

    public void goToRegister(View view) {
        Log.d(TAG, "Indo para a tela de registro."); // Adiciona log
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
}
