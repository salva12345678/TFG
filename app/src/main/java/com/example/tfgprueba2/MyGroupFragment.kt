package com.example.tfgprueba2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.databinding.FragmentGroupBinding
import com.example.tfgprueba2.databinding.FragmentMyGroupBinding

class MyGroupFragment :Fragment(), MiGrupoAdaptador.OnClickListener {

    lateinit var grupos: MutableList<Grupos>
    private lateinit var gBinding: FragmentMyGroupBinding


    private lateinit var mLinearLayoutManager: LinearLayoutManager
    var db = conect()
    private   lateinit var mAdapter:MiGrupoAdaptador




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gBinding= FragmentMyGroupBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_my_group, container, false)
        return gBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //gBinding= FragmentGroupBinding.inflate(layoutInflater)
        setupRecyclemygrupo()

    }

    private fun setupRecyclemygrupo() {

        var user=(activity as MainActivity?)?.who()

        db?.Conect()

        grupos=db?.selecmygrupos(user!!.idusuario)


        var grupoidioma=db?.selecmygruposid(user!!.idusuario)

        var grupotamanio=db?.selecmygruposidtam(user!!.idusuario)

        var propi=db?.selecmypropietario(user!!.idusuario)



        mLinearLayoutManager= LinearLayoutManager(context)

        val adapter1=MiGrupoAdaptador(grupos,this,user!!.idusuario,grupoidioma,grupotamanio,propi)


        gBinding.recycleviewmygrupo.apply {
            setHasFixedSize(true)
            layoutManager=mLinearLayoutManager
            adapter=adapter1

        }
    }


    override fun onClick(grupo: Grupos) {
        TODO("Not yet implemented")
    }

}