package com.yb.corethree.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.common.DetailWeatherNavigationEvent
import com.yb.corethree.common.NavigationEvent
import com.yb.corethree.common.SearchWeatherNavigationEvent
import com.yb.corethree.common.TextToolbarUpdate
import com.yb.corethree.common.ToolbarUpdate
import com.yb.corethree.databinding.ActivityMainBinding
import com.yb.corethree.di.ViewModelFactory
import com.yb.corethree.features.weather.detail.DetailForecastFragment
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
        if (savedInstanceState == null) viewModel.navigateToSearchScreen()
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
        when (navEvent) {
            is SearchWeatherNavigationEvent -> switchFragment(SearchFragment.newInstance(), addToBackStack = false)
            is DetailWeatherNavigationEvent -> {
                switchFragment(DetailForecastFragment.newInstance(navEvent.city), addToBackStack = true)
            }
        }
    }

    private fun switchFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, CURRENT_FRAGMENT_TAG)

        if (addToBackStack) transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun handleToolbarEvent(toolbarUpdate: ToolbarUpdate) {
        when (toolbarUpdate) {
            is TextToolbarUpdate -> updateToolbar(toolbarUpdate.title)
        }
    }

    private fun updateToolbar(title: String) {
        supportActionBar?.apply {
            this.title = title
            val showBack = supportFragmentManager.backStackEntryCount > 0
            setDisplayShowHomeEnabled(showBack)
            setDisplayHomeAsUpEnabled(showBack)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun inject() {
        (application as App).appComponent.inject(this)
    }

    companion object {
        private const val CURRENT_FRAGMENT_TAG = "current_fragment"
    }

}