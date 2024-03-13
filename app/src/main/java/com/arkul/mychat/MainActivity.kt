package com.arkul.mychat

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.arkul.mychat.databinding.ActivityMainBinding
import com.arkul.mychat.data.network.connectivity.NetworkConnectivityObserver
import com.arkul.mychat.ui.fragments.initial.InitialFragment
import com.arkul.mychat.ui.fragments.register.RegisterFragment
import com.arkul.mychat.utilities.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val connectivityObserver by lazy { NetworkConnectivityObserver(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        //setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(binding.fragmentContainer.id, InitialFragment())
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}