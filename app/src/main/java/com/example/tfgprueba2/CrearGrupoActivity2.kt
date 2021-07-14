package com.example.tfgprueba2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.Dataclass.Lugar
import com.example.tfgprueba2.Dataclass.Usuario

class CrearGrupoActivity2 : AppCompatActivity() {

    private var spinner: Spinner?=null
    private var arrayAdapter:ArrayAdapter<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_grupo2)

        var usuario=who()

        var db = conect()
        db?.Conect()
        var idGrupo=db?.ObtenerFilasGrupos()

        var tipoidioma=db?.buscartipoIdioma()

        var items1=""
        spinner=findViewById(R.id.mySpinneridiomagrupo)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,tipoidioma)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearGrupoActivity2,tipoidioma[position], Toast.LENGTH_SHORT).show()
                 items1=parent?.getItemAtPosition(position) as String
                println(items1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


//////////////////

        var tipotamnaio=db?.buscartipoTamanio()
        var items2=""
        spinner=findViewById(R.id.mySpinnertamaiogrupo)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,tipotamnaio)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearGrupoActivity2,tipotamnaio[position], Toast.LENGTH_SHORT).show()
                items2=parent?.getItemAtPosition(position) as String
                println(items2)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }




        ///guardar los datos en la base de datos de grupos.

        val grupo= Grupos(idGrupo=idGrupo+1,idUsuario=usuario.idusuario,nombre = "",fecha="",proposito = "",urlgrupo = "")
        val botonregistrarse =findViewById(R.id.btn_registarGrupo) as Button

        botonregistrarse.setOnClickListener {


            var datos1=db?.buscarDatosIdiomatipo(items1)

            var datos2=db?.buscarDatostamaniogrupo(items2)



            with(grupo){

                nombre=(findViewById(R.id.sign_nombre_grupo) as EditText).text.toString().trim()
                fecha=(findViewById(R.id.sign_fechagrupo) as EditText).text.toString().trim()
                proposito= (findViewById(R.id.sign_grupo_proposito) as EditText).text.toString().trim()
                urlgrupo=(findViewById(R.id.sign_grupo_url) as EditText).text.toString().trim()

            }

            db?.intoGrupo(grupo)


            db?.intoParaGrupo(datos1,grupo.idGrupo)               //unimos los dos parametros
            db?.intoParaValordebilGrupo(datos1,grupo.idGrupo)



            //error
            db?.intoParaGrupo(datos2,grupo.idGrupo)               //unimos los dos parametros
            db?.intoParaValordebilGrupo(datos2,grupo.idGrupo)

            db?.intousergroup(usuario.idusuario, grupo.idGrupo)

            Toast.makeText(this, R.string.Grupo_register, Toast.LENGTH_SHORT).show()
            enviaraperfil()
        }



    }


    fun who(): Usuario {

        val objetoIntent: Intent =intent

        var id=objetoIntent.getIntExtra("idUsuario",1)
        var nombre=objetoIntent.getStringExtra("Nombre")
        var contraseña=objetoIntent.getStringExtra("contraseña")
        var foto=objetoIntent.getStringExtra("foto")
        var biografia=objetoIntent.getStringExtra("biografia")
        var fecha= objetoIntent.getStringExtra("fecha")
        var direccion=objetoIntent.getStringExtra("direccion")
        val usuario= Usuario(idusuario=id, nombre= nombre.toString(), contraseña=contraseña.toString(),foto=foto.toString(), biografia=biografia.toString(), fecha=fecha.toString(), direccion=direccion.toString())
        return usuario
    }

    fun enviaraperfil(){
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
}