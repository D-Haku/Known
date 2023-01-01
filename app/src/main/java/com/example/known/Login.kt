package com.example.known

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtemail : EditText
    private lateinit var edtpassword : EditText
    private lateinit var btnLogin:  Button
    private lateinit var btnSignup : Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()
        edtemail= findViewById(R.id.edt_email)
        edtpassword= findViewById(R.id.edt_password)
        btnLogin= findViewById(R.id.btn_login)
        btnSignup= findViewById(R.id.btn_signup)

        mAuth=FirebaseAuth.getInstance()

        btnSignup.setOnClickListener {
            val intent= Intent(this,Signup::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtemail.text.toString()
            val password = edtpassword.text.toString()

            login(email,password)
        }
    }

    private fun login(email:String ,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                  val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    //unsuccessful login
                    Toast.makeText(this@Login,"Invalid username or password",Toast.LENGTH_SHORT).show()
                }
            }
    }
}