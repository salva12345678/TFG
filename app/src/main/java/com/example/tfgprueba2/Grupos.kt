package com.example.tfgprueba2

 data class Grupos(var idGrupos:Int,
                  var languaje:String,
                  var nombregrupo: String,
                  var tammax:Int,
                  var descripcion: String,
                  var url:String){

    fun getId(): Int {

        return idGrupos

    }

    fun getlanguaje(): String {

        return languaje

    }

    fun getnombregrupo(): String {

        return nombregrupo

    }

    fun gettammax(): Int {

        return tammax

    }

    fun getdescripcion(): String {

        return descripcion

    }

    fun geturl(): String {

        return url

    }

}
