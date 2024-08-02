package com.arkul.mychat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.arkul.mychat.data.network.connectivity.NetworkConnectivityObserver
import com.arkul.mychat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    private val connectivityObserver by lazy { NetworkConnectivityObserver(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController


        setupWithNavController(binding.bottomNavigation, navController)
        setupBottomNavigationBarBadge()


        /*lifecycleScope.launch {
            mainViewModel.getHasUserProfile().let {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
                val inflater = navHostFragment.navController.navInflater
                val graph = inflater.inflate(R.navigation.nav_graph)

                when (it) {
                    true -> graph.setStartDestination(R.navigation.nav_graph)
                    false -> graph.setStartDestination(R.id.createProfileFragment)
                    null -> graph.setStartDestination(R.id.initialFragment)
                }

                val navController = navHostFragment.navController
                navController.setGraph(graph, intent.extras)
            }
        }*/


        //findNavController(binding.fragmentContainer.id).navigate(InitialFragmentDirections.actionInitialFragmentToMainGraph())



        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupBottomNavigationBarBadge() {
        binding.bottomNavigation.getOrCreateBadge(R.id.inboxChatsFragment).apply {
            isVisible = true
            number = 123
            maxNumber = 99
        }
    }

    fun bottomNavigationSwitchTo(id: Int) {
        binding.bottomNavigation.selectedItemId = id
    }

    fun hideBottomNavigationBar() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigationBar() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}