/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.audioshinigami.currencyconverter.di.components.DaggerTestAppComponent
import io.audioshinigami.currencyconverter.di.components.TestAppComponent
import io.audioshinigami.currencyconverter.utils.PaperFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PaperRepoImplTest {

    @Inject lateinit var paperRepository: PaperRepository

    @Before
    fun init(){
        // init dagger2
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        val component: TestAppComponent = DaggerTestAppComponent.factory().create(applicationContext)

        component.inject(this)
    }

    @Test
    fun deleteAllPaper_DeleteAllPaperFromLocalDataSource() = runBlockingTest {
        // Arrange : create multiple paper variables & save
        paperRepository.save( PaperFactory.getPaperEntries() )

        // Act: delete all
        paperRepository.deleteAll()

        // Assert :
        val result = paperRepository.getPapers()
//        assertThat(result.isEmpty())
    }

    @Test
    fun savePaper_confirmPaperIsSaved() = runBlockingTest {
        // Arrange : create Paper Variable
        val savePaper = PaperFactory.getPaper()

        // Act: save rate
        paperRepository.save(savePaper)

        // Assert
        val result = paperRepository.getPapers()
//        assertThat(result).contains(savePaper)
    }

}