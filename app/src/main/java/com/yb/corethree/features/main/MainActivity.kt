package com.yb.corethree.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.common.DetailWeatherNavigationEvent
import com.yb.corethree.common.NavigationEvent
import com.yb.corethree.common.SearchWeatherNavigationEvent
import com.yb.corethree.common.ToolbarUpdate
import com.yb.corethree.databinding.ActivityMainBinding
import com.yb.corethree.di.ViewModelFactory
import com.yb.corethree.features.weather.search.SearchFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        observeViewModel()
        if(savedInstanceState == null) viewModel.navigateToSearchScreen()
    }

    private fun observeViewModel() {
        viewModel.navEvent.observe(this) { uiEvent ->
            uiEvent.getContent()?.let { handleNavigationEvent(it) }
        }
        viewModel.toolbarEvent.observe(this) { uiEvent ->
            uiEvent.getContent()?.let { handleToolbarEvent(it) }
        }
    }

    private fun handleNavigationEvent(navEvent: NavigationEvent) {
        when(navEvent) {
            is SearchWeatherNavigationEvent -> switchFragment(SearchFragment.newInstance(), addToBackStack = true)
            is DetailWeatherNavigationEvent -> {

            }
        }
    }

    private fun switchFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, CURRENT_FRAGMENT_TAG)

        if(addToBackStack) transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun handleToolbarEvent(toolbarUpdate: ToolbarUpdate) {

    }

    private fun inject() {
        (application as App).appComponent.inject(this)
    }

    companion object {
        private const val CURRENT_FRAGMENT_TAG = "current_fragment"
    }

}