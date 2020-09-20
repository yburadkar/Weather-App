package com.yb.corethree.features.weather.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yb.corethree.common.DetailWeatherNavigationEvent
import com.yb.corethree.common.Navigator
import com.yb.corethree.common.SimpleIdlingResource
import com.yb.corethree.common.ToolbarManager
import com.yb.corethree.domain.entities.CityList
import com.yb.corethree.domain.repos.ICurrentWeatherRepository
import com.yb.corethree.models.City
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel
    private val cityList: CityList = mockk()
    private val weatherRepo = mockk<ICurrentWeatherRepository>()
    private val trampoline = Schedulers.trampoline()
    private val navigator = spyk<Navigator>()
    private val toolbarManager = spyk<ToolbarManager>()
    private val idlingResource = SimpleIdlingResource

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(cityList, weatherRepo, trampoline, trampoline, navigator, toolbarManager, idlingResource)
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

    @Test
    fun navigateToDetail() {
        val city = mockk<City>()
        searchViewModel.navigateToDetail(city)
        verify { navigator.sendNavigationEvent(DetailWeatherNavigationEvent(city = city)) }
    }

}