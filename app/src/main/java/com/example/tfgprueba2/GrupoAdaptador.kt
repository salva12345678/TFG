package com.example.tfgprueba2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.databinding.ItemGroupBinding
import com.squareup.picasso.Picasso

class GrupoAdaptador(val grupos: MutableList<Grupos>, private var listener:OnClickListener,val idUsuario:Int):RecyclerView.Adapter<GrupoAdaptador.ViewHolder>() {


    private lateinit var mContext: Context

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){ //1 que hacemos

        var binding=ItemGroupBinding.bind(view)  //2

        fun setListener(grupo: Grupos,idUsuario: Int){


            binding.Unirsegrupo.setOnClickListener {

                var db = conect()
                db?.Conect()


                if(grupo.idUsuario==idUsuario) {
                    Toast.makeText(mContext, R.string.nounion, Toast.LENGTH_SHORT).show()
                }
                else{

                    var numeromax=db?.ObtenermiembrosgruposGruposmaxpermitido(grupo.idGrupo)
                    var numero=db?.Obtenermiembrosdeungrupo(grupo.idGrupo)

                    if (numeromax==numero){
                        Toast.makeText(mContext, R.string.maxperson, Toast.LENGTH_SHORT).show()
                    }
                    else{

                        if(db?.eresmiembrosdeungrupo(grupo.idGrupo,idUsuario)){

                            Toast.makeText(mContext, R.string.groupmember, Toast.LENGTH_SHORT).show()

                        }
                            else {
                            db?.intousergroup(idUsuario, grupo.idGrupo)
                            Toast.makeText(mContext, R.string.uniongroup, Toast.LENGTH_SHORT).show()
                            notifyDataSetChanged() ////si peta, quitar
                        }
                    }

                }

            }


            binding.root.setOnClickListener {

                //listener.onClick(grupo)



            }

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

            setListener(grupo,idUsuario)
            binding.titulodelGrupo.text=grupo.nombre
            binding.tituloidioma.text=grupo.fecha
            Picasso.get().load(grupo.urlgrupo).into(binding.fotogrupo)

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

