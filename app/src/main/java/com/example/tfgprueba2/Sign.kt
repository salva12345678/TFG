package com.example.tfgprueba2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tfgprueba2.Dataclass.Usuario
import com.example.tfgprueba2.databinding.ActivitySignBinding
import java.math.BigInteger
import java.security.MessageDigest

class Sign : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)


        lateinit var gBinding: ActivitySignBinding

        gBinding =  ActivitySignBinding.inflate(layoutInflater)

        val botonregistrarse =findViewById(R.id.btn_registrase) as Button



        var idnuevo:Int=0
        val usuario=Usuario(idusuario=idnuevo, nombre="", contraseña="",foto="", biografia="", fecha="", direccion="")



        botonregistrarse.setOnClickListener {

            var db = conect()
            db?.Conect()
            idnuevo=db?.ObtenerFilas()
            with(usuario){
                idusuario=idnuevo+1
                nombre=(findViewById(R.id.login_name) as EditText).text.toString().trim()
                contraseña=md5Hash((findViewById(R.id.login_password) as EditText).text.toString().trim())

                foto=(findViewById(R.id.url_sign) as EditText).text.toString().trim()
                biografia=(findViewById(R.id.sign_biografia) as EditText).text.toString().trim()
                fecha=(findViewById(R.id.date_sign) as EditText).text.toString().trim()
                direccion=(findViewById(R.id.locateSign) as EditText).text.toString().trim()
            }


            if((findViewById(R.id.login_name) as EditText).text.toString().trim().isEmpty() || (findViewById(R.id.login_password) as EditText).text.toString().trim().isEmpty()
                ||(findViewById(R.id.url_sign) as EditText).text.toString().trim().isEmpty()||(findViewById(R.id.sign_biografia) as EditText).text.toString().trim().isEmpty()
                ||(findViewById(R.id.date_sign) as EditText).text.toString().trim().isEmpty()||(findViewById(R.id.locateSign) as EditText).text.toString().trim().isEmpty()){

                Toast.makeText(this, R.string.emply_login, Toast.LENGTH_SHORT).show()
            }
            else{


            db?.intoUsuario(usuario)

            Toast.makeText(this, R.string.person_add, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Login::class.java))
            }

        }



    }


    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

fun devolverid():Int{
    var idnuevo:Int=0

    var db = conect()
    db?.Conect()
    idnuevo=db?.ObtenerFilas()

    idnuevo=idnuevo+1
    return idnuevo

}

}