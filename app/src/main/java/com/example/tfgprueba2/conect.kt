package com.example.tfgprueba2

import android.annotation.SuppressLint
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


    ///select from algo

   //lateinit var grupo:Grupos
   lateinit var hola:String
    var hola1:Int = 0
    //lateinit var grupos: MutableList<Grupos>
    val grupos = mutableListOf<Grupos>()


    fun Conect() {
        url = String.format(url, host, port, database)
        connect()
        //this.disconnect();

        println("connection status:$status")
    }

    @SuppressLint("newApi")
    fun connect() {
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                connection = DriverManager.getConnection(url, user, pass)
                status = true
                println("connected:$status")
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
                var connection1: Connection?  = DriverManager.getConnection(url, user, pass)
                val stsql = "select * from public.\"Grupos\";"
                val st = connection1?.createStatement()
                val rs = st?.executeQuery(stsql)
                var r = rs!!.next()
                //aqui recogeremos los datos y devolveremos los grupos
                while (r){

                    println(rs?.getString(1))
                    println(rs?.getString(2))
                    hola1=rs?.getInt(1)
                    hola=rs?.getString(2)
                    //println(hola)

                    var grupo=Grupos(hola1,hola,hola,hola1,hola,hola)

                    /*
                    grupo.descripcion=rs?.getString(1)
                    grupo.nombregrupo=rs?.getString(2)
                    grupo.url=rs?.getString(2)
                    grupo.idGrupos=rs?.getInt(1)
                    grupo.languaje=rs?.getString(1)
                    grupo.tammax=rs?.getInt(1)
                   */
                    //println(grupo.descripcion)

                    grupos.add(grupo)

                    r=rs.next()

                }
                connection1?.close()

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


    fun getExtraConnection(): Connection? {
        var c: Connection? = null
        try {
            Class.forName("org.postgresql.Driver")
            c = DriverManager.getConnection(url, user, pass)
            val stsql = "Select * from Grupos;"
            val st = c.createStatement()
            val rs = st.executeQuery(stsql)
            rs.next()
            println(rs.getString(1))
            c.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return c
    }

}