package com.example.tfgprueba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tfgprueba2.Dataclass.Actividad
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.Dataclass.GruposParametros
import com.example.tfgprueba2.databinding.FragmentActivityBinding
import java.util.Collections.sort

class ActivityFragment: Fragment(),ActividadAdaptador.OnClickListener {
    lateinit var actividades: MutableList<Actividad>

    private lateinit var binding:FragmentActivityBinding
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    var db = conect()
    private   lateinit var mAdapter:GrupoAdaptador


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentActivityBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActivity()

    }

    fun setupActivity(){

        var user=(activity as MainActivity?)?.who()

        db?.Conect()

        actividades=db?.selectActivity() //de aqui sacamos los datos de la base de datos

        var tipodeactividades=db?.buscarDatosactividades()

        var ordenado=tipodeactividades.sortedBy { it.idParametro }



        var creadores=db?.buscardueños()


        mLinearLayoutManager= LinearLayoutManager(context )

        val adapter1=ActividadAdaptador(actividades,user!!.idusuario,this,ordenado,creadores)


        binding.recycleviewactividad.apply {
            setHasFixedSize(true)
            layoutManager=mLinearLayoutManager
            adapter=adapter1

        }


    }



    override fun onClick(actividad: Actividad) {
        TODO("Not yet implemented")
    }


}