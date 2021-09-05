package com.example.tfgprueba2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.tfgprueba2.Dataclass.Usuario
import java.io.Serializable

class Busqueda : AppCompatActivity() {

    private var spinner: Spinner?=null
    private var arrayAdapter:ArrayAdapter<String>?=null
    private var checkBox1:CheckBox?=null
    private var checkBox2:CheckBox?=null
    private var checkBox3:CheckBox?=null
    private var checkBox4:CheckBox?=null
    private var checkBox5:CheckBox?=null
    private var checkBox6:CheckBox?=null
    private var checkBox7:CheckBox?=null



    private var checkBoxuunion:CheckBox?=null
    private var checkBoxinterseccion:CheckBox?=null

    private var ranking1:CheckBox?=null
    private var ranking2:CheckBox?=null
    private var ranking3:CheckBox?=null
    private var ranking4:CheckBox?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda)

        checkBox1=findViewById(R.id.checkBoxIdioma)
        checkBox2=findViewById(R.id.checkBoxTamanio)
        checkBox3=findViewById(R.id.checkBoxfecha)
        checkBox4=findViewById(R.id.checkBoxDuracion)
        checkBox5=findViewById(R.id.checkBoxLugarActividad)
        checkBox6=findViewById(R.id.checkBoxcompetenActividad)
        checkBox7=findViewById(R.id.checkBoxtipoActividad)

       // checkBox1?.isEnabled=false
        checkBoxuunion=findViewById(R.id.checkBoxunion)
        checkBoxinterseccion=findViewById(R.id.checkBoxinterseccion)

        ranking1=findViewById(R.id.checkBoxmodo1)
        ranking2=findViewById(R.id.checkBoxmodo2)
        ranking3=findViewById(R.id.checkBoxmodo3)
        ranking4=findViewById(R.id.checkBoxmodo4)

       /* checkBoxinterseccion?.setOnClickListener{

            checkBox1?.isEnabled= true

        }*/

        var db = conect()
        db?.Conect()

        var tipoidioma=db?.buscartipoIdioma()


        ///////////// idioma
        var items1=""
        spinner=findViewById(R.id.mySpinneridiomabusquedad)
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
                Toast.makeText(this@Busqueda,tipoidioma[position], Toast.LENGTH_SHORT).show()
                items1=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        /////////////// tamanio
        var tamanioequipo=db?.buscartipoTamanioequipo()
        var items2=""
        spinner=findViewById(R.id.mySpinnertamaiobusquedad)
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
                Toast.makeText(this@Busqueda,tamanioequipo[position], Toast.LENGTH_SHORT).show()
                items2=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        ///////////duracion
        var duracion=db?.buscartipoduracion()
        var items3=""
        spinner=findViewById(R.id.mySpinneduracionbusquedad)
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
                Toast.makeText(this@Busqueda,duracion[position], Toast.LENGTH_SHORT).show()
                items3=parent?.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        /////////lugar

        var tipolugar=db?.buscartipoLugar()

        var items4=""
        spinner=findViewById(R.id.mySpinnelugarbusquedad)
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
                Toast.makeText(this@Busqueda,tipolugar[position],Toast.LENGTH_SHORT).show()
                items4=parent?.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        ////////////
        ///////////

        var compete=db?.buscacompetencias()
        var items5=""

        spinner=findViewById(R.id.mySpinnecompetenbusquedad)
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
                Toast.makeText(this@Busqueda,compete[position], Toast.LENGTH_SHORT).show()

                items5=parent?.getItemAtPosition(position) as String

                /*botonregistrarcompetencia.setOnClickListener {

                    competencias.add(items6)
                    Toast.makeText(this@CrearActividad, R.string.competence_add, Toast.LENGTH_SHORT).show()
                    println(items6)

                }*/

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

///////////


        var tipoactividad=db?.buscatiposactividad()
        var items6=""

        spinner=findViewById(R.id.mySpinnetipobusquedad)
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
                Toast.makeText(this@Busqueda,tipoactividad[position], Toast.LENGTH_SHORT).show()

                 items6=parent?.getItemAtPosition(position) as String

               /* botonregistrartipoactividad.setOnClickListener {

                    tipoactiviadades.add(items)
                    Toast.makeText(this@CrearActividad, R.string.actividda_add, Toast.LENGTH_SHORT).show()

                }*/
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


/////////////
        val botonregistraractividad =findViewById(R.id.btn_Hacerbusqueda) as Button
        var  conexion=""



        var cadena2="SELECT public.\"Actividad\".\"idActividad\", public.\"Actividad\".\"idUsuario\", public.\"Actividad\".\"idGrupo\", public.\"Actividad\".\"Descripcion\", public.\"Actividad\".\"nombre\", public.\"Actividad\".\"Fecha\", rank \n" +
                "FROM ("


        botonregistraractividad.setOnClickListener {

            if(checkBoxinterseccion?.isChecked()==true){

                conexion=" intersect "

            }

            if(checkBoxuunion?.isChecked()==true){

                conexion=" union "

            }


            if(checkBox1?.isChecked() == true){

                cadena2+="(SELECT DISTINCT \"idActividad\" \n" +
                        "                           FROM public.\"BuscaIdioma\" where valor='$items1')"

                Toast.makeText(this@Busqueda,"Idioma",Toast.LENGTH_SHORT).show()
            }

            if(checkBox2?.isChecked() == true){
                if(!cadena2.isEmpty()){
                    cadena2+=conexion
                }
                //////////////

                val tama: Map<String, Int> = mapOf( Pair("Pareja", 2),
                    Pair("Pequeño", 4),
                    Pair("Mediano", 6),
                    Pair("Grande", 8))

                ////////
                val tam: Int? = tama[items2]

                cadena2+="(SELECT \"idActividad\" \n" +
                        "\tFROM public.\"BuscaEntero\" where valor='$tam')"

                Toast.makeText(this@Busqueda,"Tamaño",Toast.LENGTH_SHORT).show()
            }

            if(checkBox3?.isChecked() == true){
                if(!cadena2.isEmpty()){
                    cadena2+=conexion
                }
                
                var fecha=(findViewById(R.id.sign_fechabusqueda) as EditText).text.toString().trim()

                cadena2+="(SELECT DISTINCT  \"idActividad\" \n" +
                        "\tFROM public.\"Actividad\" where \"Fecha\"='$fecha')"

                Toast.makeText(this@Busqueda,"Fecha",Toast.LENGTH_SHORT).show()
            }

            if(checkBox4?.isChecked() == true){
                if(!cadena2.isEmpty()){

                    cadena2+=conexion
                }

                cadena2+="(SELECT DISTINCT \"idActividad\" \n" +
                        "                        FROM public.\"BuscaEtiqueta\" where valor='$items3')"
                Toast.makeText(this@Busqueda,"Duración",Toast.LENGTH_SHORT).show()
            }

            if(checkBox5?.isChecked() == true){
                if(!cadena2.isEmpty()){
                    cadena2+=conexion
                }
                cadena2+="(SELECT \"idActividad\" \n" +
                        "\tFROM public.\"BuscaLugar\"\n" +
                        "\twhere valor='$items4')"
                Toast.makeText(this@Busqueda,"Lugar",Toast.LENGTH_SHORT).show()
            }

            if(checkBox6?.isChecked() == true){
                if(!cadena2.isEmpty()){
                    cadena2+=conexion
                }

                cadena2+="(SELECT \"idActividad\" \n" +
                        "\tFROM public.\"BuscaCompetencias\" where nombre='$items5')"

                Toast.makeText(this@Busqueda,"Competencias",Toast.LENGTH_SHORT).show()
            }

            if(checkBox7?.isChecked() == true){
                if(!cadena2.isEmpty()){

                    cadena2+=conexion
                }


                cadena2+="(SELECT \"idActividad\" \n" +
                        "\tFROM public.\"BuscaTipoActividad\"\n" +
                        "\twhere nombre='$items6')"
                Toast.makeText(this@Busqueda,"Tipo de actividad",Toast.LENGTH_SHORT).show()
            }



            cadena2+=")"

            if (ranking1?.isChecked()==true){

                cadena2+="AS resultado \n" +
                        "JOIN public.\"Ranking1\" ON resultado.\"idActividad\" = public.\"Ranking1\".\"idActividad\" \n" +
                        "JOIN public.\"Actividad\" ON public.\"Ranking1\".\"idActividad\" = public.\"Actividad\".\"idActividad\" \n" +
                        "ORDER BY rank desc;"

            }

            if (ranking2?.isChecked()==true){

                cadena2+="AS resultado \n" +
                        "JOIN public.\"Ranking2\" ON resultado.\"idActividad\" = public.\"Ranking2\".\"idActividad\" \n" +
                        "JOIN public.\"Actividad\" ON public.\"Ranking2\".\"idActividad\" = public.\"Actividad\".\"idActividad\" \n" +
                        "ORDER BY rank desc;"

            }

            if (ranking3?.isChecked()==true){

                cadena2+="AS resultado \n" +
                        "JOIN public.\"Ranking3\" ON resultado.\"idActividad\" = public.\"Ranking3\".\"idActividad\" \n" +
                        "JOIN public.\"Actividad\" ON public.\"Ranking3\".\"idActividad\" = public.\"Actividad\".\"idActividad\" \n" +
                        "ORDER BY rank desc;"

            }

            if (ranking4?.isChecked()==true){

                cadena2+="AS resultado \n" +
                        "JOIN public.\"Ranking4\" ON resultado.\"idActividad\" = public.\"Ranking4\".\"idActividad\" \n" +
                        "JOIN public.\"Actividad\" ON public.\"Ranking4\".\"idActividad\" = public.\"Actividad\".\"idActividad\" \n" +
                        "ORDER BY rank desc;"

            }



            enviarabusquedad(cadena2)


        }


    }

    fun enviarabusquedad(cadena:String){
        val i = Intent(this, MainActivity::class.java)
        var usuario=recibidobusquedad()

        i.putExtra("idUsuario",usuario?.idusuario)
        i.putExtra("Nombre",usuario?.nombre)
        i.putExtra("contraseña",usuario?.contraseña)
        i.putExtra("foto",usuario?.foto)
        i.putExtra("biografia",usuario?.biografia)
        i.putExtra("fecha",usuario?.fecha)
        i.putExtra("direccion",usuario?.direccion)
        i.putExtra("cadenaString",cadena)

        startActivity(i)

    }

    fun recibidobusquedad(): Usuario? {
        val objetoIntent:Intent=intent

        var usuario = objetoIntent.getParcelableExtra("idUsuario") as Usuario?

        return (usuario)
    }




}