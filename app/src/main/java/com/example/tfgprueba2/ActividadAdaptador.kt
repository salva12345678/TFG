package com.example.tfgprueba2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgprueba2.Dataclass.Actividad
import com.example.tfgprueba2.Dataclass.GruposParametros
import com.example.tfgprueba2.databinding.ItemActivityBinding


class ActividadAdaptador (val actividades: MutableList<Actividad>,val idUsuario:Int,private var listener: ActividadAdaptador.OnClickListener,val tiposActividad:List<GruposParametros>,val creadores:MutableList<String>): RecyclerView.Adapter<ActividadAdaptador.ViewHolder>(){

    private lateinit var mContext: Context



    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        var binding=ItemActivityBinding.bind(view)

        fun setListener(actividad: Actividad,idUsuario: Int){

            binding.UnirseActividad.setOnClickListener {

                var db = conect()
                db?.Conect()

                if(actividad.idUsuario==idUsuario) {
                    Toast.makeText(mContext, R.string.noactividaduser, Toast.LENGTH_SHORT).show()
                }
                else{

                    var numeromax=db?.ObtenermiembrosactividadGruposmaxpermitido(actividad.idActividad)
                    var numero=db?.Obtenermiembrosdeunaactividad(actividad.idActividad)

                    if (numeromax==numero){
                        Toast.makeText(mContext, R.string.maxpersonactivity, Toast.LENGTH_SHORT).show()
                    }
                    else{

                        if(db?.eresmiembrosdeunactividad(actividad.idActividad,idUsuario)){

                            Toast.makeText(mContext, R.string.actividadmember, Toast.LENGTH_SHORT).show()

                        }
                        else {
                            db?.intouseractivity(idUsuario, actividad.idActividad)
                            Toast.makeText(mContext, R.string.unionactivity, Toast.LENGTH_SHORT).show()
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

    override fun onBindViewHolder(holder: ActividadAdaptador.ViewHolder, position: Int) {
        var actividad=actividades.get(position)
        var tipos=tiposActividad.get(position)
        var creador=creadores.get(position)
        with(holder){

            setListener(actividad,idUsuario)
            binding.TituloActividadtarjeta.text=actividad.nombre
            binding.fechaActividadTarjeta.text=actividad.Fecha
            binding.DescripcionActividadTarjeta.text=actividad.descripcion
            binding.tipoActividadTarjeta.text=tipos.valor
            binding.PropietarioActividadTarjeta.text=creador

        }

    }

    override fun getItemCount(): Int =actividades.size


    interface OnClickListener {
        fun onClick(actividad: Actividad)
        //  fun unirsegrupo(grupo: Grupos)
        //  fun onDeletegrupo(grupo: Grupos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadAdaptador.ViewHolder {
        mContext=parent.context

        var view= LayoutInflater.from(mContext).inflate(R.layout.item_activity,parent,false)

        return ViewHolder(view)

    }


}