package com.example.madgassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_password_page)

        // Get username passed from ForgotPasswordActivity
        val username = intent.getStringExtra("username")

        val newPasswordEditText: EditText = findViewById(R.id.PTResetPasswordNewPassword)
        val confirmPasswordEditText: EditText = findViewById(R.id.PTResetPasswordConfirmPassword)
        val cancelButton: Button = findViewById(R.id.BtnResetPasswordCancel)
        val resetButton: Button = findViewById(R.id.BtnResetPasswordOkay)

        cancelButton.setOnClickListener {
            // Navigate back to the login page
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        resetButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword == confirmPassword) {
                if (username != null) {
                    resetPassword(username, newPassword)
                } else {
                    Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetPassword(username: String, newPassword: String) {
        // Ensure the password field is not empty
        if (newPassword.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                // Get the user by username
                val user = AppDatabase.getDatabase(applicationContext).userDao().getUserByUsername(username)

                if (user != null) {
                    // Update the user's password
                    AppDatabase.getDatabase(applicationContext).userDao().updateUserPassword(username, newPassword)

                    // Notify user of the successful password reset
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Password reset successful", Toast.LENGTH_SHORT).show()

                        // Navigate back to login page
                        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
                        finish()
                    }
                } else {
                    // User not found
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}
