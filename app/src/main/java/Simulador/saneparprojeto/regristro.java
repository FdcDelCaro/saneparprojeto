package Simulador.saneparprojeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class regristro extends AppCompatActivity {

    private EditText editTextNewUsername, editTextNewPassword;
    private EditText confirmarsen;
    private CheckBox checkBox;
    private Button registrar;
    private ProgressBar barraprog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regristro);

        mAuth = FirebaseAuth.getInstance();
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
                String registroemail = editTextNewUsername.getText().toString();
                String senhacadastro = editTextNewPassword.getText().toString();
                String confsenha = confirmarsen.getText().toString();
                if (!TextUtils.isEmpty(registroemail) || !TextUtils.isEmpty(senhacadastro) || !TextUtils.isEmpty(confsenha)) {
                    if (senhacadastro.equals(confsenha)) {
                        barraprog.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(registroemail, senhacadastro).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    goToLogin();
                                } else {
                                    String error = task.getException().getMessage();

                                    Toast.makeText(regristro.this, "Não foi possivel criar sua conta, verique os dados preenchidos", Toast.LENGTH_LONG).show();
                                }
                                    barraprog.setVisibility(View.INVISIBLE);
                                }
                            });
                    }else {
                        Toast.makeText(regristro.this, "as senhas devem ser iguais !!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void register(View view) {
        String newUsername = editTextNewUsername.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        // Adicione sua lógica de registro aqui
    }

    public void goToLogin() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
