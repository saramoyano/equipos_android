package com.dam.ex1aev1prec.vista.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dam.ex1aev1prec.R;

public class DialogoConfirmación extends DialogFragment {

    private int titulo, mensaje;

    public DialogoConfirmación() {
    }

    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public int getMensaje() {
        return mensaje;
    }

    public void setMensaje(int mensaje) {
        this.mensaje = mensaje;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton(R.string.bt_Aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogPositiveClick(DialogoConfirmación.this);
            }
        });
        builder.setNegativeButton(R.string.bt_Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(DialogoConfirmación.this);
            }
        });


        return builder.create();
    }

    public interface DialogoConfCallback {
        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);
    }


    DialogoConfCallback mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            mListener = (DialogoConfCallback) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(getActivity().toString()
                    + " must implement DialogoConfCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener =null;
    }
}
