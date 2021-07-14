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
import com.example.tfgprueba2.databinding.ItemMyactivityBinding
import java.time.Duration

class MiActividadAdaptador(val misactividades: MutableList<Actividad>, val idUsuario:Int, private var listener: MiActividadAdaptador.OnClickListener, val tiposActividad:List<GruposParametros>, val creadores:MutableList<String>,val nombreactividad:MutableList<String>,val competencia:List<GruposParametros>,val tamanio:MutableList<GruposParametros>,val duration:MutableList<GruposParametros>,val rela:MutableList<GruposParametros>,val localizacion:MutableList<String>): RecyclerView.Adapter<MiActividadAdaptador.ViewHolder>() {


    private lateinit var mContext: Context



    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        var binding= ItemMyactivityBinding.bind(view)

        fun setListener(actividad: Actividad,idUsuario: Int){

            binding.root.setOnClickListener {

                //listener.onClick(grupo)

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiActividadAdaptador.ViewHolder {
        mContext=parent.context

        var view= LayoutInflater.from(mContext).inflate(R.layout.item_myactivity,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MiActividadAdaptador.ViewHolder, position: Int) {

        var actividad=misactividades.get(position)
        var tipos=tiposActividad.get(position)
        var creador=creadores.get(position)
        var nombreactivi=nombreactividad.get(position)
        var competencia=competencia.get(position)
        var tam=tamanio.get(position)
        var dura=duration.get(position)
        var loca=localizacion.get(position)

        var re=rela.get(position)
        with(holder){

            setListener(actividad,idUsuario)
            binding.TitulomiActividadtarjeta.text=actividad.nombre
            binding.fechamiActividadTarjeta.text=actividad.Fecha
            binding.DescripcionmiActividadTarjeta.text=actividad.descripcion
            binding.PropietariomiActividadTarjeta.text=creador
            binding.tipomiActividadTarjeta.text=tipos.valor
            binding.grupopertenecienteactividad.text=nombreactivi
            binding.competencias.text=competencia.valor
            binding.tamaniomiactiviad.text=tam.idParametrovalor.toString()
            binding.duracionnmiactiviad.text=dura.valor
            binding.ralacionnmiactiviad.text=re.valor
            binding.localizacionmiactiviad.text=loca
        }

    }

    override fun getItemCount(): Int =misactividades.size

    interface OnClickListener {
        fun onClick(miactividad: Actividad)
        //  fun unirsegrupo(grupo: Grupos)
        //  fun onDeletegrupo(grupo: Grupos)
    }


}