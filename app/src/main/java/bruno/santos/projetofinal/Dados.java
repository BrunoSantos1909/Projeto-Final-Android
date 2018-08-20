package bruno.santos.projetofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bruno.santos.projetofinal.model.Guitarra;


public class Dados extends AppCompatActivity {

    //Widgets
    private TextView tvMarca;
    private TextView tvModelo;
    private TextView tvPreco;
    private TextView tvAno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

        //refs
        tvMarca = findViewById(R.id.da_tv_marca);
        tvModelo = findViewById(R.id.da_tv_modelo);
        tvPreco = findViewById(R.id.da_tv_preco);
        tvAno = findViewById(R.id.da_tv_ano);

        if(getIntent().getSerializableExtra("g") != null) {

            Guitarra g = (Guitarra) getIntent().getSerializableExtra("g");

            tvMarca.setText(g.getMarca());
            tvModelo.setText(g.getModelo());
            tvPreco.setText(String.valueOf(g.getPreco()));
            tvAno.setText(String.valueOf(g.getAno()));


        }//fecha if

    }
}//fecha classe
