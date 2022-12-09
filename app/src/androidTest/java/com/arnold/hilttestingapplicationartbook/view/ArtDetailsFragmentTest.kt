package com.arnold.hilttestingapplicationartbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.arnold.hilttestingapplicationartbook.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.arnold.hilttestingapplicationartbook.R
import com.arnold.hilttestingapplicationartbook.getOrAwaitValueTest
import com.arnold.hilttestingapplicationartbook.repo.FakeArtRepository
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.arnold.hilttestingapplicationartbook.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageApiFragmentAPI() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.artSelectImage)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
        )
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave() {
        val testViewModel = ArtViewModel(FakeArtRepository())
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.artNameDetailedText)).perform(ViewActions.replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.authorNameDetailedText)).perform(ViewActions.replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.yearDetailedText)).perform(ViewActions.replaceText("1700"))
        Espresso.onView(withId(R.id.SaveButton)).perform(ViewActions.click())

        assertThat(testViewModel.artList.getOrAwaitValueTest()).contains(
            ArtEntity(
                "Mona Lisa",
                "Da Vinci",
                1700,"")
        )

    }
}