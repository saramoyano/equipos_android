package com.dam.ex1aev1prec.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.dam.ex1aev1prec.R;
import com.dam.ex1aev1prec.modelo.Equipo;
import com.dam.ex1aev1prec.vista.dialogos.DialogoConfirmación;
import com.dam.ex1aev1prec.vistamodelo.Datos;
import com.dam.ex1aev1prec.vistamodelo.LogicaEquipo;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DialogoConfirmación.DialogoConfCallback {

    private static final int NUEVO_EQUIPO = 1;
    private Intent intent;

    private static final String tagConfirmacion_Salir = "tagConfirmacion_Salir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FindViewByIds

        // Inits

        // Listeners

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menuListadoEquipos: {
                intent = new Intent(MainActivity.this, ListadoEquiposActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menuNuevoEquipoConID: {
                intent = new Intent(MainActivity.this, AltaEquipoActivity.class);
                Date fecha = new Date();
                String strFecha = "yyyyMMddHHmmss ";
                SimpleDateFormat objSDF = new SimpleDateFormat(strFecha);
                String idfecha = objSDF.format(fecha);
                intent.putExtra("id", idfecha);
                startActivityForResult(intent, NUEVO_EQUIPO);
                return true;

            }
            case R.id.menuNuevoEquipoSinID: {
                intent = new Intent(MainActivity.this, AltaEquipoActivity.class);
                startActivityForResult(intent, NUEVO_EQUIPO);
                return true;

            }
            case R.id.menuSalir:
                DialogoConfirmación dlg = new DialogoConfirmación();
                dlg.setTitulo(R.string.app_name);
                dlg.setMensaje(R.string.msg_Salir);
                dlg.show(getSupportFragmentManager(), tagConfirmacion_Salir);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVO_EQUIPO && resultCode == RESULT_OK) {
            if (data != null) {
                Equipo e = null;
                e = data.getParcelableExtra("equipo");
                if (e != null) {
                    boolean ok = Datos.getInstance().getmListaEquipos().add(e);
                    if (ok) {
                        Snackbar.make(findViewById(android.R.id.content), R.string.msg_AltaEquipoOK, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), R.string.msg_AltaEquipoKO, Snackbar.LENGTH_SHORT).show();
                    }
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), R.string.msg_Error2, Snackbar.LENGTH_SHORT).show();
            }
        }else{
            Snackbar.make(findViewById(android.R.id.content), "Accion cancelada", Snackbar.LENGTH_SHORT).show();
        }

    }
}
