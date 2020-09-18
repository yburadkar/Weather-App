package com.yb.corethree.features.weather.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.trimmedLength
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.common.Resource
import com.yb.corethree.common.ResourceStatus
import com.yb.corethree.common.ResourceStatus.ERROR
import com.yb.corethree.common.ResourceStatus.LOADING
import com.yb.corethree.common.TextToolbarUpdate
import com.yb.corethree.databinding.FragmentSearchBinding
import com.yb.corethree.di.ViewModelFactory
import com.yb.corethree.domain.entities.CityWeatherResponse
import com.yb.corethree.mappers.SearchCityMapper
import com.yb.corethree.models.City
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding by lazy { _binding!! }
    private lateinit var citiesAdapter: CitiesAdapter
    private val resultClickAction: (City) -> Unit = {
        viewModel.navigateToDetail(city = it)
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
        Timber.d("onAttach")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSearchBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        setUpViews()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.sendToolbarUpdate(TextToolbarUpdate(getString(R.string.search_fragment_title)))
        Timber.d("onStart")
    }

    private fun observeViewModel() {
        viewModel.cities.observe(viewLifecycleOwner) { renderViewState(it) }
    }

    private fun renderViewState(resource: Resource<List<CityWeatherResponse>>) {
        Timber.d("renderViewState")
        showUserMessages(resource.status)
        citiesAdapter.submitList(SearchCityMapper.map(resource.data))
    }

    private fun showUserMessages(status: ResourceStatus) {
        with(binding) {
            tvLoadingError.visibility = if (status == ERROR) View.VISIBLE else View.GONE
            srlCities.isRefreshing = status == LOADING
        }
    }

    private fun setUpViews() {
        with(binding) {
            etCityName.addTextChangedListener { text ->
                text?.takeIf { it.trimmedLength() > 2 }
                    ?.let { viewModel.searchText.onNext(it.trim().toString()) }
            }
            citiesAdapter = CitiesAdapter(resultClickAction)
            rvCities.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = citiesAdapter
            }
            srlCities.setOnRefreshListener {
                viewModel.searchText.onNext(etCityName.text?.trim().toString())
            }
        }
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
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