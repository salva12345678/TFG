package com.example.tfgprueba2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tfgprueba2.Dataclass.Usuario
import com.example.tfgprueba2.databinding.ActivityLoginBinding
import java.math.BigInteger
import java.security.MessageDigest

class Login : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mBinding= ActivityLoginBinding.inflate(layoutInflater)

        val botonregistrarse =findViewById(R.id.btn_registrase) as Button
        val botoniniciar =findViewById(R.id.botonLogin) as Button

        var existe:Boolean

        //para registarse
        botonregistrarse.setOnClickListener {
            startActivity(Intent(this,Sign::class.java))
        }

        //iniciar sesion

        botoniniciar.setOnClickListener {
            var db = conect()
            db?.Conect()
            existe=db?.buscarUsuario((findViewById(R.id.login_name1) as EditText).text.toString().trim(),md5Hash((findViewById(R.id.login_pass1) as EditText).text.toString().trim()))


            if((findViewById(R.id.login_name1) as EditText).text.toString().trim().isEmpty() || (findViewById(R.id.login_pass1) as EditText).text.toString().trim().isEmpty() ){

                Toast.makeText(this, R.string.emply_login, Toast.LENGTH_SHORT).show()
            }

            if(existe){
                //enviamos la infprmacion del usuario
                Toast.makeText(this, R.string.vali_person, Toast.LENGTH_SHORT).show()
                var usuario:Usuario=db?.selectUsuario((findViewById(R.id.login_name1) as EditText).text.toString().trim(),md5Hash((findViewById(R.id.login_pass1) as EditText).text.toString().trim()))
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("idUsuario",usuario.idusuario)
                i.putExtra("Nombre",usuario.nombre)
                i.putExtra("contraseña",usuario.contraseña)
                i.putExtra("foto",usuario.foto)
                i.putExtra("biografia",usuario.biografia)
                i.putExtra("fecha",usuario.fecha)
                i.putExtra("direccion",usuario.direccion)
                startActivity(i)

            }
            else{
                Toast.makeText(this, R.string.failed_login, Toast.LENGTH_SHORT).show()
            }

        }


    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }


}