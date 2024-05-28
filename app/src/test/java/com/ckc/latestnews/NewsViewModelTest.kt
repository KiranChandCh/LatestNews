package com.ckc.latestnews

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ckc.latestnews.model.NewsItem
import com.ckc.latestnews.model.NewsSectionsRequest
import com.ckc.latestnews.repository.APIService
import com.ckc.latestnews.repository.NewsRepository
import com.ckc.latestnews.viewmodel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private lateinit var apiService: APIService
    private lateinit var newsRepository: NewsRepository
    private lateinit var viewModel: NewsViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher for testing
        apiService = mock(APIService::class.java)
        newsRepository = mock(NewsRepository::class.java) // Mock NewsRepository for testing
        val factory = TestNewsViewModelFactory(newsRepository)
        val viewModelStoreOwner = object : ViewModelStoreOwner {
            private val store = ViewModelStore()
            override val viewModelStore: ViewModelStore
                get() = store
        }
        viewModel = ViewModelProvider(viewModelStoreOwner, factory).get(NewsViewModel::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after testing
        testDispatcher.cleanupTestCoroutines() // Clean up the test dispatcher
    }

    @Test
    fun fetchNewsCategoriesTest() = runBlockingTest {
        // Arrange
        val mockCategories = listOf("Sports", "Technology")
        `when`(newsRepository.getNewsCategories()).thenReturn(mockCategories)

        // Act
        viewModel.fetchNewsCategories()

        // Assert
        verify(newsRepository).getNewsCategories()
        // Add further assertions to check if the ViewModel's LiveData has been updated
    }

    @Test
    fun getNewsBySectionsTest() = runBlockingTest {
        //Arrange
        val expectedItems = listOf( NewsItem("link1", "og1", "source1", "icon1", "title1", "Business"),
            NewsItem("link3", "og3", "source3", "icon3", "title3", "Sports"))
        val mockCategories = NewsSectionsRequest(listOf("Business", "Sports"))
        val mockResponse = mapOf(
            "Business" to listOf(
                mapOf("link" to "link1", "og" to "og1", "source" to "source1", "source_icon" to "icon1", "title" to "title1")
            ),
            "Sports" to listOf(
                mapOf("link" to "link3", "og" to "og3", "source" to "source3", "source_icon" to "icon3", "title" to "title3")
            )
        )
        `when`(newsRepository.getNewsBySections(mockCategories)).thenReturn(mockResponse)

        //Act
        viewModel.getNewsBySections(mockCategories)

        //Assert
        verify(newsRepository).getNewsBySections(mockCategories)

        val expectedGroupedNewsItems = expectedItems.groupBy { it.type }
        val value = viewModel.newsBySections.value

        Assert.assertEquals(expectedGroupedNewsItems, value)
    }

}
