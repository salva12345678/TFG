package com.example.tfgprueba2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.anurag.multiselectionspinner.MultiSelectionSpinnerDialog
import com.example.tfgprueba2.Dataclass.Actividad
import com.example.tfgprueba2.Dataclass.Grupos
import com.example.tfgprueba2.Dataclass.Usuario
import com.example.tfgprueba2.databinding.ActivityCrearActividadBinding

class CrearActividad : AppCompatActivity() {

    val competencias = mutableListOf<String>()
    val tipoactiviadades= mutableListOf<String>()
    private var spinner: Spinner?=null
    private var arrayAdapter: ArrayAdapter<String>?=null

    private lateinit var mBinding: ActivityCrearActividadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_actividad)
        mBinding= ActivityCrearActividadBinding.inflate(layoutInflater)

        var usuario=who()

        var db = conect()
        db?.Conect()

        ///Grupos que ha creado el usuario conectado.

        var items1=""

        var misgrupos=db?.buscarGruposdelusuarioPropietario(usuario.idusuario)

        spinner=findViewById(R.id.mySpinnergrupo)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,misgrupos)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,misgrupos[position], Toast.LENGTH_SHORT).show()
                items1=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



        ///////////////////////////////////////////
        //mostrar lugares disponibles.
        var lugares=db?.buscartipolugaresdisponibles()
        var items2=""

        spinner=findViewById(R.id.mySpinnerlugar)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,lugares)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,lugares[position], Toast.LENGTH_SHORT).show()
                items2=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        ////////////////

        var tamanioequipo=db?.buscartipoTamanioequipo()
        var items3=""
        spinner=findViewById(R.id.mySpinnertamaio)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,tamanioequipo)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,tamanioequipo[position], Toast.LENGTH_SHORT).show()
                items3=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //////////////////////////////////////////////////
        // duracion de la actividad
        var duracion=db?.buscartipoduracion()
        var items4=""
        spinner=findViewById(R.id.mySpinnerduracion)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,duracion)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,duracion[position], Toast.LENGTH_SHORT).show()
                items4=parent?.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

       ////////////////////////////////
        //////tipo de relacion
        var relacion=db?.buscartiporelacion()
        var items5=""
        spinner=findViewById(R.id.mySpinnerrelacion)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,relacion)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,relacion[position], Toast.LENGTH_SHORT).show()
                items5=parent?.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }




      //  var hola="ghola"
       // competencias.add(hola)

        ////competenciaaaaaasssssssssssss

        var compete=db?.buscacompetencias()
        var items6=""
        val botonregistrarcompetencia =findViewById(R.id.cargarcompetencia) as ImageButton

        spinner=findViewById(R.id.mySpinnercompe)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_list_item_checked,compete)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,compete[position], Toast.LENGTH_SHORT).show()

                 items6=parent?.getItemAtPosition(position) as String

                botonregistrarcompetencia.setOnClickListener {

                    competencias.add(items6)
                    Toast.makeText(this@CrearActividad, R.string.competence_add, Toast.LENGTH_SHORT).show()
                    println(items6)

                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        /////////////////

        var tipoactividad=db?.buscatiposactividad()
        val botonregistrartipoactividad =findViewById(R.id.cargaractividad) as ImageButton

        spinner=findViewById(R.id.mySpinneractividad)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_list_item_checked,tipoactividad)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,tipoactividad[position], Toast.LENGTH_SHORT).show()

                var items:String=parent?.getItemAtPosition(position) as String

                botonregistrartipoactividad.setOnClickListener {

                    tipoactiviadades.add(items)
                    Toast.makeText(this@CrearActividad, R.string.actividda_add, Toast.LENGTH_SHORT).show()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }




        ///////
        //primer parametro
        var param1=db?.buscarparámetro()
        var items7=""
        spinner=findViewById(R.id.parametro1)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_list_item_checked,param1)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,param1[position], Toast.LENGTH_SHORT).show()
                items7=parent?.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        /*
        db?.buscarparametrosfleicxle(items7)

        var aux=""
        var listaaux=mutableListOf<String>()
        //listaaux.add(aux)
        var items8=""
        spinner=findViewById(R.id.parametro1_2)
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_list_item_checked,listaaux)
        spinner?.adapter=arrayAdapter
        spinner?.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@CrearActividad,listaaux[position], Toast.LENGTH_SHORT).show()
                items8=parent?.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

*/


        ////
        //segundo parámetro
        //var param2=db?.buscarparámetro()

       ////////////////////////




        val botonregistraractividad =findViewById(R.id.btn_registaractividad) as Button

        ///sacar el numero de actividades que hay
        var idActividad=db?.ObtenerFilasActividades()
        //////sacar el numero de configuraciones que existen
        var Nconfiguraciones=db?.ObtenerFilasConfiguraciones()

        botonregistraractividad.setOnClickListener {

            var idgrupo=db?.buscarDatosdelgrupo(items1)
            val actividad= Actividad(idActividad+1,usuario.idusuario,idgrupo[0].idParametro,"","","")
            with(actividad){

                descripcion=(findViewById(R.id.sign_descripcion) as EditText).text.toString().trim()
                nombre= (findViewById(R.id.sign_nombreactividad) as EditText).text.toString().trim()
                Fecha=(findViewById(R.id.sign_fechaactividad) as EditText).text.toString().trim()

            }
            ////crear actividad
            db?.intoActividad(actividad)

            var idLugar=db?.buscarDatosdellugar(items2)
            //REGISTRAMOS EL LUGAR

            db?.intoSeRealiza(idLugar[0].idParametro,actividad.idActividad)
            /// EL LUGAR QUEDA REGISTRADO Y SE ANULA
            db?.actualizarlugar(idLugar[0].idParametro)

            //////////creamos la configuración
            db?.intoconfiguraci(Nconfiguraciones+1)
            db?.intoActividadTieneConfi(actividad.idActividad,Nconfiguraciones+1)

            /////creamos el tamaño del equipo
            var datos1equipo=db?.buscarDatostamanioequipo(items3)
            db?.intoParaConfi(datos1equipo,Nconfiguraciones+1)
            db?.intoParaValorConfi(datos1equipo,Nconfiguraciones+1)

            //////// duracion de la actividad
            var duracionequipo=db?.buscarDatosduracionactividad(items4)
            db?.intoParaConfi(duracionequipo,Nconfiguraciones+1)
            db?.intoParaValorConfi(duracionequipo,Nconfiguraciones+1)

            ///////////tipo de relacion
            var tipoRelacion=db?.buscarDatosrelacion(items5)
            db?.intoParaConfi(tipoRelacion,Nconfiguraciones+1)
            db?.intoParaValorConfi(tipoRelacion,Nconfiguraciones+1)

            /////////
            ////competenciaaaaaasssssssssssss
            var tipocompetencias=db?.buscarCompetencias(competencias)
            db?.intoCompetencia(tipocompetencias,Nconfiguraciones+1)
            //////tipo de actividad
            var tipoactividad=db?.buscarTipoActividad(tipoactiviadades)
            db?.intotipoActividad(tipoactividad,Nconfiguraciones+1)

            db?.intouseractivity(usuario.idusuario, actividad.idActividad)

            Toast.makeText(this, R.string.Activity_register, Toast.LENGTH_SHORT).show()
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