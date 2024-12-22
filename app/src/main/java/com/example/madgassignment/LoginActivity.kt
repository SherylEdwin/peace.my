package com.example.madgassignment

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var cancelButton: Button
    private lateinit var visibilityButton: ImageButton
    private lateinit var signUpButton: Button
    private lateinit var forgotPasswordButton: Button
    private var isPasswordVisible = false
    private lateinit var userDao: UserDao
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        // Room Database and DAO
        appDatabase = AppDatabase.getDatabase(applicationContext)
        userDao = appDatabase.userDao()

        // Find the views
        usernameEditText = findViewById(R.id.PTLoginUsername)
        passwordEditText = findViewById(R.id.PTLoginPassword)
        loginButton = findViewById(R.id.BtnLoginLogin)
        cancelButton = findViewById(R.id.BtnLoginCancel)
        visibilityButton = findViewById(R.id.IBVisibilityOff)
        signUpButton = findViewById(R.id.BtnLoginSignUp)
        forgotPasswordButton = findViewById(R.id.BtnLoginForgotPassword)

        // Show/Hide Password
        visibilityButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                visibilityButton.setImageResource(R.drawable.visibility_on)
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                visibilityButton.setImageResource(R.drawable.visibility_off)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        // Login button
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Check Room Database
            authenticateUser(username, password)
        }

        // Cancel (Exit) button
        cancelButton.setOnClickListener {
            finishAffinity()  // Close all activities and exit the app
        }

        // Redirect to sign up page
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Redirect to forgot password page
        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    // Authenticate
    private fun authenticateUser(username: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            // Query
            val user = userDao.authenticateUser(username, password)

            withContext(Dispatchers.Main) {
                if (user != null) {
                    // Login, move to home page
                    val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                    startActivity(intent)
                    finish() // Close the login page
                } else {
                    // Error, invalid username or password
                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    usernameEditText.text.clear()
                    passwordEditText.text.clear()
                }
            }
        }
    }
}
