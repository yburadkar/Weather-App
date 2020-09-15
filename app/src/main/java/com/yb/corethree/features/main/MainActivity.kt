package com.yb.corethree.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.yb.corethree.App
import com.yb.corethree.common.NavigationEvent
import com.yb.corethree.databinding.ActivityMainBinding
import com.yb.corethree.di.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.navEvent.observe(this) { uiEvent ->
            uiEvent.getContent()?.let { handleNavigationEvent(it) }
        }
        viewModel.toolbarEvent.observe(this) {
            it.getContent()?.let { handleToolbarEvent() }
        }
    }

    private fun handleNavigationEvent(navEvent: NavigationEvent) {

    }

    private fun handleToolbarEvent() {

    }

    private fun inject() {
        (application as App).appComponent.inject(this)
    }

}