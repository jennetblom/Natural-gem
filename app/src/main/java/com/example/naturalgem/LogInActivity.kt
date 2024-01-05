package com.example.naturalgem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var emailView : EditText
    lateinit var passwordView : EditText
    lateinit var imageView : ImageView
    lateinit var welcomeTextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = Firebase.auth
        emailView = findViewById(R.id.emailEditText)
        passwordView = findViewById(R.id.passwordEditText)
        imageView = findViewById(R.id.imageView)
        welcomeTextView = findViewById(R.id.welcomeTextView)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val signInbutton = findViewById<Button>(R.id.signInButton)

        signUpButton.setOnClickListener {
            signUp()
        }
        signInbutton.setOnClickListener {
            signIn()
        }
        if(auth.currentUser!=null){
            gotToAddActivity()
        }
    }

    fun gotToAddActivity(){
        val intent = Intent(this,AddPlaceAndEditActivity::class.java)
        startActivity(intent)
    }
    fun signUp(){
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if(email.isEmpty()||password.isEmpty()){
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d("!!!","Great sucess")
                    Toast.makeText(this,"Du lyckades logga in!",Toast.LENGTH_SHORT).show()
                    gotToAddActivity()
                }
                else{
                    Log.d("!!!", "User not created ${task.exception}")
                    Toast.makeText(this,"Mailadressen behöver vara i rätt format och lösenordet behöver vara minst 6 tecken långt.",Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun signIn(){
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if(email.isEmpty()||password.isEmpty()){
            return
        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d("!!!","Great sucess")
                    Toast.makeText(this,"Du lyckades logga in!",Toast.LENGTH_SHORT).show()
                    gotToAddActivity()
                }
                else{
                    Log.d("!!!", "User not created ${task.exception}")
                    Toast.makeText(this,"Mailadressen behöver vara i rätt format och lösenordet behöver vara minst 6 tecken långt.",Toast.LENGTH_SHORT).show()
                }
            }
    }
}