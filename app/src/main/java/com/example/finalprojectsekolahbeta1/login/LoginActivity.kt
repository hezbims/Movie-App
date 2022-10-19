package com.example.finalprojectsekolahbeta1.login

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectsekolahbeta1.MainActivity
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.ActivityLoginBinding
import com.example.finalprojectsekolahbeta1.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var authenticator : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticator = Firebase.auth

        val registerText = getString(R.string.register_text)
        val mainSpannableText = SpannableString(
            getString(R.string.doesnt_have_account_text , registerText)
        )

        val clickableSpan = object : ClickableSpan(){
            override fun onClick(v : View) {
                startActivity(
                    Intent(this@LoginActivity , RegisterActivity::class.java)
                )
                this@LoginActivity.finish()
            }
        }

        mainSpannableText.setSpan(
            clickableSpan,
            mainSpannableText.length - registerText.length,
            mainSpannableText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.alternativeRegisterText.text = mainSpannableText
        binding.alternativeRegisterText.movementMethod = LinkMovementMethod()
        binding.submit.setOnClickListener {
            verifyLogin(binding.emailValue.text.toString() , binding.passwordValue.text.toString())
        }

        setContentView(binding.root)
    }

    private fun verifyLogin(email : String , password : String){
        if (email.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.empty_email_text),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (password.isEmpty()){
            Toast.makeText(
                this ,
                getString(R.string.empty_password_text),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        Firebase.auth.signInWithEmailAndPassword(email , password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    startActivity(
                        Intent(this , MainActivity::class.java)
                    )
                    finish()
                }
                else
                    Toast.makeText(
                        this ,
                        getString(R.string.login_failed , task.exception?.toString()) ,
                        Toast.LENGTH_SHORT
                    ).show()
            }
    }
}