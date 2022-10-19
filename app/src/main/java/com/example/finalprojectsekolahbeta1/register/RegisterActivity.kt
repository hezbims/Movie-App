package com.example.finalprojectsekolahbeta1.register

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.databinding.ActivityRegisterBinding
import com.example.finalprojectsekolahbeta1.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)

        val loginText = getString(R.string.login_text)
        val mainSpannableText = SpannableString(
            getString(R.string.already_have_account_text , loginText)
        )

        val clickableSpan = object : ClickableSpan(){
            override fun onClick(view : View) {
                startActivity(
                    Intent(this@RegisterActivity , LoginActivity::class.java)
                )
                this@RegisterActivity.finish()
            }
        }

        mainSpannableText.setSpan(
            clickableSpan,
            mainSpannableText.length - loginText.length,
            mainSpannableText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.doesntHaveAccountText.text = mainSpannableText
        binding.doesntHaveAccountText.movementMethod = LinkMovementMethod()
        binding.submit.setOnClickListener {
            submitData(
                binding.emailValue.text.toString() ,
                binding.passwordValue.text.toString() ,
                binding.passwordConfirmValue.text.toString()
            )
        }

        setContentView(binding.root)
    }

    private fun submitData(email : String , password : String , cPassword : String){
        if (email.isEmpty()){
            Toast.makeText(
                this,
                getString(R.string.empty_email_text),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (password.isEmpty() || cPassword.isEmpty()){
            Toast.makeText(
                this,
                getString(R.string.empty_password_text),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (password == cPassword){
            Firebase.auth.createUserWithEmailAndPassword(
                email, password
            ).addOnCompleteListener {task ->
                if (task.isSuccessful){
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.create_account_success),
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(
                        Intent(this , LoginActivity::class.java)
                    )
                    finish()
                }
                else
                    Toast.makeText(
                        applicationContext ,
                        getString(R.string.create_account_failed , task.exception?.toString()),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
        else
            Toast.makeText(
                this,
                getString(R.string.password_not_same),
                Toast.LENGTH_SHORT
            ).show()
    }
}