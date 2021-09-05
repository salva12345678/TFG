package com.example.tfgprueba2

import android.annotation.SuppressLint
import com.example.tfgprueba2.Dataclass.*
import java.sql.Connection
import java.sql.DriverManager


class conect {

    private var connection: Connection? = null

    private val host = "35.189.243.158"

    private val database = "postgres"
    private val port = 5432
    private val user = "postgres"
    private val pass = "eee"
    private var url = "jdbc:postgresql://%s:%d/%s"
    private var status = false


   //lateinit var grupo:Grupos
   lateinit var hola:String
    var hola1:Int = 0
    //lateinit var grupos: MutableList<Grupos>
    val grupos = mutableListOf<Grupos>()
    val actividades = mutableListOf<Actividad>()

    val actividades1 = mutableListOf<Actividad>()

    val misgrupos=mutableListOf<clasemisgrupos>()

    val misgruposparametrosidioma=mutableListOf<GruposParametros>()
    val misgruposparametrostam=mutableListOf<GruposParametros>()
    val misgruposparametrosprop=mutableListOf<GruposParametros>()


    val misact1=mutableListOf<GruposParametros>()
    val misact4=mutableListOf<GruposParametros>()
    val misact3=mutableListOf<GruposParametros>()

    var cadena=mutableListOf<String>()
    var cadena1=mutableListOf<String>()
    var cadena2=mutableListOf<String>()

    fun Conect() {
        url = String.format(url, host, port, database)
        connect()
        //this.disconnect();

        //println("connection status:$status")
    }

    @SuppressLint("newApi")
    fun connect() {
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                connection = DriverManager.getConnection(url, user, pass)
                status = true
               // println("connected:$status")
            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun selectgrupo(): MutableList<Grupos> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "select * from public.\"Grupos\";"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= Grupos(rs?.getInt(1),rs?.getInt(2),rs?.getString(3),rs?.getString(4),rs?.getString(5),rs?.getString(6))


                    grupos.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return grupos

    }


    fun getExtraConnection(): Connection? {
        var c: Connection? = null
        try {
            Class.forName("org.postgresql.Driver")
            c = DriverManager.getConnection(url, user, pass)
            val stsql = "Select * from Grupos;"
            val st = c.createStatement()
            val rs = st.executeQuery(stsql)
            rs.next()
            //println(rs.getString(1))
            c.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return c
    }

    ///busca si existe el usuario en la base de datos y lo devulve
    fun selectUsuario(Nombre:String,Contraseña:String):Usuario {
        var usuario=Usuario(idusuario=0, nombre="", contraseña="",foto="", biografia="", fecha="", direccion="")

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT * FROM public.\"Usuario\" WHERE  \"Nombre\"='$Nombre' AND \"Contraseña\" = '$Contraseña';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos

                    usuario=Usuario(rs?.getInt(1),rs?.getString(2),rs?.getString(3),rs?.getString(4),rs?.getString(5),rs?.getString(6),rs?.getString(7))

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return usuario
    }


    fun intoUsuario(usuario: Usuario) {

        val idusuario:Int =usuario.idusuario
        val nombre: String = usuario.nombre
        val foto:String =usuario.foto
        val biografia:String =usuario.biografia
        val fecha:String =usuario.fecha
        val direccion:String =usuario.direccion
        val contraseña:String=usuario.contraseña

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"Usuario\"(\n" +
                        "\t\"idUsuario\", \"Nombre\", \"Foto\", \"Biografía\", \"fecha\", \"Direccion\",\"Contraseña\")\n" +
                        "\tVALUES ($idusuario, '$nombre','$foto' ,'$biografia', '$fecha','$direccion','$contraseña');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

//de aqui sacamos el numero de filas de usuarios
    fun ObtenerFilas(): Int {
    var idnuevo:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.\"Usuario\";"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idnuevo=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idnuevo
    }

    fun buscarUsuario(Nombre:String,Contraseña:String) :Boolean {
        var r:Boolean=false
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"Nombre\", \"Contraseña\" FROM public.\"Usuario\" WHERE  \"Nombre\"='$Nombre' AND \"Contraseña\" = '$Contraseña';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                r = rs!!.next()
               // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return r
    }


    fun ObtenerFilaslugares(): Int {
        var idnuevo:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.\"Lugar\";"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idnuevo=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idnuevo
    }


    fun intoEstablecimiento(lugar: Lugar,idusuario:Int) {

        val idlugar:Int =lugar.idLugar
        val nombre: String = lugar.nombre
        val loca:String=lugar.localizacion
        val precio:Double=lugar.precio
        val fecha:String =lugar.fecha


        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"Lugar\"(\n" +
                        "\t\"idLugar\", \"idUsuario\", \"nombre\", \"localización\", \"precio\", \"Fecha\")\n" +
                        "\tVALUES ($idlugar, '$idusuario','$nombre' ,'$loca',$precio, '$fecha');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    ///para encontrar la disponibilidad
    fun buscarDisponibilidad():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"ValoresParametroLógico\";"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    //println(rs?.getBoolean(1))
                    Totalpar.add((rs?.getBoolean(1).toString()))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }
    //buscar cosas de la disponibilidad
    fun buscarDatos(condicion:Boolean):MutableList<LugarParametros>{

        var listpar= mutableListOf<LugarParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = " SELECT \"idParametro\", \"idValor\", valor\n" +
                        "        FROM public.\"valorLogico\" where valor=$condicion;"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = LugarParametros(rs?.getInt(1), rs?.getInt(2), rs?.getBoolean(3))

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }

    fun intoPara(lugar: MutableList<LugarParametros>,idLugar:Int) {


        val idlugar:Int =idLugar
        val idPara:Int =lugar[0].idParametro

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"LugarTieneParametro\"(\n" +
                        "\t\"idLugar\", \"idParametro\")\n" +
                        "\tVALUES ($idlugar, $idPara);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

               // connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }
    fun intoParaValordebil(lugar: MutableList<LugarParametros>,idLugar:Int) {

        val idPara:Int =lugar[0].idParametro
        val idParavalor:Int =lugar[0].idParametrovalor

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
               // var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"LugarTieneValorParámetro\"(\n" +
                        "\t\"idLugar\", \"idParametro\", \"idValor\")\n" +
                        "\tVALUES ($idLugar, $idPara, $idParavalor);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }




    ///para encontrar los datos del tipo lugar
    fun buscartipoLugar():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"ValoresParametro\" where \"idParametro\"=1;"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
               // connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }
    fun buscarDatoslugartipo(lugar:String): MutableList<LugarParametros>{

        var listpar= mutableListOf<LugarParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idParametro\", \"idValor\", valor\n" +
                        "\tFROM public.\"ValoresParametro\" where valor='$lugar';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = LugarParametros(rs?.getInt(1), rs?.getInt(2),false)

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }

    fun intoParalugar(lugar: MutableList<LugarParametros>,idLugar:Int) {


        val idlugar:Int =idLugar
        val idPara:Int =lugar[0].idParametro

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
               // var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"LugarTieneParametro\"(\n" +
                        "\t\"idLugar\", \"idParametro\")\n" +
                        "\tVALUES ($idlugar, $idPara);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }
    fun intoParaValordebillugar(lugar: MutableList<LugarParametros>,idLugar:Int) {

        val idPara:Int =lugar[0].idParametro
        val idParavalor:Int =lugar[0].idParametrovalor

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"LugarTieneValorParámetro\"(\n" +
                        "\t\"idLugar\", \"idParametro\", \"idValor\")\n" +
                        "\tVALUES ($idLugar, $idPara, $idParavalor);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun ObtenerFilasGrupos(): Int {
        var idnuevo:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.\"Grupos\";"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idnuevo=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idnuevo
    }



    fun buscartipoIdioma():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"ValoresParametro\" where \"idParametro\"=3;"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }
    fun buscartipoTamanio():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"ValoresParametro\" where \"idParametro\"=4;"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }


    fun intoGrupo(grupos: Grupos) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"Grupos\"(\n" +
                        "\t\"idGrupo\", \"idUsuario\", nombre, fecha, proposito, urlgrupo)\n" +
                        "\tVALUES (${grupos.idGrupo}, ${grupos.idUsuario}, '${grupos.nombre}', '${grupos.fecha}', '${grupos.proposito}', '${grupos.urlgrupo}');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun buscarDatosIdiomatipo(idioma:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idParametro\", \"idValor\", valor\n" +
                        "\tFROM public.\"ValoresParametro\" where valor='$idioma';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), rs?.getInt(2),rs?.getString(3))

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }

    fun intoParaGrupo(lugar: MutableList<GruposParametros>,idGrupo:Int) {


        val idGrupo:Int =idGrupo
        val idPara:Int =lugar[0].idParametro

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"GrupoTieneParametro\"(\n" +
                        "\t\"idGrupo\", \"idParametro\")\n" +
                        "\tVALUES ($idGrupo, $idPara);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun intoParaValordebilGrupo(lugar: MutableList<GruposParametros>,idGrupo:Int) {

        val idPara:Int =lugar[0].idParametro
        val idParavalor:Int =lugar[0].idParametrovalor

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"GrupoTieneValorParametro\"(\n" +
                        "\t\"idGrupo\", \"idParametro\", \"idValor\")\n" +
                        "\tVALUES ($idGrupo, $idPara, $idParavalor);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }


    fun buscarDatostamaniogrupo(tamanio:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idParametro\", \"idValor\", valor\n" +
                        "\tFROM public.\"valorEtiqueta\" where valor='$tamanio';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(8, rs?.getInt(2),rs?.getString(3))

                    listpar.add(para)
                    r=rs.next()
                }

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun ObtenermiembrosgruposGruposmaxpermitido(idGrupo: Int): Int {
        var idvalor:Int=0
        var valor=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                var stsql = "SELECT \"idValor\"\n" +
                        "\tFROM public.\"GrupoTieneValorParametro\" where \"idGrupo\"='$idGrupo' and \"idParametro\"='8';"
                var st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idvalor=rs?.getInt(1)

                stsql = "SELECT  valor\n" +
                        "\tFROM public.\"valorEntero\" where \"idParametro\"='8' and \"idValor\"='$idvalor';"

                 st = connection?.createStatement()
                 rs = st?.executeQuery(stsql)
                 r = rs!!.next()

                valor=rs?.getInt(1)

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return valor
    }



    fun Obtenermiembrosdeungrupo(idGrupo: Int): Int {
        var idnuevo:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
               // var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.usuarioformapartedelgrupo where \"idGrupo\"='$idGrupo';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idnuevo=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idnuevo
    }


    fun intousergroup(idusuario: Int,idGrupo:Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.usuarioformapartedelgrupo(\n" +
                        "\t\"idUsuario\", \"idGrupo\")\n" +
                        "\tVALUES ($idusuario, $idGrupo);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }


    fun eresmiembrosdeungrupo(idGrupo: Int,idusuario: Int): Boolean {
        var esmiembro=true
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.usuarioformapartedelgrupo where \"idUsuario\"='$idusuario' and \"idGrupo\"='$idGrupo' ;"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                if(0==rs?.getInt(1)){
                    esmiembro=false
                }


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return esmiembro
    }

/////////////////////
    fun selecmygrupos(idusuario: Int): MutableList<Grupos> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"Grupos\".\"idGrupo\", public.\"Grupos\".\"idUsuario\", nombre, fecha, proposito, urlgrupo\n" +
                        " FROM public.usuarioformapartedelgrupo JOIN public.\"Grupos\" ON usuarioformapartedelgrupo.\"idGrupo\" = public.\"Grupos\".\"idGrupo\" where public.usuarioformapartedelgrupo.\"idUsuario\"='$idusuario';\n"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= Grupos(rs?.getInt(1),rs?.getInt(2),rs?.getString(3),rs?.getString(4),rs?.getString(5),rs?.getString(6))


                    grupos.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        //println(grupos[0].nombregrupo)

        return grupos

    }


    fun selecmygruposid(idusuario: Int): MutableList<GruposParametros> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT  public.\"valorEtiqueta\".\"idParametro\",public.\"valorEtiqueta\".\"idValor\",public.\"valorEtiqueta\".\"valor\" FROM \n" +
                        "public.usuarioformapartedelgrupo \n" +
                        "JOIN \n" +
                        "public.\"Grupos\" \n" +
                        "ON usuarioformapartedelgrupo.\"idGrupo\" = public.\"Grupos\".\"idGrupo\" \n" +
                        "\n" +
                        "JOIN \n" +
                        "public.\"GrupoTieneValorParametro\"\n" +
                        "ON public.\"GrupoTieneValorParametro\".\"idGrupo\" = public.\"Grupos\".\"idGrupo\" \n" +
                        "\n" +
                        "JOIN \n" +
                        "public.\"valorEtiqueta\"\n" +
                        "ON public.\"valorEtiqueta\".\"idParametro\"=public.\"GrupoTieneValorParametro\".\"idParametro\" AND\n" +
                        "public.\"valorEtiqueta\".\"idValor\"=public.\"GrupoTieneValorParametro\".\"idValor\"\n" +
                        "\n" +
                        "\n" +
                        "WHERE usuarioformapartedelgrupo.\"idUsuario\"='$idusuario';\n" +
                        ";\n"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= GruposParametros(rs?.getInt(1),rs?.getInt(2),rs?.getString(3))


                    misgruposparametrosidioma.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return misgruposparametrosidioma

    }


    fun selecmygruposidtam(idusuario: Int): MutableList<GruposParametros> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT  public.\"valorEntero\".\"idParametro\",public.\"valorEntero\".\"idValor\",public.\"valorEntero\".\"valor\" FROM \n" +
                        "public.usuarioformapartedelgrupo \n" +
                        "JOIN \n" +
                        "public.\"Grupos\" \n" +
                        "ON usuarioformapartedelgrupo.\"idGrupo\" = public.\"Grupos\".\"idGrupo\" \n" +
                        "\n" +
                        "JOIN \n" +
                        "public.\"GrupoTieneValorParametro\"\n" +
                        "ON public.\"GrupoTieneValorParametro\".\"idGrupo\" = public.\"Grupos\".\"idGrupo\" \n" +
                        "\n" +
                        "JOIN\n" +
                        "public.\"valorEntero\"\n" +
                        "ON public.\"valorEntero\".\"idParametro\"=public.\"GrupoTieneValorParametro\".\"idParametro\" AND\n" +
                        "public.\"valorEntero\".\"idValor\"=public.\"GrupoTieneValorParametro\".\"idValor\"\n" +
                        "\n" +
                        "\n" +
                        "WHERE usuarioformapartedelgrupo.\"idUsuario\"='$idusuario';\n" +
                        ";"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= GruposParametros(rs?.getInt(1),rs?.getInt(3),"")


                    misgruposparametrostam.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return misgruposparametrostam

    }



    fun selecmypropietario(idusuario: Int): MutableList<GruposParametros> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT  public.\"Usuario\".\"Nombre\" FROM \n" +
                        "public.usuarioformapartedelgrupo \n" +
                        "JOIN \n" +
                        "public.\"Grupos\" \n" +
                        "ON usuarioformapartedelgrupo.\"idGrupo\" = public.\"Grupos\".\"idGrupo\" \n" +
                        "\n" +
                        "JOIN \n" +
                        "public.\"Usuario\"\n" +
                        "ON public.\"Usuario\".\"idUsuario\" = public.\"Grupos\".\"idUsuario\" \n" +
                        "\n" +
                        "WHERE usuarioformapartedelgrupo.\"idUsuario\"='$idusuario';\n" +
                        ";"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= GruposParametros(0,0,rs?.getString(1))


                    misgruposparametrosprop.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return misgruposparametrosprop

    }


    ///////esto bsuca los grupos que creó el usuario
    fun buscarGruposdelusuarioPropietario(idusuario: Int):MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT nombre FROM public.\"Grupos\" where \"idUsuario\"='$idusuario';"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }

    ///para el spinner del tamaño del equipo
    fun buscartipoTamanioequipo():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"ValoresParametro\" where \"idParametro\"=0;"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }


    fun buscartipolugaresdisponibles():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT nombre\n" +
                        "\tFROM public.\"Lugar\"\n" +
                        "\tjoin\n" +
                        "\t public.\"LugarTieneValorParámetro\"\n" +
                        "\ton public.\"LugarTieneValorParámetro\".\"idLugar\"=public.\"Lugar\".\"idLugar\"\n" +
                        "\twhere \"idValor\"='0' and public.\"LugarTieneValorParámetro\".\"idParametro\"='6';"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }

    fun buscartipoduracion():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"valorEtiqueta\" where \"idParametro\"='5';"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }

    fun buscartiporelacion():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"valorEtiqueta\" where \"idParametro\"='2';"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }


    fun buscarparámetro():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT nombre\n" +
                        "\tFROM public.\"Parametro\";"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }



    fun buscacompetencias():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT  nombre\n" +
                        "\tFROM public.\"Competencias\";"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }

                // println(r)
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }

    fun buscatiposactividad():MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT nombre\n" +
                        "\tFROM public.\"TipoActividad\";"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }


    fun ObtenerFilasActividades(): Int {
        var idActividad:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.\"Actividad\";"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idActividad=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idActividad
    }



    fun buscarDatosdelgrupo(grupoSeleccionado:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idGrupo\"\n" +
                        "\tFROM public.\"Grupos\" where nombre='$grupoSeleccionado';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), 0,"")

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun intoActividad(actividad: Actividad) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)


                val stsql = "INSERT INTO public.\"Actividad\"(\n" +
                        "\t\"idActividad\", \"idUsuario\", \"idGrupo\", \"Descripcion\", \"nombre\", \"Fecha\")\n" +
                        "\tVALUES ('${actividad.idActividad}', '${actividad.idUsuario}', '${actividad.idGrupo}', '${actividad.descripcion}', '${actividad.nombre}', '${actividad.Fecha}');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }



    fun buscarDatosdellugar(lugarSeleccionado:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idLugar\"\n" +
                        "\tFROM public.\"Lugar\" where nombre='$lugarSeleccionado';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), 0,"")

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun intoSeRealiza(idLugar: Int ,idActividad: Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)


                val stsql = "INSERT INTO public.\"seRealiza\"(\n" +
                        "\t\"idLugar\", \"idActividad\")\n" +
                        "\tVALUES ('$idLugar', '$idActividad');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }



    fun actualizarlugar(idLugar: Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)


                val stsql = "UPDATE public.\"LugarTieneValorParámetro\"\n" +
                        "\tSET  \"idValor\"=1\n" +
                        "\tWHERE \"idLugar\"=$idLugar and \"idParametro\"=6;"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }



    fun ObtenerFilasConfiguraciones(): Int {
        var idActividad:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.\"Configuracion\";"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idActividad=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idActividad
    }


    fun intoconfiguraci(idconfiguran: Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)


                val stsql = "INSERT INTO public.\"Configuracion\"(\n" +
                        "\t\"idConfiguracion\")\n" +
                        "\tVALUES ('$idconfiguran');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }



    fun intoActividadTieneConfi(idactividad: Int ,idconfiguran: Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)


                val stsql = "INSERT INTO public.\"actividadTieneConfiguración\"(\n" +
                        "\t\"idActividad\", \"idConfiguracion\")\n" +
                        "\tVALUES ('$idactividad', '$idconfiguran');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }



    fun buscarDatostamanioequipo(tamanioequipo:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idParametro\", \"idValor\", valor\n" +
                        "\tFROM public.\"valorEtiqueta\" where valor='$tamanioequipo';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(7, rs?.getInt(2),rs?.getString(3))

                    listpar.add(para)
                    r=rs.next()
                }

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun intoParaConfi(lugar: MutableList<GruposParametros>,idConfi:Int) {

        val idPara:Int =lugar[0].idParametro

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.configuraciontieneparametro(\n" +
                        "\t\"idConfiguracion\", \"idParametro\")\n" +
                        "\tVALUES ('$idConfi', '$idPara');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun intoParaValorConfi(lugar: MutableList<GruposParametros>,idConfi:Int) {

        val idPara:Int =lugar[0].idParametro
        val idParavalor:Int =lugar[0].idParametrovalor

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.\"ConfiguracionTieneValorParametro\"(\n" +
                        "\t\"idConfiguracion\", \"idParametro\", \"idValor\")\n" +
                        "\tVALUES ('$idConfi', '$idPara', '$idParavalor');"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }


    fun buscarDatosduracionactividad(duracionSeleccionado:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idParametro\", \"idValor\", valor\n" +
                        "\tFROM public.\"valorEtiqueta\" where valor='$duracionSeleccionado';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), rs?.getInt(2),"")

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }

    fun buscarDatosrelacion(tiporelacionSeleccionado:String): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idParametro\", \"idValor\", valor\n" +
                        "\tFROM public.\"valorEtiqueta\" where valor='$tiporelacionSeleccionado';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), rs?.getInt(2),"")

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }

    fun buscarCompetencias(competencias:MutableList<String>): MutableList<Int>{

        var listpar= mutableListOf<Int>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")

                for(competencia in competencias) {

                    //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                    val stsql = "SELECT \"idCompetencias\", nombre\n" +
                            "\tFROM public.\"Competencias\" where nombre='$competencia';"
                    val st = connection?.createStatement()
                    val rs = st?.executeQuery(stsql)
                    var r = rs!!.next()
                    //aqui recogeremos los datos y devolveremos los grupos

                        var para =(rs?.getInt(1))
                        listpar.add(para)

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }

    fun intoCompetencia(competencias: MutableList<Int>,idConfi:Int) {


        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)

                for(competencia in competencias) {

                    val stsql = "INSERT INTO public.\"ConfiguracionTieneCompetencias\"(\n" +
                            "\t\"idCompetencias\", \"idConfiguracion\")\n" +
                            "\tVALUES ('$competencia', '$idConfi');"
                    val st = connection?.createStatement()
                    st?.executeUpdate(stsql)

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }

    fun buscarTipoActividad(tipoActividad:MutableList<String>): MutableList<Int>{

        var listpar= mutableListOf<Int>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")

                for(competencia in tipoActividad) {

                    //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                    val stsql = "SELECT \"idTipoActividad\", nombre\n" +
                            "\tFROM public.\"TipoActividad\" where nombre='$competencia';"
                    val st = connection?.createStatement()
                    val rs = st?.executeQuery(stsql)
                    var r = rs!!.next()
                    //aqui recogeremos los datos y devolveremos los grupos

                    var para =(rs?.getInt(1))
                    listpar.add(para)

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun intotipoActividad(tipoActividad: MutableList<Int>,idConfi:Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)

                for(tipo in tipoActividad) {

                    val stsql = "INSERT INTO public.\"ConfiguracionTieneTipoActividad\"(\n" +
                            "\t\"idConfiguracion\", \"idTipoActividad\")\n" +
                            "\tVALUES ('$idConfi', '$tipo');"
                    val st = connection?.createStatement()
                    st?.executeUpdate(stsql)

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }



    ////////terminar mas adelante

    fun buscarparametrosfleicxle(parametros: String):MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT valor\n" +
                        "\tFROM public.\"ValoresParametro\" where \"idParametro\"=4;"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }


    fun selectActivity(): MutableList<Actividad> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"idActividad\", \"idUsuario\", \"idGrupo\", \"Descripcion\", nombre, \"Fecha\"\n" +
                        "\tFROM public.\"Actividad\";"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var actividad= Actividad(rs?.getInt(1),rs?.getInt(2),rs?.getInt(3),rs?.getString(4),rs?.getString(5),rs?.getString(6))

                    actividades.add(actividad)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return actividades

    }

    fun selectActivityconfi(cadena:String?): MutableList<Actividad> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)

                val st = connection?.createStatement()
                var rs = st?.executeQuery(cadena)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var actividad= Actividad(rs?.getInt(1),rs?.getInt(2),rs?.getInt(3),rs?.getString(4),rs?.getString(5),rs?.getString(6))

                    actividades.add(actividad)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return actividades

    }



    fun buscarDatosactividades(): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"ConfiguracionTieneTipoActividad\".\"idConfiguracion\",\n" +
                        "\t   string_agg(public.\"TipoActividad\".\"nombre\",',')\n" +
                        "\t   \n" +
                        "    FROM public.\"Actividad\"\n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "    join\n" +
                        "    public.\"Configuracion\"\n" +
                        "    ON\n" +
                        "    public.\"actividadTieneConfiguración\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"ConfiguracionTieneTipoActividad\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneTipoActividad\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"TipoActividad\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneTipoActividad\".\"idTipoActividad\"=public.\"TipoActividad\".\"idTipoActividad\"\n" +
                        "\tgroup by public.\"ConfiguracionTieneTipoActividad\".\"idConfiguracion\";\n" +
                        "\t\n"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), 0,rs?.getString(2))

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun buscardueños(): MutableList<String> {



        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT \"Nombre\"\n" +
                        "\tFROM public.\"Actividad\"\n" +
                        "\tjoin \n" +
                        "\tpublic.\"Usuario\"\n" +
                        "\ton\n" +
                        "\tpublic.\"Actividad\".\"idUsuario\"=public.\"Usuario\".\"idUsuario\"\n" +
                        "\t\n" +
                        "\t;"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var actividad= rs?.getString(1)

                    cadena1.add(actividad)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return cadena1

    }


    fun ObtenermiembrosactividadGruposmaxpermitido(idActividad: Int): Int {
        var idvalor=0
        var valor=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                var stsql = "SELECT  \"idValor\"\n" +
                        "\tFROM public.\"ConfiguracionTieneValorParametro\" where \"idConfiguracion\"='$idActividad' and \"idParametro\"='7';"
                var st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idvalor=rs?.getInt(1)


                stsql = "SELECT  valor\n" +
                        "\tFROM public.\"valorEntero\" where \"idParametro\"='7' and \"idValor\"='$idvalor';"

                st = connection?.createStatement()
                rs = st?.executeQuery(stsql)
                r = rs!!.next()

                valor=rs?.getInt(1)



            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return valor
    }

    fun Obtenermiembrosdeunaactividad(idActividad: Int): Int {
        var idnuevo:Int=0
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                // var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.usuarioformaparteactividad where \"idActividad\"='$idActividad';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                idnuevo=rs?.getInt(1)


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return idnuevo
    }

    fun eresmiembrosdeunactividad(idactividad: Int,idusuario: Int): Boolean {
        var esmiembro=true
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.usuarioformaparteactividad where \"idActividad\"='$idactividad' and \"idUsuario\"='$idusuario';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                if(0==rs?.getInt(1)){
                    esmiembro=false
                }


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return esmiembro
    }


    fun intouseractivity(idusuario: Int,idactividad:Int) {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "INSERT INTO public.usuarioformaparteactividad(\n" +
                        "\t\"idActividad\", \"idUsuario\")\n" +
                        "\tVALUES ($idactividad, $idusuario);"
                val st = connection?.createStatement()
                st?.executeUpdate(stsql)

                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
    }


    fun selectmyActivity(idusuario: Int): MutableList<Actividad> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"Actividad\".\"idActividad\", public.\"Actividad\".\"idUsuario\", \"idGrupo\", \"Descripcion\", nombre, \"Fecha\"\n" +
                        "\tFROM public.\"Actividad\"\n" +
                        "\tjoin\n" +
                        "\tpublic.usuarioformaparteactividad\n" +
                        "\tON \n" +
                        "\tpublic.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "\twhere\n" +
                        "\tpublic.usuarioformaparteactividad.\"idUsuario\"='$idusuario'\n" +
                        "\t;;"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var actividad= Actividad(rs?.getInt(1),rs?.getInt(2),rs?.getInt(3),rs?.getString(4),rs?.getString(5),rs?.getString(6))

                    actividades1.add(actividad)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return actividades1

    }


    fun buscarDatosmisactividades(idusuario: Int): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"ConfiguracionTieneTipoActividad\".\"idConfiguracion\",\n" +
                        "       string_agg(public.\"TipoActividad\".\"nombre\",',')\n" +
                        "    \n" +
                        "\tFROM public.usuarioformaparteactividad\n" +
                        "\tjoin\n" +
                        "     public.\"Actividad\"\n" +
                        "\t on public.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "\t \n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "    join\n" +
                        "    public.\"Configuracion\"\n" +
                        "    ON\n" +
                        "    public.\"actividadTieneConfiguración\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"ConfiguracionTieneTipoActividad\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneTipoActividad\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"TipoActividad\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneTipoActividad\".\"idTipoActividad\"=public.\"TipoActividad\".\"idTipoActividad\"\n" +
                        "   \tWHERE public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario'\n" +
                        "   group by public.\"ConfiguracionTieneTipoActividad\".\"idConfiguracion\"\n" +
                        "\t\n" +
                        "\t;"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), 0,rs?.getString(2))

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }


    fun buscardemisactividadesdueños(idusuario: Int): MutableList<String> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT  public.\"Usuario\".\"Nombre\"\n" +
                        "\tFROM public.usuarioformaparteactividad\n" +
                        "\tjoin \n" +
                        "    public.\"Actividad\"\n" +
                        "    on \n" +
                        "\tpublic.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "\tjoin \n" +
                        "\tpublic.\"Usuario\"\n" +
                        "\ton \n" +
                        "\tpublic.\"Actividad\".\"idUsuario\"=public.\"Usuario\".\"idUsuario\"\n" +
                        "\twhere public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario';"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var actividad= rs?.getString(1)

                    cadena2.add(actividad)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return cadena2

    }

    fun nombredemisactividades(idusuario: Int): MutableList<String> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT  public.\"Grupos\".\"nombre\"\n" +
                        "\tFROM public.usuarioformaparteactividad\n" +
                        "\tjoin \n" +
                        "    public.\"Actividad\"\n" +
                        "    on \n" +
                        "\tpublic.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "\tjoin \n" +
                        "\tpublic.\"Grupos\"\n" +
                        "\ton \n" +
                        "\tpublic.\"Actividad\".\"idGrupo\"=public.\"Grupos\".\"idGrupo\"\n" +
                        "\twhere public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario';"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var actividad= rs?.getString(1)

                    cadena.add(actividad)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return cadena

    }


    fun buscarcompetenciasmisactividades(idusuario: Int): MutableList<GruposParametros>{

        var listpar= mutableListOf<GruposParametros>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"ConfiguracionTieneCompetencias\".\"idConfiguracion\",\n" +
                        "       string_agg(public.\"Competencias\".\"nombre\",',')\n" +
                        "    \n" +
                        "    FROM public.usuarioformaparteactividad\n" +
                        "    join\n" +
                        "     public.\"Actividad\"\n" +
                        "     on public.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "    \n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "    join\n" +
                        "    public.\"Configuracion\"\n" +
                        "    ON\n" +
                        "    public.\"actividadTieneConfiguración\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"ConfiguracionTieneCompetencias\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneCompetencias\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"Competencias\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneCompetencias\".\"idCompetencias\"=public.\"Competencias\".\"idCompetencias\"\n" +
                        "       WHERE public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario'\n" +
                        "   group by public.\"ConfiguracionTieneCompetencias\".\"idConfiguracion\"\n" +
                        "    \n" +
                        "    ;"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r) {

                    var para = GruposParametros(rs?.getInt(1), 0,rs?.getString(2))

                    listpar.add(para)
                    r=rs.next()
                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return listpar


    }



    fun selecmyactividadidtam(idusuario: Int): MutableList<GruposParametros> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"valorEntero\".\"idParametro\",public.\"valorEntero\".\"idValor\",public.\"valorEntero\".valor\n" +
                        "    \n" +
                        "    FROM public.usuarioformaparteactividad\n" +
                        "    join\n" +
                        "     public.\"Actividad\"\n" +
                        "     on public.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "    \n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "    join\n" +
                        "    public.\"Configuracion\"\n" +
                        "    ON\n" +
                        "    public.\"actividadTieneConfiguración\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"ConfiguracionTieneValorParametro\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneValorParametro\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"valorEntero\"\n" +
                        "    on\n" +
                        "    public.\"valorEntero\".\"idParametro\"=public.\"ConfiguracionTieneValorParametro\".\"idParametro\" and \n" +
                        "\t\tpublic.\"valorEntero\".\"idValor\"=public.\"ConfiguracionTieneValorParametro\".\"idValor\"\n" +
                        "       WHERE public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario'\n" +
                        "    \n" +
                        "    ;"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= GruposParametros(rs?.getInt(1),rs?.getInt(3),"")


                    misact1.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return misact1

    }


    fun myactividaduracion(idusuario: Int): MutableList<GruposParametros> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"valorEtiqueta\".\"idParametro\",public.\"valorEtiqueta\".\"idValor\",public.\"valorEtiqueta\".valor\n" +
                        "    \n" +
                        "    FROM public.usuarioformaparteactividad\n" +
                        "    join\n" +
                        "     public.\"Actividad\"\n" +
                        "     on public.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "    \n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "    join\n" +
                        "    public.\"Configuracion\"\n" +
                        "    ON\n" +
                        "    public.\"actividadTieneConfiguración\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"ConfiguracionTieneValorParametro\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneValorParametro\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"valorEtiqueta\"\n" +
                        "    on\n" +
                        "    public.\"valorEtiqueta\".\"idParametro\"=public.\"ConfiguracionTieneValorParametro\".\"idParametro\" and\n" +
                        "\t\n" +
                        "\tpublic.\"valorEtiqueta\".\"idValor\"=public.\"ConfiguracionTieneValorParametro\".\"idValor\"\n" +
                        "\t\t\n" +
                        "       WHERE public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario' and public.\"valorEtiqueta\".\"idParametro\"='5'\n" +
                        "    \n" +
                        "    ;"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= GruposParametros(rs?.getInt(1),rs?.getInt(2),rs?.getString(3))


                    misact4.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return misact4

    }

    fun myactividarelac(idusuario: Int): MutableList<GruposParametros> {

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"valorEtiqueta\".\"idParametro\",public.\"valorEtiqueta\".\"idValor\",public.\"valorEtiqueta\".valor\n" +
                        "    \n" +
                        "    FROM public.usuarioformaparteactividad\n" +
                        "    join\n" +
                        "     public.\"Actividad\"\n" +
                        "     on public.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "    \n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "    join\n" +
                        "    public.\"Configuracion\"\n" +
                        "    ON\n" +
                        "    public.\"actividadTieneConfiguración\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"ConfiguracionTieneValorParametro\"\n" +
                        "    on\n" +
                        "    public.\"ConfiguracionTieneValorParametro\".\"idConfiguracion\"=public.\"Configuracion\".\"idConfiguracion\"\n" +
                        "    join\n" +
                        "    public.\"valorEtiqueta\"\n" +
                        "    on\n" +
                        "    public.\"valorEtiqueta\".\"idParametro\"=public.\"ConfiguracionTieneValorParametro\".\"idParametro\" and\n" +
                        "\t\n" +
                        "\tpublic.\"valorEtiqueta\".\"idValor\"=public.\"ConfiguracionTieneValorParametro\".\"idValor\"\n" +
                        "\t\t\n" +
                        "       WHERE public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario' and public.\"valorEtiqueta\".\"idParametro\"='2'\n" +
                        "    \n" +
                        "    ;"
                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    var grupo= GruposParametros(rs?.getInt(1),rs?.getInt(2),rs?.getString(3))


                    misact3.add(grupo)

                    r=rs.next()

                }
                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return misact3

    }


    fun buscaractividadlugar(idusuario: Int):MutableList<String> {

        val Totalpar = mutableListOf<String>()

        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT public.\"Lugar\".\"localización\"\n" +
                        "\n" +
                        "    FROM public.usuarioformaparteactividad\n" +
                        "    join\n" +
                        "     public.\"Actividad\"\n" +
                        "     on public.usuarioformaparteactividad.\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "    \n" +
                        "    join\n" +
                        "    public.\"actividadTieneConfiguración\"\n" +
                        "    ON\n" +
                        "    public.\"Actividad\".\"idActividad\"=public.\"actividadTieneConfiguración\".\"idActividad\"\n" +
                        "   \n" +
                        "   join \n" +
                        "   public.\"seRealiza\"\n" +
                        "   \ton public.\"seRealiza\".\"idActividad\"=public.\"Actividad\".\"idActividad\"\n" +
                        "   \n" +
                        "   join \n" +
                        "\tpublic.\"Lugar\"\n" +
                        "\ton public.\"Lugar\".\"idLugar\"=public.\"seRealiza\".\"idLugar\"\n" +
                        "       WHERE public.\"usuarioformaparteactividad\".\"idUsuario\"='$idusuario' \n" +
                        "    \n" +
                        "    ;"

                val st = connection?.createStatement()
                var rs = st?.executeQuery(stsql)
                var r = rs!!.next()


                while (r){
                    Totalpar.add(rs?.getString(1))

                    r=rs.next()

                }


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return Totalpar
    }



    fun eresmiembrodeunaactividad(idusuario: Int): Boolean {
        var esmiembro=true
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                //var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "SELECT count(*)\n" +
                        "\tFROM public.\"Grupos\" where \"idUsuario\"='$idusuario';"
                val st = connection?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()

                if(0==rs?.getInt(1)){
                    esmiembro=false
                }


                //connection1?.close()

            } catch (e: Exception) {
                status = false
                print(e.message)
                e.printStackTrace()
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return esmiembro
    }


}