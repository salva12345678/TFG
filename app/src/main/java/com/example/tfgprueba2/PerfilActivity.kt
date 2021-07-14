package com.example.tfgprueba2

import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.example.tfgprueba2.Dataclass.Usuario

import com.example.tfgprueba2.databinding.ActivityPerfilBinding
import com.squareup.picasso.Picasso

class PerfilActivity : AppCompatActivity() {

    lateinit var texto1:TextView


    private lateinit var mBinding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        mBinding= ActivityPerfilBinding.inflate(layoutInflater)

        cargarperfil()

        val botonregistrarse =findViewById(R.id.btn_inicio) as Button

        botonregistrarse.setOnClickListener {
            enviar()
        }

    }

    fun cargarperfil(){

        var usuario=whoRecibido()
        texto1=findViewById(R.id.texto_Nombre_introducir)
        texto1.setText(usuario.nombre)
        texto1=findViewById(R.id.texto_biografia_introducir)
        texto1.setText(usuario.biografia)
        texto1=findViewById(R.id.texto_fecha_introducir)
        texto1.setText(usuario.fecha)
        texto1=findViewById(R.id.texto_Direccion_introducir)
        texto1.setText(usuario.direccion)

        var ivBasicImage: ImageView = findViewById(R.id.fotoperfil) as ImageView
        Picasso.get().load(usuario.foto).into(ivBasicImage)

    }

    fun whoRecibido(): Usuario {

        val objetoIntent: Intent =intent

        var id=objetoIntent.getIntExtra("idUsuario",1)
        var nombre=objetoIntent.getStringExtra("Nombre")
        var contraseña=objetoIntent.getStringExtra("contraseña")
        var foto=objetoIntent.getStringExtra("foto")
        var biografia=objetoIntent.getStringExtra("biografia")
        var fecha= objetoIntent.getStringExtra("fecha")
        var direccion=objetoIntent.getStringExtra("direccion")

        println(nombre)
        val usuario= Usuario(idusuario=id, nombre= nombre.toString(), contraseña=contraseña.toString(),foto=foto.toString(), biografia=biografia.toString(), fecha=fecha.toString(), direccion=direccion.toString())
        return usuario
    }

    fun enviar(){
        val i = Intent(this, MainActivity::class.java)
        var usuario=whoRecibido()
        i.putExtra("idUsuario",usuario.idusuario)
        i.putExtra("Nombre",usuario.nombre)
        i.putExtra("contraseña",usuario.contraseña)
        i.putExtra("foto",usuario.foto)
        i.putExtra("biografia",usuario.biografia)
        i.putExtra("fecha",usuario.fecha)
        i.putExtra("direccion",usuario.direccion)
        startActivity(i)

    }

}