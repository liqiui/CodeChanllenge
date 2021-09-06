package com.example.codechallenge.ui.detail

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.codechallenge.data.Show
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class DetailViewModelTest: Spek({

    val mockApplication = mockk<Application>() {
        every { applicationContext } returns mockk()
    }
    val show = Show(title = "111")
    lateinit var detailViewModel: DetailViewModel
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
        detailViewModel = spyk(DetailViewModel(show = show, app = mockApplication))
    }

    afterGroup {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    describe("init") {
        it("should init successfully") {
            detailViewModel.show.observeForever {
                Assertions.assertThat(detailViewModel.show.value).isEqualTo(show)
            }
        }
    }
})