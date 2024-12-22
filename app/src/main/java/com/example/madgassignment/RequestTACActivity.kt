package com.example.madgassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RequestTACActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_tac_page)

        val tacCodeEditText: EditText = findViewById(R.id.TNRequestTACCode)
        val verifyButton: Button = findViewById(R.id.BtnRequestTACVerify)
        val backButton: Button = findViewById(R.id.BtnRequestTACBack)
        val sendTACAgainButton: Button = findViewById(R.id.BtnRequestTACAgain)

        verifyButton.setOnClickListener {
            val tacCode = tacCodeEditText.text.toString()

            if (tacCode.isNotEmpty()) {
                verifyTAC(tacCode)
            } else {
                Toast.makeText(this, "Please enter the TAC", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }

        sendTACAgainButton.setOnClickListener {
            // Simulate sending the TAC again
            Toast.makeText(this, "TAC sent again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyTAC(tacCode: String) {
        // Simulate TAC verification
        if (tacCode == "1234") {  // Placeholder for actual TAC validation
            // Navigate to reset password page
            startActivity(Intent(this, ResetPasswordActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Invalid TAC", Toast.LENGTH_SHORT).show()
        }
    }
}
