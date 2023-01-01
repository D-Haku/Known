package com.example.known

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.SimpleTimeZone

class Signup : AppCompatActivity() {
    private lateinit var edtemail: EditText
    private lateinit var edtname: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        edtname = findViewById(R.id.edt_name)
        edtemail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)
        btnSignup = findViewById(R.id.btn_signup)
        mAuth = FirebaseAuth.getInstance()

        btnSignup.setOnClickListener {
            val name = edtname.text.toString()
            val email = edtemail.text.toString()
            val password = edtpassword.text.toString()

            signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // signup(successful) -> home
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@Signup, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Signup, "Unable to sign up", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbref=FirebaseDatabase.getInstance().getReference()
        mDbref.child("user").child(uid).setValue(user(name,email,uid))
    }
}