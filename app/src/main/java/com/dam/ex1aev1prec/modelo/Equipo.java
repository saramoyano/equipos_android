package com.dam.ex1aev1prec.modelo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Equipo implements Parcelable {

    /* Atributos **********************************************************************************/

    private String id;          // PK not null
    private String fecha;       // not null
    private int loc;            // not null         1:TALLER1   2:TALLER2   3:DPTO
    private String modelo;      // null
    private boolean enUso;      // not null
    private Bitmap foto;


    /* Constructores ******************************************************************************/

    public Equipo() {
        id = "";
        fecha = "";
        loc = 3;
        modelo = "";
        enUso = true;
        foto = null;
    }

    /* Getters & Setters **************************************************************************/

    protected Equipo(Parcel in) {
        id = in.readString();
        fecha = in.readString();
        loc = in.readInt();
        modelo = in.readString();
        enUso = in.readByte() != 0;
        foto = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Equipo> CREATOR = new Creator<Equipo>() {
        @Override
        public Equipo createFromParcel(Parcel in) {
            return new Equipo(in);
        }

        @Override
        public Equipo[] newArray(int size) {
            return new Equipo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isEnUso() {
        return enUso;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fecha);
        dest.writeInt(loc);
        dest.writeString(modelo);
        dest.writeByte((byte) (enUso ? 1 : 0));
        dest.writeParcelable(foto, flags);
    }

    /* Parcelable *********************************************************************************/

}
