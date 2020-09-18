package com.yb.corethree.features.weather.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yb.corethree.App
import com.yb.corethree.R
import com.yb.corethree.common.Resource
import com.yb.corethree.common.ResourceStatus
import com.yb.corethree.common.TextToolbarUpdate
import com.yb.corethree.databinding.FragmentDetailForecastBinding
import com.yb.corethree.di.ViewModelFactory
import com.yb.corethree.models.City
import com.yb.corethree.models.ForecastItem
import javax.inject.Inject

class DetailForecastFragment : Fragment() {

    private var _binding: FragmentDetailForecastBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DetailForecastViewModel by viewModels { viewModelFactory }
    private val city: City by lazy { requireArguments().getParcelable(KEY_CITY)!! }
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDetailForecastBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeViewModel()
        viewModel.getDetailForecast(cityId = city.id)
    }

    override fun onStart() {
        super.onStart()
        viewModel.sendToolbarUpdate(TextToolbarUpdate(getString(R.string.detail_fragment_title)))
    }

    private fun observeViewModel() {
        viewModel.forecasts.observe(viewLifecycleOwner) {
            renderViewState(it)
        }
    }

    private fun renderViewState(resource: Resource<List<ForecastItem>>) {
        showUserMessages(resource.status)
        forecastAdapter.submitList(resource.data ?: emptyList())
    }

    private fun showUserMessages(status: ResourceStatus) {
        if (status == ResourceStatus.ERROR) showSnackBar(getString(R.string.loading_error_message))
        with(binding) {
            srlForecasts.isRefreshing = status == ResourceStatus.LOADING
        }
    }

    private fun setUpViews() {
        forecastAdapter = ForecastAdapter()
        with(binding) {
            tvCity.text = city.name
            rvForecasts.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = forecastAdapter
            }
            srlForecasts.setOnRefreshListener { viewModel.getDetailForecast(cityId = city.id) }
        }
    }

    private fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(requireView(), message, duration).show()
    }

    private fun inject() {
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_CITY = "city"

        fun newInstance(city: City): DetailForecastFragment {
            return DetailForecastFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_CITY, city)
                }
            }
        }
    }

}