package com.example.tfgprueba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgprueba2.databinding.FragmentGroupBinding

class GroupFragment:Fragment(),GrupoAdaptador.OnClickListener{


    lateinit var grupos: MutableList<Grupos>
    private lateinit var gBinding: FragmentGroupBinding


    private lateinit var mLinearLayoutManager: LinearLayoutManager
    var db = conect()
    private   lateinit var mAdapter:GrupoAdaptador

    //private lateinit var recv:RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //R.layout.item_group  fragment_group

        gBinding= FragmentGroupBinding.inflate(inflater,container,false)

        var view =  inflater.inflate(R.layout.fragment_group, container, false)

         return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gBinding= FragmentGroupBinding.inflate(layoutInflater)
        setupRecyclegrupo()

    }

    private fun setupRecyclegrupo(){

        //base de datos

        db?.Conect()
        grupos=db?.selectgrupo() //de aqui sacamos los datos de la base de datos

        mLinearLayoutManager= LinearLayoutManager(context )

        val adapter1=GrupoAdaptador(grupos,this)

        val grupo=Grupos(1,"aleman","grupoaleman",20,"grupo de alemanes en españa","www.ggoo.es")
        //println(grupo.languaje)
        adapter1.add(grupo)


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