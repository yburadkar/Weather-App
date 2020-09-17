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
import com.yb.corethree.App
import com.yb.corethree.databinding.FragmentSearchBinding
import com.yb.corethree.di.ViewModelFactory
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding by lazy { _binding!! }

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

    }

    private fun setUpViews() {
        with(binding) {
            etCityName.addTextChangedListener { text ->
                text?.takeIf { it.trimmedLength() > 2 }
                    ?.let { viewModel.searchText.onNext(it.trim().toString()) }
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