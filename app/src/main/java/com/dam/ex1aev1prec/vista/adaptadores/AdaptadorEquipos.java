package com.dam.ex1aev1prec.vista.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.ex1aev1prec.R;
import com.dam.ex1aev1prec.modelo.Equipo;

import java.util.List;

public class AdaptadorEquipos extends RecyclerView.Adapter<AdaptadorEquipos.EquipoVH> {

    private List<Equipo> mDatos;
    private int mItemPos;
    private Context mContext;


    public AdaptadorEquipos(List<Equipo> datos, Context context) {
        mDatos = datos;
        mContext = context;

    }

    public int getmItemPos() {
        return mItemPos;
    }

    public void setmItemPos(int mItemPos) {
        this.mItemPos = mItemPos;
    }

    @NonNull
    @Override
    public EquipoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_equipos, parent, false);
        return new EquipoVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoVH holder, int position) {
        holder.setItem(mDatos.get(position));
        holder.itemView.setBackgroundColor(getmItemPos()==position ? Color.YELLOW : Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    public class EquipoVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        private ImageView ivFoto;
        private TextView tvId, tvFecha, tvLoc, tvModelo, tvEnUso;

        public EquipoVH(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvId = itemView.findViewById(R.id.tvId);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvLoc = itemView.findViewById(R.id.tvLoc);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvEnUso = itemView.findViewById(R.id.tvEnUso);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        private void setItem(Equipo e) {
            tvId.setText(mContext.getString(R.string.tv_Id) + " " + e.getId());
            tvModelo.setText(mContext.getString(R.string.tv_Modelo) + " " + e.getModelo());
            tvLoc.setText(mContext.getString(R.string.tv_Loc) + " " + e.getLoc());
            tvFecha.setText(mContext.getString(R.string.tv_Fecha) + " " + e.getFecha());
            tvEnUso.setText(mContext.getString(R.string.tv_EnUso) + " " + (e.isEnUso()? mContext.getString(R.string.bt_SI) : mContext.getString(R.string.bt_NO) ));
            switch(e.getLoc()){
                case 1:
                    tvLoc.setText(mContext.getString(R.string.tv_Loc) + "" + mContext.getString(R.string.rb_Taller1));
                    break;
                case 2:
                    tvLoc.setText(mContext.getString(R.string.tv_Loc) + "" + mContext.getString(R.string.rb_Taller2));
                    break;
                case 3:
                    tvLoc.setText(mContext.getString(R.string.tv_Loc) + "" + mContext.getString(R.string.rb_Dpto));
                    break;
            }
            Bitmap bm =null;
            bm = e.getFoto();
            if(bm == null) {
                ivFoto.setImageResource(R.mipmap.ic_launcher);
            }else{
                ivFoto.setImageBitmap(bm);
            }

        }



        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            notifyItemChanged(mItemPos);
            if (mItemPos == -1 || mItemPos != pos) {
                mItemPos = pos;

            } else {
                mItemPos = -1;
            }
            notifyItemChanged(mItemPos);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            ((Activity)mContext).getMenuInflater().inflate(R.menu.menu_contextual, menu);
        }

        @Override
        public boolean onLongClick(View v) {
            int pos = getLayoutPosition();
            notifyItemChanged(mItemPos);
            mItemPos = pos;
            return false;
        }
    }

}
