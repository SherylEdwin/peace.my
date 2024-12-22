package com.example.madgassignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_navigation_menu)

        val homeIcon: ImageView = findViewById(R.id.nav_home)
        val searchIcon: ImageView = findViewById(R.id.nav_search)
        val notificationIcon: ImageView = findViewById(R.id.nav_notification)
        val settingsIcon: ImageView = findViewById(R.id.nav_settings)

        // Get the active page from the intent
        val activePage = intent.getStringExtra("active_page")

        // Set selected icon based on the active page
        homeIcon.isSelected = activePage == "home"
        searchIcon.isSelected = activePage == "search"
        notificationIcon.isSelected = activePage == "notification"
        settingsIcon.isSelected = activePage == "settings"

        // Home
        homeIcon.setOnClickListener {
            navigateTo(HomePageActivity::class.java, homeIcon, searchIcon, notificationIcon, settingsIcon)
        }

        // Search
        searchIcon.setOnClickListener {
            navigateTo(SearchActivity::class.java, homeIcon, searchIcon, notificationIcon, settingsIcon)
        }

        // Notifications
        notificationIcon.setOnClickListener {
            navigateTo(NotificationsActivity::class.java, homeIcon, searchIcon, notificationIcon, settingsIcon)
        }

        // Settings
        settingsIcon.setOnClickListener {
            navigateTo(SettingsActivity::class.java, homeIcon, searchIcon, notificationIcon, settingsIcon)
        }
    }

    // Update the icons
    private fun navigateTo(activityClass: Class<*>, vararg icons: ImageView) {
        icons.forEach { it.isSelected = false } // Reset all icons to unselected
        val selectedIcon = icons.first { it.id == R.id.nav_search } // Default to search icon being clicked
        selectedIcon.isSelected = true

        startActivity(Intent(this, activityClass))
        finish()
    }

    // Method to update the active page in the bottom navigation
    fun setActivePage(activePage: String?) {
        val homeIcon: ImageView = findViewById(R.id.nav_home)
        val searchIcon: ImageView = findViewById(R.id.nav_search)
        val notificationIcon: ImageView = findViewById(R.id.nav_notification)
        val settingsIcon: ImageView = findViewById(R.id.nav_settings)

        homeIcon.isSelected = activePage == "home"
        searchIcon.isSelected = activePage == "search"
        notificationIcon.isSelected = activePage == "notification"
        settingsIcon.isSelected = activePage == "settings"
    }
}

