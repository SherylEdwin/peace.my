package com.example.madgassignment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private val userDao: UserDao by lazy {
        AppDatabase.getDatabase(applicationContext).userDao()
    }

    private lateinit var TVSettingsName: TextView
    private lateinit var BtnSettingsFavourites: Button
    private lateinit var BtnSettingsMedicalHistory: Button
    private lateinit var BtnSettingsAccount: Button
    private lateinit var BtnSettingsPrivacy: Button
    private lateinit var BtnSettingsNotifications: Button
    private lateinit var BtnSettingsStorage: Button
    private lateinit var BtnSettingsHelp: Button
    private lateinit var BtnSettingsLogOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)

        // Initialize views using findViewById
        TVSettingsName = findViewById(R.id.TVSettingsName)
        BtnSettingsFavourites = findViewById(R.id.BtnSettingsFavourites)
        BtnSettingsMedicalHistory = findViewById(R.id.BtnSettingsMedicalHistory)
        BtnSettingsAccount = findViewById(R.id.BtnSettingsAccount)
        BtnSettingsPrivacy = findViewById(R.id.BtnSettingsPrivacy)
        BtnSettingsNotifications = findViewById(R.id.BtnSettingsNotifications)
        BtnSettingsStorage = findViewById(R.id.BtnSettingsStorage)
        BtnSettingsHelp = findViewById(R.id.BtnSettingsHelp)
        BtnSettingsLogOut = findViewById(R.id.BtnSettingsLogOut)

        val username = "current_user"
        fetchUserDetails(username)
        setupNavigationButtons()

        // Retrieve active page from intent and set active page in navigation bar
        val activePage = intent.getStringExtra("active_page")
        if (activePage == "settings") {
            setActivePage()
        }
    }

    private fun fetchUserDetails(username: String) {
        lifecycleScope.launch {
            try {
                val user = userDao.getUserByUsername(username)

                if (user != null) {
                    TVSettingsName.text = "${user.firstName}, ${user.lastName}"
                } else {
                    Toast.makeText(this@SettingsActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Error fetching user details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNavigationButtons() {
        BtnSettingsFavourites.setOnClickListener {
            navigateToPlaceholderPage("Favourites")
        }

        BtnSettingsMedicalHistory.setOnClickListener {
            navigateToPlaceholderPage("Medical History")
        }

        BtnSettingsAccount.setOnClickListener {
            navigateToPlaceholderPage("Account")
        }

        BtnSettingsPrivacy.setOnClickListener {
            navigateToPlaceholderPage("Privacy")
        }

        BtnSettingsNotifications.setOnClickListener {
            navigateToPlaceholderPage("Notifications")
        }

        BtnSettingsStorage.setOnClickListener {
            navigateToPlaceholderPage("Storage and Data")
        }

        BtnSettingsHelp.setOnClickListener {
            navigateToPlaceholderPage("Help")
        }

        BtnSettingsLogOut.setOnClickListener {
            navigateToPlaceholderPage("Log Out")
        }
    }

    private fun navigateToPlaceholderPage(pageName: String) {
        Toast.makeText(this, "Navigating to $pageName page", Toast.LENGTH_SHORT).show()
    }

    // Function to set the active page in BottomNavigationView
    private fun setActivePage() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_settings
    }
}