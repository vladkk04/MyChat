package com.arkul.mychat

import android.app.TaskStackBuilder
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.whenCreated
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.NavigationUiSaveStateControl
import androidx.navigation.ui.setupActionBarWithNavController
import com.arkul.mychat.databinding.ActivityMainBinding
import com.arkul.mychat.data.network.connectivity.NetworkConnectivityObserver
import com.arkul.mychat.ui.screens.initial.InitialFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}