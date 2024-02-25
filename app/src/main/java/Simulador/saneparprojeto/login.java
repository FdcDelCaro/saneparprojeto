package Simulador.saneparprojeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;

    private Button buttonLogin;
    private Button Register;
    private FirebaseAuth mAuth;
    private ProgressBar progresso;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        Register = findViewById(R.id.Register);
        progresso = findViewById(R.id.progresso);
        checkbox = findViewById(R.id.checkbox);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextUsername.getText().toString();
                String senha = editTextPassword.getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(senha)){
                    progresso.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        abrirTelaPrincipal();
                                    }else{
                                        String error = task.getException().getMessage();
                                        Toast.makeText(login.this, "Conta nao encontrada, por favaor registre-se"+error,Toast.LENGTH_LONG).show();
                                        progresso.setVisibility(View.INVISIBLE);




                                    }
                                }
                            });
                }
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });




    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(login.this, Menu.class);
        startActivity(intent);
    }


    public void loginaut(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Adicione sua lógica de autenticação aqui
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, regristro.class);
        startActivity(intent);
    }
}
