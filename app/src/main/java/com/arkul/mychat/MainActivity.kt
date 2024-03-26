package com.arkul.mychat

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.arkul.mychat.databinding.ActivityMainBinding
import com.arkul.mychat.data.network.connectivity.NetworkConnectivityObserver
import com.arkul.mychat.ui.fragments.initial.InitialFragment
import com.arkul.mychat.ui.fragments.waitingVerify.WaitingVerifyEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val connectivityObserver by lazy { NetworkConnectivityObserver(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}