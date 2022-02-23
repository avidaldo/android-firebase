package com.avidaldo.firebase1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avidaldo.firebase1.databinding.ActivityAuthPasswordBinding
import com.google.firebase.auth.FirebaseAuth

// https://www.youtube.com/watch?v=KYPc7CAYJOw


const val TAG = ":::"

class AuthPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthPasswordBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegistrar.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()


            if (validCredentials(email, password)) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {  // Añadimos a la tarea un listener para cuando se realiza la acción
                        if (it.isSuccessful) correctRegistration() // comprobamos si se ha autenticado correctamente
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", it.exception)
                            registrationError()
                        }
                    }
            } else {
                notValidCredentials()
            }

        }




        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()


            if (validCredentials(email, password)) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) correctLogin()
                        else autenticationError()
                    }
            } else {
                notValidCredentials()
            }

        }

    }

    private fun registrationError() {
        Toast.makeText(this, "Error de registro", Toast.LENGTH_SHORT).show()
    }

    private fun correctRegistration() {
        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
    }

    private fun correctLogin() {
        Toast.makeText(this, "Usuario autenticado", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun notValidCredentials() {
        Toast.makeText(this, "Credenciales no válidas", Toast.LENGTH_SHORT).show()
    }

    private fun autenticationError() {
        Toast.makeText(this, "No se ha podido autenticar", Toast.LENGTH_SHORT).show()
    }

    private fun validCredentials(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }

}