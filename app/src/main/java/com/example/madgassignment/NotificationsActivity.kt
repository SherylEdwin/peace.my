package com.example.madgassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificationsActivity : BaseActivity() {

    private lateinit var notificationsRecyclerView: RecyclerView
    private val notificationsList = mutableListOf<Notification>()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifications_page)

        // Initialize views
        notificationsRecyclerView = findViewById(R.id.RVNotifications)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Add dummy data to the notifications list
        notificationsList.add(
            Notification(
                title = "Promotion!",
                message = "Are you ready for a 4x session ...",
                timestamp = "2024-12-11 10:00"
            )
        )
        notificationsList.add(
            Notification(
                title = "Medication Reminder",
                message = "Don't forget to take your 125g of P...",
                timestamp = "2024-12-11 15:30"
            )
        )
        notificationsList.add(
            Notification(
                title = "peace.my updates",
                message = "Your opinion matters! Please let us know...",
                timestamp = "2024-12-11 19:00"
            )
        )

        // Setup RecyclerView
        setupRecyclerView()

        // Retrieve active page from intent and set active page in navigation bar
        val activePage = intent.getStringExtra("active_page")
        if (activePage == "notifications") {
            setActivePage()
        }
    }

    private fun setupRecyclerView() {
        notificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        notificationsRecyclerView.adapter = NotificationsAdapter(notificationsList)
    }

    // Function to set the active page in BottomNavigationView
    private fun setActivePage() {
        bottomNavigationView.selectedItemId = R.id.nav_notification
        Toast.makeText(this, "You are on the Notifications page", Toast.LENGTH_SHORT).show()
    }
}

class NotificationsAdapter(private val notifications: List<Notification>) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.notificationTitle)
        val message: TextView = itemView.findViewById(R.id.notificationMessage)
        val timestamp: TextView = itemView.findViewById(R.id.notificationTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.title.text = notification.title
        holder.message.text = notification.message
        holder.timestamp.text = notification.timestamp
    }

    override fun getItemCount(): Int = notifications.size
}

data class Notification(
    val title: String,
    val message: String,
    val timestamp: String
)
