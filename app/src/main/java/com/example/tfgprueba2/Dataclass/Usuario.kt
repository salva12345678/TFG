package com.example.tfgprueba2.Dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Usuario (
    var idusuario:Int,
    var nombre: String,
    var foto:String,
    var biografia:String,
    var fecha:String,
    var direccion:String,
    var contraseña:String):Parcelable
