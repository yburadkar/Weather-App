package com.yb.openweather.features.weather.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yb.openweather.App
import com.yb.openweather.R
import com.yb.openweather.common.Resource
import com.yb.openweather.common.ResourceStatus
import com.yb.openweather.common.ResourceStatus.ERROR
import com.yb.openweather.common.ResourceStatus.LOADING
import com.yb.openweather.common.ResourceStatus.SUCCESS
import com.yb.openweather.common.TextToolbarUpdate
import com.yb.openweather.databinding.FragmentSearchBinding
import com.yb.openweather.di.ViewModelFactory
import com.yb.openweather.domain.entities.CityWeatherResponse
import com.yb.openweather.mappers.SearchCityMapper
import com.yb.openweather.models.City
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val resultClickAction: (City) -> Unit = {
        viewModel.navigateToDetail(city = it)
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSearchBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CitiesAdapter(resultClickAction)
        setUpViews(adapter)
        observeViewModel(adapter)
    }

    override fun onStart() {
        super.onStart()
        viewModel.sendToolbarUpdate(TextToolbarUpdate(getString(R.string.search_fragment_title)))
    }

    private fun observeViewModel(adapter: CitiesAdapter) {
        viewModel.cities.observe(viewLifecycleOwner) { renderViewState(it, adapter) }
    }

    private fun renderViewState(resource: Resource<List<CityWeatherResponse>>, adapter: CitiesAdapter) {
        showUserMessages(resource.status)
        if (resource.status == SUCCESS) {
            val cities = SearchCityMapper.map(resource.data)
            adapter.submitList(cities)
            if (cities.isEmpty()) showSnackBar(getString(R.string.no_matching_city_message))
        }
    }

    private fun showUserMessages(status: ResourceStatus) {
        if (status == ERROR) showSnackBar(getString(R.string.loading_error_message))
        with(binding) {
            srlCities.isRefreshing = status == LOADING
        }
    }

    private fun setUpViews(adapter: CitiesAdapter) {
        with(binding) {
            rvCities.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            etCityName.addTextChangedListener { text ->
                text?.trim()?.takeIf { it.length > 2 }
                    ?.let { viewModel.updateSearchText(it.toString()) }
            }
            srlCities.setOnRefreshListener {
                val text = etCityName.text?.trim() ?: ""
                if (text.length > 2) viewModel.searchText.onNext(etCityName.text?.trim().toString())
                else srlCities.isRefreshing = false
            }
        }
    }

    private fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(requireView(), message, duration).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inject() {
        (requireActivity().application as App).appComponent.inject(this)
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

}