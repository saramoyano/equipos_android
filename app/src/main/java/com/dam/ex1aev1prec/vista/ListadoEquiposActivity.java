package com.dam.ex1aev1prec.vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.ex1aev1prec.R;
import com.dam.ex1aev1prec.modelo.Equipo;
import com.dam.ex1aev1prec.vista.adaptadores.AdaptadorEquipos;
import com.dam.ex1aev1prec.vista.dialogos.DialogoConfirmación;
import com.dam.ex1aev1prec.vistamodelo.Datos;
import com.dam.ex1aev1prec.vistamodelo.LogicaEquipo;

public class ListadoEquiposActivity extends AppCompatActivity implements DialogoConfirmación.DialogoConfCallback {


    private RecyclerView rvEquipos;
    private AdaptadorEquipos mAdaptadorEquipos;

    private static final int ELIMINAR_EQUIPO = 2;
    private static final int ELIMINAR_TODOS = 3;

    private static final int ACTUALIZAR = 5;
    private static final String tagConfirmacion_EliminarEquipo = "tagConfirmacion_EliminarEquipo";
    private static final String tagConfirmacion_EliminarTodos = "tagConfirmacion_EliminarTodos";

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_equipos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FindViewByIds
        rvEquipos = findViewById(R.id.rvEquipos);

        // Inits
        mAdaptadorEquipos = new AdaptadorEquipos(Datos.getInstance().getmListaEquipos(), this);
        rvEquipos.setHasFixedSize(true);
        rvEquipos.setLayoutManager(new LinearLayoutManager(this));
        rvEquipos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvEquipos.setAdapter(mAdaptadorEquipos);


        for (int i = 0; i < 3; i++) {
            Equipo e = new Equipo();
            e.setId("20191219121212" + String.valueOf(i));
            e.setFecha("12/12/2012");
            e.setModelo("HP");
            e.setFoto(null);
            e.setEnUso(true);
            e.setLoc(1);
            Datos.getInstance().getmListaEquipos().add(e);
        }

        // Listeners

    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuEliminarEquipo:
                DialogoConfirmación dlg = new DialogoConfirmación();
                dlg.setTitulo(R.string.app_name);
                dlg.setMensaje(R.string.msg_Confirmacion);
                dlg.show(getSupportFragmentManager(), tagConfirmacion_EliminarEquipo);

                break;
            case R.id.menuEliminarTodos:
                DialogoConfirmación dialogo = new DialogoConfirmación();
                dialogo.setTitulo(R.string.app_name);
                dialogo.setMensaje(R.string.msg_Confirmacion);
                dialogo.show(getSupportFragmentManager(), tagConfirmacion_EliminarTodos);

                break;
        }

        return super.onContextItemSelected(item);
    }

    private void actualizarRV(int tipoOp) {
        switch (tipoOp) {
            case ELIMINAR_EQUIPO:
                mAdaptadorEquipos.notifyItemRemoved(mAdaptadorEquipos.getmItemPos());
                break;
            case ELIMINAR_TODOS:
                mAdaptadorEquipos.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog.getTag() == tagConfirmacion_EliminarEquipo) {
            Equipo e = null;
            if (mAdaptadorEquipos.getmItemPos() != -1) {
                e= Datos.getInstance().getmListaEquipos().get(mAdaptadorEquipos.getmItemPos());
                LogicaEquipo.bajaEquipo(e);
                actualizarRV(ELIMINAR_EQUIPO);
            }
        } else {
            Datos.getInstance().getmListaEquipos().clear();
            actualizarRV(ELIMINAR_TODOS);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        ;
    }
}
