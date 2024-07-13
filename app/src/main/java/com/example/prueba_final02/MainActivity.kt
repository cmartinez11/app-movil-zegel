package com.example.prueba_final02

//importa la clase Bundle para manejar datos pasados entre actividades
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
//importa la clase LayoutInflater para inflar layouts
import android.view.LayoutInflater
//Importa la clase EditText para trabajar con campos de texto
import android.widget.EditText
import android.widget.Toast
//Importa la función para habilitar el modo 'edge-to-edge' (opcional)
import androidx.activity.enableEdgeToEdge
//Importa la clase AlertDialog para crear cuadros de diálogo
import androidx.appcompat.app.AlertDialog
// Importa la clase base AppCompatActivity para actividades de la interfaz de usuario
import androidx.appcompat.app.AppCompatActivity
// Importa utilidades para trabajar con vistas
import androidx.core.view.ViewCompat
// Importa utilidades para manejar insets de ventana (opcional)
import androidx.core.view.WindowInsetsCompat
// Importa la clase de enlace de vistas para la actividad principal
import com.example.prueba_final02.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    //Variable para almacenar el enlace de vistas de la actividad principal
    private lateinit var binding: ActivityMainBinding

    private lateinit var fireBaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama al método onCreate de la clase padre
        super.onCreate(savedInstanceState)

        // Infla el layout de la actividad principal y lo asigna a la variable binding
        binding=ActivityMainBinding.inflate(layoutInflater)
        // Establece la vista raíz del layout como contenido de la actividad
        setContentView(binding.root)

        // Listener para el botón de login
        binding.btnLogin.setOnClickListener{
            // Al hacer clic en el botón, se llama al método showLogin()
            showLogin()
        }

        binding.btnRegistrar.setOnClickListener{
            mostrarRegistrar()
        }

        binding.tvOlvidoContrasena.setOnClickListener{
            showForgotPassword()
        }

        fireBaseAuth=FirebaseAuth.getInstance()

    }

    fun showForgotPassword(){
        // Crea un constructor de AlertDialog
        val dialog = AlertDialog.Builder(this)
        // Establece el título del cuadro de diálogo
        dialog.setTitle("Cambiar contraseña")
        // Establece el icono del cuadro de diálogo
        dialog.setIcon(R.drawable.logo)

        // Obtiene un objeto LayoutInflater
        val inflater = LayoutInflater.from(this)
        // Infla el layout del cuadro de diálogo de login
        val layout = inflater.inflate(R.layout.forgot_password_layout, null)

        // Obtiene la referencia al EditText del correo electrónico
        val et_email : EditText = layout.findViewById(R.id.et_correo_forgot_password)

        // Establece la vista del cuadro de diálogo con el layout inflado
        dialog.setView(layout)

        // Configura el botón positivo
        dialog.setPositiveButton("Aceptar"){a,b ->
            // Obtiene el texto ingresado en el correo electrónico
            val email = et_email.text.toString()
            forgotPassword(email)

        }

        // No hace nada en particular, solo cierra el cuadro de diálogo
        dialog.setNegativeButton("Cancelar"){a,b ->
        }

        // Muestra el cuadro de diálogo de login
        dialog.show()
    }

    fun forgotPassword(eml : String){

        val alert = ProgressDialog(this)
        alert.setTitle("QueTalTiendas")
        alert.setIcon(R.drawable.logo)
        alert.setMessage("Enviando...")
        alert.show()

        fireBaseAuth.sendPasswordResetEmail(eml).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                alert.dismiss()
                Toast.makeText(this, "Revise su bandeja de entrada!", Toast.LENGTH_SHORT).show()
            } else {
                alert.dismiss()
                showMessage(task.exception?.message.toString())
            }
        }
    }





    fun mostrarRegistrar(){
        //funcion para redirigir a la pagina de registrar
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

    // Método para mostrar el cuadro de diálogo de login
    fun showLogin(){
        // Crea un constructor de AlertDialog
        val dialog = AlertDialog.Builder(this)
        // Establece el título del cuadro de diálogo
        dialog.setTitle("Login")
        // Establece el icono del cuadro de diálogo
        dialog.setIcon(R.drawable.logo)

        // Obtiene un objeto LayoutInflater
        val inflater = LayoutInflater.from(this)
        // Infla el layout del cuadro de diálogo de login
        val layout = inflater.inflate(R.layout.login, null)

        // Obtiene la referencia al EditText del correo electrónico
        val et_email : EditText = layout.findViewById(R.id.et_correo)
        // Obtiene la referencia al EditText de la contraseña
        val et_password : EditText = layout.findViewById(R.id.et_password)

        // Establece la vista del cuadro de diálogo con el layout inflado
        dialog.setView(layout)

        // Configura el botón positivo
        dialog.setPositiveButton("Aceptar"){a,b ->
            // Obtiene el texto ingresado en el correo electrónico
            val email = et_email.text.toString()
            // Obtiene el texto ingresado en la contraseña
            val psw = et_password.text.toString()

            // Llama al método loginFireBase() con los valores del email y contraseña
            loginFireBase(email, psw)
        }

        // No hace nada en particular, solo cierra el cuadro de diálogo
        dialog.setNegativeButton("Cancelar"){a,b ->
        }

        // Muestra el cuadro de diálogo de login
        dialog.show()
    }

    fun loginFireBase(email : String, psw : String){
        val alert = ProgressDialog(this)
        alert.setTitle("QueTalTiendas")
        alert.setIcon(R.drawable.logo)
        alert.setMessage("Enviando...")
        alert.show()


        fireBaseAuth.signInWithEmailAndPassword(email, psw).addOnCompleteListener{task->
            if(task.isSuccessful){
                alert.dismiss()
                mostrarActividadPrincipal()
            }else{
                alert.dismiss()
                showMessage(task.exception?.message.toString())
            }
        }
    }

    fun mostrarActividadPrincipal(){
        //creamos esta funcion para cuando nos logiemos nos redirija a la pantalla principal
        val intent = Intent(this,activity_bienvenida::class.java)
        //intent.putExtra("id","d4yr@2024")
        startActivity(intent)
    }


    fun showMessage(txt : String){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Mensaje")
        dialog.setMessage(txt)
        dialog.setIcon(R.drawable.logo)
        dialog.setPositiveButton("OK"){a, b ->

        }
        dialog.show()
    }
}