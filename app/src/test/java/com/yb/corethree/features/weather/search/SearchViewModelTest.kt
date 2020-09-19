package com.yb.corethree.features.weather.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.ToolbarManager
import com.yb.corethree.domain.entities.CityList
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel
    private val cityList: CityList = mockk()
    private val weatherRepo = mockk<ICurrentWeatherRepository>()
    private val trampoline = Schedulers.trampoline()
    private val navigator = Navigator // spyk<Navigator>()
    private val toolbarManager = spyk<ToolbarManager>()

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(cityList, weatherRepo, trampoline, trampoline, navigator, toolbarManager)
    }


    @Test
    fun updateSearchText_UpdatesPrevSearchTerm_IfCalledFirstTime() {
        val firstText = "ABC"
        searchViewModel.updateSearchText(firstText)
        val result = searchViewModel.prevSearchTerm
        assertThat(result, `is`(firstText))
    }

    @Test
    fun updateSearchText_updatesPrevSearchTerm_IfSearchTextIsDifferent() {
        val firstText = "ABC"
        searchViewModel.updateSearchText(firstText)
        val newText = "ABD"
        searchViewModel.updateSearchText(newText)
        val result = searchViewModel.prevSearchTerm
        assertThat(result, `is`(newText))
    }

}