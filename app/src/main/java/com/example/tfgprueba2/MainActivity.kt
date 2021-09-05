package com.example.tfgprueba2

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.Dataclass.Usuario
import com.example.tfgprueba2.databinding.ActivityMainBinding
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivityMainBinding

    private lateinit var mActiveFragment:Fragment
    private lateinit var mFragmentManager: FragmentManager

    lateinit var grupos: MutableList<Grupos>


    //var db = conect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupBottomNav()

        mBinding.botonCrearGrupos.setOnClickListener {

            enviaracreaciongrupo()

        }

        mBinding.botonCrearActividad.setOnClickListener {
            var usuario=who()
            var db = conect()
            db?.Conect()
            if(db.eresmiembrodeunaactividad(usuario.idusuario)) {
                enviaracreacionactividad()
            }
            else{
                Toast.makeText(this, R.string.nogroup, Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.perfil.setOnClickListener {
            enviaraperfil()
        }

        mBinding.house.setOnClickListener {
            enviaracreacionhouse()
        }

        mBinding.busquedaavanzada.setOnClickListener {

            enviarabusquedad()
        }

        mBinding.recargar.setOnClickListener {
            recargarpágina()
        }
    }

    fun enviarabusquedad(){
        val i = Intent(this, Busqueda::class.java)
        var usuario=who()
        i.putExtra("idUsuario", usuario )

        startActivity(i)

    }

    fun recargarpágina(){
        val i = Intent(this, MainActivity::class.java)
        var usuario=who()
        i.putExtra("idUsuario",usuario.idusuario)
        i.putExtra("Nombre",usuario.nombre)
        i.putExtra("contraseña",usuario.contraseña)
        i.putExtra("foto",usuario.foto)
        i.putExtra("biografia",usuario.biografia)
        i.putExtra("fecha",usuario.fecha)
        i.putExtra("direccion",usuario.direccion)
        startActivity(i)

    }


    fun enviaracreacionactividad(){
        val i = Intent(this, CrearActividad::class.java)
         var usuario=who()
         i.putExtra("idUsuario",usuario.idusuario)
         i.putExtra("Nombre",usuario.nombre)
         i.putExtra("contraseña",usuario.contraseña)
         i.putExtra("foto",usuario.foto)
         i.putExtra("biografia",usuario.biografia)
         i.putExtra("fecha",usuario.fecha)
         i.putExtra("direccion",usuario.direccion)
        startActivity(i)

    }


    fun enviaracreaciongrupo(){
        val i = Intent(this, CrearGrupoActivity2::class.java)
         var usuario=who()
         i.putExtra("idUsuario",usuario.idusuario)
         i.putExtra("Nombre",usuario.nombre)
         i.putExtra("contraseña",usuario.contraseña)
         i.putExtra("foto",usuario.foto)
         i.putExtra("biografia",usuario.biografia)
         i.putExtra("fecha",usuario.fecha)
         i.putExtra("direccion",usuario.direccion)
        startActivity(i)

    }







    fun enviaracreacionhouse(){
        val i = Intent(this, CrearActivityEstablecimiento::class.java)
        var usuario=who()
        i.putExtra("idUsuario",usuario.idusuario)
        i.putExtra("Nombre",usuario.nombre)
        i.putExtra("contraseña",usuario.contraseña)
        i.putExtra("foto",usuario.foto)
        i.putExtra("biografia",usuario.biografia)
        i.putExtra("fecha",usuario.fecha)
        i.putExtra("direccion",usuario.direccion)
        startActivity(i)

    }

    //enviar
    fun enviaraperfil(){
        val i = Intent(this, PerfilActivity::class.java)
        var usuario=who()
        i.putExtra("idUsuario",usuario.idusuario)
        i.putExtra("Nombre",usuario.nombre)
        i.putExtra("contraseña",usuario.contraseña)
        i.putExtra("foto",usuario.foto)
        i.putExtra("biografia",usuario.biografia)
        i.putExtra("fecha",usuario.fecha)
        i.putExtra("direccion",usuario.direccion)
        startActivity(i)

    }

    fun busqueda():String?{

        val objetoIntent:Intent=intent
        var direccion=objetoIntent.getStringExtra("cadenaString")
        return (direccion)

    }


    ///cargaremos los datos del usuario
    fun who():Usuario{

        val objetoIntent:Intent=intent

        var id=objetoIntent.getIntExtra("idUsuario",1)

        var nombre=objetoIntent.getStringExtra("Nombre")
        var contraseña=objetoIntent.getStringExtra("contraseña")
        var foto=objetoIntent.getStringExtra("foto")
        var biografia=objetoIntent.getStringExtra("biografia")
        var fecha= objetoIntent.getStringExtra("fecha")
        var direccion=objetoIntent.getStringExtra("direccion")

        val usuario= Usuario(idusuario=id, nombre= nombre.toString(),
            contraseña=contraseña.toString(),foto=foto.toString(),
            biografia=biografia.toString(), fecha=fecha.toString(),
            direccion=direccion.toString())

        return usuario

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


}

