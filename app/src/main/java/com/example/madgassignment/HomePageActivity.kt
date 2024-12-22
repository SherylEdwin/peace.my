package com.example.madgassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class HomePageActivity : BaseActivity() {

    private val handler = Handler()
    private var newsIndex = 0

    private val newsImages = listOf(
        R.drawable.news_image_1,
        R.drawable.news_image_2
    )

    private val newsTitles = listOf(
        "Flood in Kelantan",
        "Dengue is spreading"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val homeNews: ImageView = findViewById(R.id.IVHomeNews)
        val homeNewsTitle: TextView = findViewById(R.id.TVHomeNewsTitle)

        startNewsUpdates(homeNews, homeNewsTitle)

        // Symptom Tracking Button
        val symptomTrackingButton: ImageButton = findViewById(R.id.IBHomeSymptomTracking)
        symptomTrackingButton.setOnClickListener {
            startActivity(Intent(this, SymptomTrackingActivity::class.java))
        }

        // Medication Management Button
        val medicationManagementButton: ImageButton = findViewById(R.id.IBHomeMedicationManagement)
        medicationManagementButton.setOnClickListener {
            startActivity(Intent(this, MedicationManagementActivity::class.java))
        }

        // Donor Pledge Button
        val donorPledgeButton: ImageButton = findViewById(R.id.IBHomeDonorPledge)
        donorPledgeButton.setOnClickListener {
            startActivity(Intent(this, DonorPledgeActivity::class.java))
        }

        // Education Button
        val educationButton: ImageButton = findViewById(R.id.IBHomeEducation)
        educationButton.setOnClickListener {
            startActivity(Intent(this, EducationActivity::class.java))
        }

        // Calendar Button
        val calendarButton: ImageButton = findViewById(R.id.IBHomeCalendar)
        calendarButton.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        setActivePage("home")
    }

    private fun startNewsUpdates(homeNews: ImageView, homeNewsTitle: TextView) {
        // Update news image and title every 10 seconds
        handler.postDelayed(object : Runnable {
            override fun run() {
                homeNews.setImageResource(newsImages[newsIndex])
                homeNewsTitle.text = newsTitles[newsIndex]

                newsIndex = (newsIndex + 1) % newsImages.size

                handler.postDelayed(this, 10000)
            }
        }, 0)
    }

    override fun onStop() {
        super.onStop()
        // Remove any pending messages or callbacks from the handler when activity is stopped
        handler.removeCallbacksAndMessages(null)
    }

    private fun setActivePage(page: String) {
        val intent = Intent(this, BaseActivity::class.java)
        intent.putExtra("active_page", page)
        startActivity(intent)
    }
}
