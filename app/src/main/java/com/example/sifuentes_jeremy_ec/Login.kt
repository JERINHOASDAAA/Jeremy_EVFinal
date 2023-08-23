package com.example.sifuentes_jeremy_ec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

class Login : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id)) // Utiliza la cadena desde strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        usernameEditText = findViewById(R.id.tilEmail)
        passwordEditText = findViewById(R.id.tilPassword)
        loginButton = findViewById(R.id.btnLogin)

        val btnLoginWithGoogle = findViewById<Button>(R.id.btnLoginWithGoogle)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validación de los datos ingresados
            if (username == "Jero@idat.edu.pe" && password == "123456") {
                Toast.makeText(this, "Login Iniciado", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o Password Incorrectos", Toast.LENGTH_SHORT).show()
            }
        }


        btnLoginWithGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    // ...

    companion object {
        const val RC_SIGN_IN = 9001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val displayName = account.displayName
                    val email = account.email


                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("displayName", displayName)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                }
            } catch (e: ApiException) {

                Toast.makeText(this, "Error en el inicio de sesión de Google", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

