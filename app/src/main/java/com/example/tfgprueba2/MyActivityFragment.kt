package com.example.tfgprueba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tfgprueba2.Dataclass.Actividad
import com.example.tfgprueba2.databinding.FragmentMyActivityBinding

class MyActivityFragment:Fragment(),MiActividadAdaptador.OnClickListener {

    lateinit var misactividades: MutableList<Actividad>

    private lateinit var binding:FragmentMyActivityBinding

    private lateinit var mLinearLayoutManager: LinearLayoutManager
    var db = conect()
    private   lateinit var mAdapter:MiActividadAdaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMyActivityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupmiActivity()

    }


    override fun onClick(miactividad: Actividad) {
        TODO("Not yet implemented")
    }


   fun  setupmiActivity(){

       var user=(activity as MainActivity?)?.who()

       db?.Conect()
       misactividades=db?.selectmyActivity(user!!.idusuario) //de aqui sacamos los datos de la base de datos

       var tipodeactividades=db?.buscarDatosmisactividades(user!!.idusuario)

       var ordenado=tipodeactividades.sortedBy { it.idParametro }

       var creadores=db?.buscardemisactividadesdueños(user!!.idusuario)

       var nombreGrupo=db?.nombredemisactividades(user!!.idusuario)

       var competencias=db?.buscarcompetenciasmisactividades(user!!.idusuario)

       var ordenadocompe=competencias.sortedBy { it.idParametro }


       var tamanio=db?.selecmyactividadidtam(user!!.idusuario)

       var duracion=db?.myactividaduracion(user!!.idusuario)

       var rela=db?.myactividarelac(user!!.idusuario)

       var loca=db?.buscaractividadlugar(user!!.idusuario)

       mLinearLayoutManager= LinearLayoutManager(context )

       val adapter1=MiActividadAdaptador(misactividades,user!!.idusuario,this,ordenado,creadores,nombreGrupo,ordenadocompe,tamanio,duracion,rela,loca)


       binding.recycleviewmyactividad.apply {
           setHasFixedSize(true)
           layoutManager=mLinearLayoutManager
           adapter=adapter1

       }


    }
}