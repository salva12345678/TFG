package com.example.tfgprueba2

import android.app.usage.UsageEvents
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tfgprueba2.databinding.ItemGroupBinding
import com.squareup.picasso.Picasso

class GrupoAdaptador( val grupos: MutableList<Grupos>,private var listener:OnClickListener ):RecyclerView.Adapter<GrupoAdaptador.ViewHolder>() {

    private lateinit var mContext: Context

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){ //1 que hacemos
        var binding=ItemGroupBinding.bind(view)  //2

        fun setListener(grupo:Grupos){

            binding.root.setOnClickListener { listener.onClick(grupo) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {///5 que hacemos
        mContext=parent.context

        var view=LayoutInflater.from(mContext).inflate(R.layout.item_group,parent,false)

        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grupo=grupos.get(position)
        with(holder){
            setListener(grupo)
            binding.titulodelGrupo.text=grupo.nombregrupo
            binding.tituloidioma.text=grupo.languaje
            Picasso.get().load(grupo.url).into(binding.fotogrupo)

        }

    }

    override fun getItemCount(): Int =grupos.size

    fun add(grupo: Grupos) {
        grupos.add(grupo)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(grupo: Grupos)
      //  fun unirsegrupo(grupo: Grupos)
      //  fun onDeletegrupo(grupo: Grupos)
    }

}

