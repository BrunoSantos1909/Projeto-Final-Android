package bruno.santos.projetofinal;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import bruno.santos.projetofinal.adapter.GuitarraAdapter;
import bruno.santos.projetofinal.model.Guitarra;

public class Cadastrar extends AppCompatActivity {

    private EditText etMarca;
    private EditText etModelo;
    private EditText etPreco;
    private EditText etAno;
    private Button btCadastrar;
    private RecyclerView rvGuitarras;

    private ArrayList<Guitarra> guitarras;
    private GuitarraAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        if(getIntent().getSerializableExtra("MA") != null) {

            MainActivity MA = (MainActivity) getIntent().getSerializableExtra("MA");
        }

        //refs
        etMarca =  findViewById(R.id.ca_et_marca);
        etModelo =  findViewById(R.id.ca_et_modelo);
        etPreco =  findViewById(R.id.ca_et_preco);
        etAno =  findViewById(R.id.ca_et_ano);
        rvGuitarras = findViewById(R.id.ca_rv_guitarras);
        btCadastrar =  findViewById(R.id.ca_bt_cadastrar);

        FirebaseApp.initializeApp(Cadastrar.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("guitarras");

        guitarras = new ArrayList<>();
        adapter = new GuitarraAdapter(Cadastrar.this, guitarras);

        rvGuitarras.setAdapter(adapter);

        rvGuitarras.setHasFixedSize(true);
        rvGuitarras.setLayoutManager(new LinearLayoutManager(this));

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Guitarra g = new Guitarra();

                g.setMarca(etMarca.getText().toString());
                g.setModelo(etModelo.getText().toString());
                g.setPreco(Double.parseDouble(etPreco.getText().toString()));
                g.setAno(Integer.parseInt(etAno.getText().toString()));
                banco.push().setValue(g); //Enviando para nuvem!

                Toast.makeText(
                        getBaseContext(),
                        "Guitarra cadastrada com sucesso!",
                        Toast.LENGTH_SHORT).show();

            }//onclick
        });//onclick do btOK

        adapter.setOnItemClickListener(new GuitarraAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(
                        getBaseContext(),
                        "Clicou na posiçao: "+position +
                                "\ncliente: "+guitarras.get(position),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(final int position, View view) {

                AlertDialog.Builder msg = new AlertDialog.Builder(Cadastrar.this);
                msg.setTitle("Confirmaçao");
                msg.setMessage("Voce tem certeza que deseja excluir esse cliente");
                msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Guitarra g = guitarras.get(position);

                        //Removendo através da chave(key) no firebase
                        banco.child(g.getKey()).removeValue();

                        guitarras.remove(position);
                        adapter.notifyDataSetChanged();

                        Toast.makeText(
                                getBaseContext(),
                                "Cliente removido com sucesso!",
                                Toast.LENGTH_LONG).show();

                    }
                });
                msg.setNegativeButton("Nao", null);
                msg.show();

                Toast.makeText(
                        getBaseContext(),
                        "Clicou e segurou na posiçao: "+position +
                                "\ncliente: "+guitarras.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });


        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                guitarras.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Guitarra g = data.getValue(Guitarra.class);
                    g.setKey(data.getKey());
                    guitarras.add(g);
                }//fecha for
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
