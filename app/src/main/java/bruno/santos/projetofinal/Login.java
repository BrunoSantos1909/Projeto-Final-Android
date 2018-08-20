package bruno.santos.projetofinal;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bruno.santos.projetofinal.model.Usuario;

public class Login extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;
    private Button btEntrar;
    private ProgressBar progress;

    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Refs
        etLogin = findViewById(R.id.lo_et_login);
        etSenha = findViewById(R.id.lo_et_senha);
        btEntrar = findViewById(R.id.lo_bt_entrar);
        progress = findViewById(R.id.lo_progress);

        progress.setVisibility(View.INVISIBLE);

        //Buscando ref. firebase
        mAuth = FirebaseAuth.getInstance();

        //Verificando se o usuário já logou anteriormente
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //redirecionar para tela de abertura
                    Intent it = new Intent(Login.this,MainActivity.class);
                    startActivity(it);
                    finish();

                }else{

                    //Usuário NÃO está logado
                    Toast.makeText(
                            getBaseContext(),
                            "Você não está logado ainda!",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etLogin.getText().toString().isEmpty() &&
                        !etSenha.getText().toString().isEmpty()){

                    //Iniciando progress
                    progress.setVisibility(View.VISIBLE);

                    Usuario u = new Usuario();
                    u.setLogin(etLogin.getText().toString());
                    u.setSenha(etSenha.getText().toString());

                    //Verificar e autenticar usuário no Firebase
                    mAuth.signInWithEmailAndPassword(u.getLogin(), u.getSenha())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(!task.isSuccessful()){
                                        Toast.makeText(
                                                getBaseContext(),
                                                "Usuário NÃO autenticado!",
                                                Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(
                                                getBaseContext(),
                                                "Usuário autenticado com sucesso!",
                                                Toast.LENGTH_LONG).show();

                                        //redirecionar para tela de abertura
                                        Intent it = new Intent(Login.this,MainActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                    //Removendo progress
                                    progress.setVisibility(View.INVISIBLE);
                                }
                            });
                }else{
                    Toast.makeText(
                            getBaseContext(),
                            "Digite os dados para entrar no App",
                            Toast.LENGTH_LONG).show();
                }
            }//fecha onclick
        });
    }//fecha oncreate

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}