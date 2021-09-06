package com.example.codechallenge.ui.show

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.codechallenge.MockApiService
import com.example.codechallenge.data.Data
import com.example.codechallenge.data.Show
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class ShowViewModelTest: Spek({
    val payload = listOf( Show(title = "title1", episodeCount = 1), Show(title = "title2", episodeCount = 1), Show(title = "title3", episodeCount = 1))
    val data = Data( payload = payload)
    val mockApiService = MockApiService( data )
    lateinit var showViewModel: ShowViewModel
    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    beforeGroup {
        Dispatchers.setMain(mainThreadSurrogate)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
        showViewModel = spyk(ShowViewModel(apiService = mockApiService))
    }

    afterGroup {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    describe("init") {
        it("should init successfully") {
            showViewModel.data.observeForever {
                Assertions.assertThat(showViewModel.data.value).isEqualTo(data)
            }
        }
    }

    describe("#${ShowViewModel::displayDataDetails.name}") {
        it("should return") {
            val show = Show(title = "123")
            showViewModel.displayDataDetails(show)
            Assertions.assertThat(showViewModel.selectedData.value).isEqualTo(show)
        }
    }

    describe("#${ShowViewModel::displayDataDetailComplete.name}") {
        it("should return null") {
            showViewModel.displayDataDetailComplete()
            Assertions.assertThat(showViewModel.selectedData.value).isNull()
        }
    }
})

