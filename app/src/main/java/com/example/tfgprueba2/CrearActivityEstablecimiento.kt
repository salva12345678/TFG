package com.example.tfgprueba2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tfgprueba2.Dataclass.Lugar
import com.example.tfgprueba2.Dataclass.LugarParametros
import com.example.tfgprueba2.Dataclass.Usuario
import com.example.tfgprueba2.databinding.ActivityCrearEstablecimientoBinding
import org.postgresql.jdbc.PgResultSet.toDouble

class CrearActivityEstablecimiento : AppCompatActivity() {

    private var spinner:Spinner?=null
    private var arrayAdapter:ArrayAdapter<String>?=null

    private lateinit var mBinding: ActivityCrearEstablecimientoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_establecimiento)
        mBinding = ActivityCrearEstablecimientoBinding.inflate(layoutInflater)

        //spiner1()


        var usuario=who()

        var idlugar:Int=0
        var db = conect()
        db?.Conect()
        idlugar=db?.ObtenerFilaslugares()


        var tipolugar=db?.buscartipoLugar()

        var items=""
        spinner=findViewById(R.id.mySpinner1)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,tipolugar)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActivityEstablecimiento,tipolugar[position],Toast.LENGTH_SHORT).show()
                items=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        //

        var par=db?.buscarDisponibilidad()

        var items1=""
        spinner=findViewById(R.id.mySpinner)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,par )
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActivityEstablecimiento,par[position],Toast.LENGTH_SHORT).show()
                items1=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



        val lugar= Lugar(idLugar=idlugar, nombre="", localizacion="",precio=0.0, fecha="", disponibilidad=false)

        val botonregistrarse =findViewById(R.id.btn_registarlugar) as Button

        botonregistrarse.setOnClickListener {

            var datos1=db?.buscarDatoslugartipo(items)
            var datos=db?.buscarDatos(items1.toBoolean())

            with(lugar){

                idLugar=idlugar+1
                nombre=(findViewById(R.id.sign_establecimiento) as EditText).text.toString().trim()
                localizacion=(findViewById(R.id.sign_localizacionlugar) as EditText).text.toString().trim()
                var preci=(findViewById(R.id.sign_preciolugar) as EditText).text.toString().trim()
                precio=toDouble(preci)
                fecha=(findViewById(R.id.sign_fechalugar) as EditText).text.toString().trim()

            }

            if((findViewById(R.id.sign_establecimiento) as EditText).text.toString().trim().isEmpty() || (findViewById(R.id.sign_localizacionlugar) as EditText).text.toString().trim().isEmpty()
                ||(findViewById(R.id.sign_preciolugar) as EditText).text.toString().trim().isEmpty()||(findViewById(R.id.sign_fechalugar) as EditText).text.toString().trim().isEmpty()
                ){

                Toast.makeText(this, R.string.emply_login, Toast.LENGTH_SHORT).show()
            }
            else {


                db?.intoEstablecimiento(lugar, usuario.idusuario) ///metemos el establecimiento


                db?.intoPara(datos, lugar.idLugar)               //unimos los dos parametros
                db?.intoParaValordebil(datos, lugar.idLugar)



                db?.intoParalugar(datos1, lugar.idLugar)               //unimos los dos parametros
                db?.intoParaValordebillugar(datos1, lugar.idLugar)


                Toast.makeText(this, R.string.house_register, Toast.LENGTH_SHORT).show()
                enviaraperfil()
            }
        }


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

    fun spinerdisponibilidad(par: MutableList<String>):String{

        var items=""
        spinner=findViewById(R.id.mySpinner)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,par )
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActivityEstablecimiento,par[position],Toast.LENGTH_SHORT).show()
                items=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return items

    }

    fun spinertipolugar(par: MutableList<String>):String{
        var items=""
        spinner=findViewById(R.id.mySpinner1)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,par)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActivityEstablecimiento,par[position],Toast.LENGTH_SHORT).show()
                 items=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return items

    }


    fun who():Usuario{

        val objetoIntent: Intent =intent

        var id=objetoIntent.getIntExtra("idUsuario",1)
        //println(id)
        var nombre=objetoIntent.getStringExtra("Nombre")
        //println(nombre)
        var contraseña=objetoIntent.getStringExtra("contraseña")
        //println(contraseña)
        var foto=objetoIntent.getStringExtra("foto")
        //println(foto)
        var biografia=objetoIntent.getStringExtra("biografia")
        //println(biografia)
        var fecha= objetoIntent.getStringExtra("fecha")
        //println(fecha)
        var direccion=objetoIntent.getStringExtra("direccion")
        //println(direccion)


        val usuario= Usuario(idusuario=id, nombre= nombre.toString(), contraseña=contraseña.toString(),foto=foto.toString(), biografia=biografia.toString(), fecha=fecha.toString(), direccion=direccion.toString())
        return usuario
    }


    }
