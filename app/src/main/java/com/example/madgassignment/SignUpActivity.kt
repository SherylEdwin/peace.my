package com.example.madgassignment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showStepOne()
    }

    private fun showStepOne() {
        setContentView(R.layout.sign_up_page_1)

        val firstNameEditText: EditText = findViewById(R.id.PTSignUpFirstName)
        val lastNameEditText: EditText = findViewById(R.id.PTSignUpLastName)
        val usernameEditText: EditText = findViewById(R.id.PTSignUpUsername)
        val passwordEditText: EditText = findViewById(R.id.PTSignUpPassword)
        val confirmPasswordEditText: EditText = findViewById(R.id.PTSignUpConfirmPassword)
        val nextButton: Button = findViewById(R.id.BtnSignUpNext)
        val cancelButton: Button = findViewById(R.id.BtnSignUpCancel)

        // Cancel button
        cancelButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Next button
        nextButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Validate
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() &&
                password.isNotEmpty() && password == confirmPassword) {

                showStepTwo(firstName, lastName, username, password)
            } else {
                Toast.makeText(this, "Please fill all fields and ensure passwords match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showStepTwo(firstName: String, lastName: String, username: String, password: String) {
        setContentView(R.layout.sign_up_page_2)

        val addressEditText: EditText = findViewById(R.id.PASignUp2Address)
        val address2EditText: EditText = findViewById(R.id.PASignUp2Address2)
        val postcodeEditText: EditText = findViewById(R.id.TNSignUp2Postcode)
        val cityEditText: EditText = findViewById(R.id.PTSignUp2City)
        val stateSpinner: Spinner = findViewById(R.id.SSignUp2States)
        val dobTextView: TextView = findViewById(R.id.TDSignUp2DOB)
        val phoneEditText: EditText = findViewById(R.id.TPSignUp2PhoneNumber)
        val nextButton: Button = findViewById(R.id.BtnSignUp2Next)
        val cancelButton: Button = findViewById(R.id.BtnSignUp2Cancel)

        // State spinner
        val states = resources.getStringArray(R.array.states_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, states)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = adapter

        // DOB
        dobTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // day/month/year
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dobTextView.text = formattedDate
            }, year, month, day).show()
        }

        // Cancel button
        cancelButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Next button
        nextButton.setOnClickListener {
            val address = addressEditText.text.toString()
            val address2 = address2EditText.text.toString()
            val postcode = postcodeEditText.text.toString()
            val city = cityEditText.text.toString()
            val state = stateSpinner.selectedItem.toString()
            val dob = dobTextView.text.toString()
            val phone = phoneEditText.text.toString()

            if (address.isNotEmpty() && postcode.isNotEmpty() && city.isNotEmpty() &&
                state != "Select State" && dob.isNotEmpty() && phone.isNotEmpty()) {

                // Save user
                GlobalScope.launch(Dispatchers.IO) {
                    val user = User(
                        username = username,
                        password = password,
                        firstName = firstName,
                        lastName = lastName,
                        address = address,
                        address2 = address2,
                        postcode = postcode,
                        city = city,
                        state = state,
                        dob = dob,
                        phone = phone
                    )
                    AppDatabase.getDatabase(applicationContext).userDao().insertUser(user)
                }

                // Show success dialog
                runOnUiThread {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
