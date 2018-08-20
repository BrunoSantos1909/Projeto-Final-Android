package bruno.santos.projetofinal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Contato extends AppCompatActivity {

    //Widgets
    private EditText etEmail;
    private EditText etTelefone;
    private EditText etAssunto;
    private EditText etMensagem;
    private Button btEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        //refs
        etEmail = findViewById(R.id.co_et_email);
        etTelefone = findViewById(R.id.co_et_telefone);
        etAssunto = findViewById(R.id.co_et_assunto);
        etMensagem = findViewById(R.id.co_et_mensagem);
        btEnviar = findViewById(R.id.co_bt_enviar);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String msg = null;

                    msg = "E-mail: " + etEmail.getText().toString() +
                            "\nTelefone: " + etTelefone.getText().toString() +
                            "\nAssunto: " + etAssunto.getText().toString() +
                            "\nMensagem: " + etMensagem.getText().toString();

                Intent gmail = new Intent(Intent.ACTION_SEND);
                gmail.setType("text/html");
                gmail.putExtra(Intent.EXTRA_EMAIL, etEmail.getText().toString());
                gmail.putExtra(Intent.EXTRA_SUBJECT, etAssunto.getText().toString());
                gmail.putExtra(Intent.EXTRA_TEXT, etMensagem.getText().toString());

                startActivity(Intent.createChooser(gmail, "Send Email"));
            }
        });
    }
}



