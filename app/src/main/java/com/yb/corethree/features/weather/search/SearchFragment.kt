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
import com.yb.corethree.common.Resource
import com.yb.corethree.common.ResourceStatus
import com.yb.corethree.common.ResourceStatus.ERROR
import com.yb.corethree.common.ResourceStatus.LOADING
import com.yb.corethree.databinding.FragmentSearchBinding
import com.yb.corethree.di.ViewModelFactory
import com.yb.corethree.domain.entities.CityWeatherResponse
import com.yb.corethree.features.weather.search.CitiesAdapter.CityItem
import javax.inject.Inject
import kotlin.math.roundToInt

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding by lazy { _binding!! }
    private lateinit var citiesAdapter: CitiesAdapter

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSearchBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.cities.observe(viewLifecycleOwner) { renderViewState(it) }
    }

    private fun renderViewState(resource: Resource<List<CityWeatherResponse>>) {
        showUserMessages(resource.status)
        citiesAdapter.submitList(mapCityItems(resource.data))
    }

    private fun mapCityItems(cities: List<CityWeatherResponse>?): List<CityItem> {
        return cities?.map { response ->
            CityItem(
                name = "${response.name}, ${response.sys.country}",
                temp = response.main?.temp?.roundToInt()?.let { "$it c" } ?: "NA",
                description = response.weather?.first()?.main ?: "",
                speed = response.wind?.speed?.roundToInt()?.let { "$it m/s" } ?: "",
                direction = response.wind?.deg ?: 0.0F
            )
        } ?: emptyList()
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
            citiesAdapter = CitiesAdapter()
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