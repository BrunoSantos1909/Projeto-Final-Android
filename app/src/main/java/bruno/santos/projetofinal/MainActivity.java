package bruno.santos.projetofinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import bruno.santos.projetofinal.model.Guitarra;
import bruno.santos.projetofinal.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button btCadastrar;

    private static final int MENU_CADASTRAR = 300;

    private Toolbar toolbar;

    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastrar = findViewById(R.id.ma_bt_cadastrar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(getIntent().getSerializableExtra("u") != null) {

                    Usuario u = (Usuario) getIntent().getSerializableExtra("u");

                    Toast.makeText(
                            getBaseContext(),
                            "Olá " + u.getLogin() + ", seja bem vindo!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CadIt = new Intent(MainActivity.this, Cadastrar.class);
                startActivity(CadIt);
            }
        });

        //Inicio AccountHeader
        //####################### SÓ O CABEÇALHO #######################
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.fundo)
                .addProfiles(
                        new ProfileDrawerItem().withName("Bruno Santos").withEmail("brunosantos@gmail.com").withIcon(getResources().getDrawable(R.drawable.darthvadericon))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener(){
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //Inicio Menu
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home")
                                .withIdentifier(0)
                                .withIcon(GoogleMaterial.Icon.gmd_home),

                        new SecondaryDrawerItem()
                                .withName("Site")
                                .withIdentifier(1).withIcon(
                                R.drawable.baseline_language_24),

                        new DividerDrawerItem(),

                        new SecondaryDrawerItem()
                                .withName("Sair")
                                .withIdentifier(2)
                                .withIcon(R.drawable.signout)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch ((int)drawerItem.getIdentifier()){
                            case 0:
                                Intent itContato = new Intent(MainActivity.this, Contato.class);
                                startActivity(itContato);
                                break;

                            case 1:
                                Uri uri = Uri.parse("http://www.gibson.com");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                break;
                            case 2:
                                AuthUI.getInstance()
                                        .signOut(MainActivity.this)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // user is now signed out
                                                startActivity(new Intent(MainActivity.this, Login.class));
                                                finish();
                                            }
                                        });
                                Toast.makeText(getBaseContext(),"Volte logo",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                }).build();


    }//fecha oncreate




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(MENU_CADASTRAR,
                MENU_CADASTRAR,
                MENU_CADASTRAR,
                "Guitarra")
                .setIcon(R.mipmap.ic_launcher)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sobre:
                Intent itSobre =  new Intent(MainActivity.this, Sobre.class);
                startActivity(itSobre);
                break;
        }//fecha switch

        return super.onOptionsItemSelected(item);
    }
    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }//fecha toast

}

