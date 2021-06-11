package com.example.tfgprueba2

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfgprueba2.databinding.ActivityMainBinding
import com.example.tfgprueba2.databinding.FragmentGroupBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivityMainBinding
    //private lateinit var gBinding: FragmentGroupBinding


    private lateinit var mActiveFragment:Fragment
    private lateinit var mFragmentManager: FragmentManager

    private lateinit var mGridLayout: GridLayoutManager

    lateinit var grupos: MutableList<Grupos>

    //var c: Connection? = null

    var db = conect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        //gBinding= FragmentGroupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        ///las 4 vistas
        setupBottomNav()
        //setupRecyclegrupo()


    }





    ///fragmentos de los botones  de abajo
    private fun setupBottomNav(){
        mFragmentManager=supportFragmentManager

        val grupoFragment=GroupFragment()
        val migrupoFragment=MyGroupFragment()
        val actividadFragment=ActivityFragment()
        val miactividadFragment=MyActivityFragment()

        mActiveFragment=grupoFragment

        mFragmentManager.beginTransaction()
            .add(R.id.hostfragment,miactividadFragment,MyActivityFragment::class.java.name)
            .hide(miactividadFragment).commit()

        mFragmentManager.beginTransaction()
            .add(R.id.hostfragment,actividadFragment,ActivityFragment::class.java.name)
            .hide(actividadFragment).commit()

        mFragmentManager.beginTransaction()
            .add(R.id.hostfragment,migrupoFragment,MyGroupFragment::class.java.name)
            .hide(migrupoFragment).commit()

        mFragmentManager.beginTransaction()
            .add(R.id.hostfragment,grupoFragment,GroupFragment::class.java.name)
            .commit()

        mBinding.bottomNav.setOnNavigationItemSelectedListener {

            when(it.itemId){

                R.id.grupo->{

                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(grupoFragment).commit()
                    mActiveFragment=grupoFragment
                    true

                }
                R.id.migrupo->{

                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(migrupoFragment).commit()
                    mActiveFragment=migrupoFragment
                    true

                }
                R.id.Actividad->{

                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(actividadFragment).commit()
                    mActiveFragment=actividadFragment
                    true

                }
                R.id.Misactividades->{

                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(miactividadFragment).commit()
                    mActiveFragment=miactividadFragment
                    true

                }
                else->false
            }


        }

    }

 /*   private fun setupRecyclegrupo(){

        //base de datos
        db?.Conect()
        grupos=db?.selectgrupo()

        mGridLayout= GridLayoutManager(this,1)

        val adapter1=GrupoAdaptador(grupos)

        val grupo=Grupos(1,"aleman","grupoaleman",20,"grupo de alemanes en españa","www.ggoo.es")
        adapter1.add(grupo)

        gBinding.recycleviewgrupo.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=adapter1

        }


    }*/

}