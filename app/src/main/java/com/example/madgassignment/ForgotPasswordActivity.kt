package com.example.madgassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_page)

        val usernameEditText: EditText = findViewById(R.id.PTForgotPasswordUsername)
        val phoneEditText: EditText = findViewById(R.id.TPForgotPasswordPhoneNumber)
        val sendOTPButton: Button = findViewById(R.id.BtnForgotPasswordSendOTP)
        val cancelButton: Button = findViewById(R.id.BtnForgotPasswordCancel)

        sendOTPButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (username.isNotEmpty() && phone.isNotEmpty()) {
                // Simulate sending OTP
                sendOTP(phone)
            } else {
                Toast.makeText(this, "Please enter both username and phone number", Toast.LENGTH_SHORT).show()
            }

            // Pass to ResetPasswordActivity
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            // Back to the login page
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun sendOTP(phone: String) {
        // Simulate OTP sending
        Toast.makeText(this, "OTP sent to $phone", Toast.LENGTH_SHORT).show()
        // Navigate to request TAC page
        startActivity(Intent(this, RequestTACActivity::class.java))
        finish()
    }
}
