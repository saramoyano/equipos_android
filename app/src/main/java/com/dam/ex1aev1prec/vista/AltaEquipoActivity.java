package com.dam.ex1aev1prec.vista;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.dam.ex1aev1prec.R;
import com.dam.ex1aev1prec.modelo.Equipo;
import com.dam.ex1aev1prec.vista.dialogos.DlgSeleccionFecha;
import com.dam.ex1aev1prec.vistamodelo.Datos;
import com.google.android.material.snackbar.Snackbar;

public class AltaEquipoActivity extends AppCompatActivity implements DlgSeleccionFecha.DlgSeleccionFechaListener {

    private EditText etId, etFecha;
    private RadioGroup rgLoc;
    private RadioButton rbTaller1, rbTaller2, rbDpto;
    private Spinner spModelo;
    private Button btFecha, btCancelar, btAceptar;
    private CheckBox cbEnUso;
    private ImageView ivFoto;

    private static final int INTENT_IMPLICIT_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_equipo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FindViewByIds
        etId = findViewById(R.id.etId);
        etFecha = findViewById(R.id.etFecha);
        rgLoc = findViewById(R.id.rgLoc);
        rbTaller1 = findViewById(R.id.rbTaller1);
        rbTaller2 = findViewById(R.id.rbTaller2);
        rbDpto = findViewById(R.id.rbDpto);
        spModelo = findViewById(R.id.spModelo);
        btFecha = findViewById(R.id.btFecha);
        btCancelar = findViewById(R.id.btCancelar);
        btAceptar = findViewById(R.id.btAceptar);
        cbEnUso = findViewById(R.id.cbEnUso);
        ivFoto = findViewById(R.id.ivFoto);
        // Inits

        ArrayAdapter<CharSequence> aAdapter =  ArrayAdapter.createFromResource(this,R.array.modelos_equipos_array,  android.R.layout.simple_spinner_item);
        aAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModelo.setAdapter(aAdapter);
        // Listeners
        btCancelar.setOnClickListener(btCancelar_OnClickListener);
        btAceptar.setOnClickListener(btAceptar_OnClickListener);
        btFecha.setOnClickListener(btFecha_OnClick);
        ivFoto.setOnClickListener(ivFoto_onClick);



        Intent iAltaConId = getIntent();
        if(iAltaConId != null){
            if(iAltaConId.getStringExtra("id")!= null){
                String id = iAltaConId.getStringExtra("id");
                etId.setText(id);
                etId.setEnabled(false);
            }
        }
    }

    private View.OnClickListener btCancelar_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Oculta el teclado
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            setResult(RESULT_CANCELED);
           finish();
        }
    };

    private View.OnClickListener btAceptar_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Oculta el teclado
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            if(etId.getText().length() == 0 || etFecha.getText().length()==0){
                Snackbar.make(v, R.string.msg_FaltanDatosObligatorios, Snackbar.LENGTH_SHORT).show();
            }else {
                Equipo e = new Equipo();
                e.setId(etId.getText().toString());
                e.setFecha(etFecha.getText().toString());
                String modelo = getResources().getStringArray(R.array.modelos_equipos_array)[spModelo.getSelectedItemPosition()];
                e.setModelo(modelo);
                if(rbDpto.isChecked()){
                e.setLoc(3);
                }else if(rbTaller2.isChecked()){
                    e.setLoc(2);
                }else{
                    e.setLoc(1);
                }
                e.setEnUso(cbEnUso.isChecked());

                Intent iAlta = new Intent();
                iAlta.putExtra("equipo", e);
                setResult(RESULT_OK, iAlta);
                finish();
            }
        }
    };

    private View.OnClickListener ivFoto_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent  intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, INTENT_IMPLICIT_CAMERA);
            }
        }
    };

    private View.OnClickListener  btFecha_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DlgSeleccionFecha dlgSeleccionFecha = new DlgSeleccionFecha();
            dlgSeleccionFecha.show(getSupportFragmentManager(), "tagFecha");
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap foto = null;
        switch (requestCode) {
            case INTENT_IMPLICIT_CAMERA:
                if (data != null) {
                    foto = data.getParcelableExtra("data");
                }else {
                    Snackbar.make(findViewById(android.R.id.content), R.string.msg_Error2, Snackbar.LENGTH_SHORT).show();
                }
                if (foto != null) {
                   ivFoto.setImageBitmap(foto);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) {
        etFecha.setText(fecha);
    }

    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        etFecha.setText("");
    }
}
