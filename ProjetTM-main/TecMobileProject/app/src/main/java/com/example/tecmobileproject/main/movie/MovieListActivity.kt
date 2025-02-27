package com.example.tecmobileproject.main.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.tecmobileproject.MovieManagerFragment
import com.example.tecmobileproject.R
import com.example.tecmobileproject.databinding.ActivityMainBinding
import com.example.tecmobileproject.main.favorie.FavorieActivity
import com.google.android.material.navigation.NavigationView

class MovieListActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val preferences = getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
        val token = preferences.getString("jwtToken", "???")

        viewModel.getDataTokenRole(token.toString()) { userRole ->
            val navView: NavigationView = findViewById(R.id.nav_view)

            val menu = navView.menu

            val addMovieItem = menu.findItem(R.id.nav_add_movie)

            addMovieItem.isVisible = userRole == "admin"

            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this, MovieListActivity::class.java))
                        true
                    }
                    R.id.nav_add_movie -> {
                        if (userRole == "admin") {
                            startActivity(Intent(this, AddMovieActivity::class.java))
                        }
                        true
                    }
                    R.id.nav_favori -> {
                        startActivity(Intent(this, FavorieActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
        }


        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MovieManagerFragment.newInstance())
            .commit()

        setUpListeners()
    }



    private fun setUpListeners() {

    }
}