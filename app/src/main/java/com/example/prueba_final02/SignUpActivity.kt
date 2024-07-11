package com.example.prueba_final02

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prueba_final02.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var fireBaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fireBaseAuth=FirebaseAuth.getInstance()

        binding.btnRegistrar.setOnClickListener{
            registerFireBase()
        }

    }


    fun registerFireBase(){
        var nombre = binding.etNombre.text.toString()
        var correo = binding.etCorreo.text.toString()
        var password1 = binding.etPassword1.text.toString()
        var password2 = binding.etPassword2.text.toString()


        if (password1 != password2){
            showMessage("Las contraseñas no coinciden")
            return
        }



        val alert = ProgressDialog(this)
        alert.setTitle("QueTalTiendas")
        alert.setIcon(R.drawable.logo)
        alert.setMessage("Enviando...")
        alert.show()


        fireBaseAuth.createUserWithEmailAndPassword(correo,password1).addOnCompleteListener{task ->
            if(task.isSuccessful){
                //registro del usuario
                alert.dismiss()
                Toast.makeText(this, "Te registraste exitosamente!", Toast.LENGTH_SHORT).show()
                irActividadInicial()
            }else{
                showMessage(task.exception?.message.toString())
            }
        }
    }

    fun irActividadInicial(){
        //estamos creando una funcion para cuando iniciemos sesion nos devuelva a la actividad inicial, en este caso cuando nos registremos volver donde nos podemos logear
        finish()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }



    fun showMessage(txt : String){
        //funcion para mostrar mensajes
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Mensaje")
        dialog.setMessage(txt)
        dialog.setIcon(R.drawable.logo)
        dialog.setPositiveButton("OK"){a, b ->

        }
        dialog.show()

    }
}