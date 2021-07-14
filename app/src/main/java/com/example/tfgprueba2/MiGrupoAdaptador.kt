package com.example.tfgprueba2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.Dataclass.GruposParametros
import com.example.tfgprueba2.databinding.ItemMyGroupBinding
import com.squareup.picasso.Picasso

class MiGrupoAdaptador(val grupos: MutableList<Grupos>, private var listener: MiGrupoAdaptador.OnClickListener, val idUsuario:Int,val miidiomas:MutableList<GruposParametros>,val mitamanio:MutableList<GruposParametros>,val prop:MutableList<GruposParametros>):
    RecyclerView.Adapter<MiGrupoAdaptador.ViewHolder>() {

    private lateinit var mContext: Context

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        var binding=ItemMyGroupBinding.bind(view)

        fun setListener(grupo: Grupos,idUsuario: Int){

            binding.root.setOnClickListener {

                //listener.onClick(grupo)



            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiGrupoAdaptador.ViewHolder {///5 que hacemos
        mContext=parent.context

        var view= LayoutInflater.from(mContext).inflate(R.layout.item_my_group,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val grupo=grupos.get(position)
        val idioma=miidiomas.get(position)
        val tamanio=mitamanio.get(position)
        val prop=prop.get(position)
        with(holder){

            setListener(grupo,idUsuario)
            binding.titulomigrupo.text=grupo.nombre
            binding.PropositoGrupo.text=grupo.proposito
            binding.tituloidioma.text=idioma.valor
            binding.TamaONumero.text=tamanio.idParametrovalor.toString()
            binding.FechaGrupo.text=grupo.fecha
            binding.PropietarioGrupoNombre.text=prop.valor
            Picasso.get().load(grupo.urlgrupo).into(binding.fotomygroup)

        }

    }

    fun add(grupo: Grupos) {
        grupos.add(grupo)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =grupos.size

    interface OnClickListener {
        fun onClick(grupo: Grupos)
        //  fun unirsegrupo(grupo: Grupos)
        //  fun onDeletegrupo(grupo: Grupos)
    }

}