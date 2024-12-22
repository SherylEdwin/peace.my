package com.example.madgassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

class SearchActivity : BaseActivity() {

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        intent.putExtra("active_page", "search")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_page)

        val activePage = intent.getStringExtra("active_page")
        if (activePage == "search") {
            setActivePage(activePage)
        }

        setupSearchComponents()
    }

    private fun setupSearchComponents() {
        val searchBox: AutoCompleteTextView = findViewById(R.id.AutoCompleteSearch)
        val searchSuggestions = listOf("Suggestion 1", "Suggestion 2", "Suggestion 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, searchSuggestions)
        searchBox.setAdapter(adapter)

        searchBox.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = adapter.getItem(position)
            performSearch(selectedItem ?: "")
        }

        val noResultsTextView: TextView = findViewById(R.id.TVNoSearchResults)
        val recyclerView: RecyclerView = findViewById(R.id.RVSearchResults)

        noResultsTextView.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, searchResults: List<String>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchResultsAdapter(searchResults)
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch {
            val results = database.userDao().searchUsers("%$query%")

            updateSearchResults(results)
        }
    }

    private fun updateSearchResults(results: List<User>) {
        val noResultsTextView: TextView = findViewById(R.id.TVNoSearchResults)
        val recyclerView: RecyclerView = findViewById(R.id.RVSearchResults)

        if (results.isEmpty()) {
            noResultsTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            noResultsTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            setupRecyclerView(recyclerView, results.map { it.username })
        }
    }
}

class SearchResultsAdapter(private val results: List<String>) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultText: TextView = itemView.findViewById(R.id.TVSearchResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultText.text = results[position]
    }

    override fun getItemCount(): Int = results.size
}
