package com.example.tfgprueba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.databinding.FragmentGroupBinding


class GroupFragment:Fragment(),GrupoAdaptador.OnClickListener{

    lateinit var grupos: MutableList<Grupos>
    private lateinit var gBinding: FragmentGroupBinding


    private lateinit var mLinearLayoutManager: LinearLayoutManager
    var db = conect()
    private   lateinit var mAdapter:MiGrupoAdaptador


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         gBinding =  FragmentGroupBinding.inflate(inflater, container, false)
         return gBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //gBinding= FragmentGroupBinding.inflate(layoutInflater)
        setupRecyclegrupo()

    }

    private fun setupRecyclegrupo(){

        var user=(activity as MainActivity?)?.who()

        db?.Conect()

        grupos=db?.selectgrupo() //de aqui sacamos los datos de la base de datos

        mLinearLayoutManager= LinearLayoutManager(context )

        val adapter1=GrupoAdaptador(grupos,this,user!!.idusuario)


        gBinding.recycleviewgrupo.apply {
            setHasFixedSize(true)
            layoutManager=mLinearLayoutManager
            adapter=adapter1

        }


    }

    override fun onClick(grupo: Grupos) {
        TODO("Not yet implemented")
    }

}