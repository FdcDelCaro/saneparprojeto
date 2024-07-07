package Simulador.saneparprojeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    private EditText editTextNome, editTextNomeUser, editTextNewUsername, editTextNewPassword, confirmarsen;
    private CheckBox checkBox;
    private Button registrar;
    private ProgressBar barraprog;
    private AcessoBD acessoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regristro);

        acessoBD = new AcessoBD(this);
        editTextNome = findViewById(R.id.ednome);
        editTextNomeUser = findViewById(R.id.ednomeuser);
        editTextNewUsername = findViewById(R.id.edmail);
        editTextNewPassword = findViewById(R.id.edsenha);
        confirmarsen = findViewById(R.id.confirmarsenha);
        checkBox = findViewById(R.id.checkbox);
        registrar = findViewById(R.id.btregistro);
        barraprog = findViewById(R.id.progresso);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editTextNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmarsen.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editTextNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmarsen.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = editTextNome.getText().toString();
                String nomeUser = editTextNomeUser.getText().toString();
                String registroemail = editTextNewUsername.getText().toString();
                String senhacadastro = editTextNewPassword.getText().toString();
                String confsenha = confirmarsen.getText().toString();

                if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(nomeUser) && !TextUtils.isEmpty(registroemail) &&
                        !TextUtils.isEmpty(senhacadastro) && !TextUtils.isEmpty(confsenha)) {
                    if (senhacadastro.equals(confsenha)) {
                        barraprog.setVisibility(View.VISIBLE);
                        long result = acessoBD.addUsuario(nome, registroemail, nomeUser, senhacadastro);
                        barraprog.setVisibility(View.INVISIBLE);
                        if (result != -1) {
                            Toast.makeText(Registro.this, "Registro bem-sucedido!", Toast.LENGTH_LONG).show();
                            goToLogin();
                        } else {
                            Toast.makeText(Registro.this, "Erro ao registrar usuário. Tente novamente.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Registro.this, "As senhas devem ser iguais!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Registro.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish(); // Fecha a atividade atual
    }
}
