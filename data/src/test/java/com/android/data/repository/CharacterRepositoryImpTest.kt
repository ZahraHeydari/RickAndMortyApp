package com.android.data.repository

import com.android.data.model.CharactersResponse
import com.android.data.model.InfoEntity
import com.android.data.source.ApiService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import java.io.IOException
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.*

class CharacterRepositoryImpTest {

    private val apiService = mockk<ApiService>()
    private val repository = CharacterRepositoryImp(apiService)

    @Test
    fun `fetchAllCharacters Successful data fetch`() {
        val response = mockk<CharactersResponse>()
        val info = mockk<InfoEntity>()
        every { info.pages } returns 5
        every { response.info } returns info
    }

    @Test
    fun `fetchAllCharacters API returns empty list`() {
        val response = mockk<CharactersResponse>()
        val info = mockk<InfoEntity>()
        every { info.pages } returns 0
        every { response.info.pages } returns info.pages
        every { response.results } returns emptyList()
    }

    @Test
    fun `fetchAllCharacters API throws an exception`() = runTest {
        val page = 1
        val errorResponse = mockk<Response<*>>(relaxed = true) {
            every { code() } returns 404
        }
        val httpException = HttpException(errorResponse)
        coEvery { apiService.getAllCharacters(page) } throws httpException
        assertFailsWith<HttpException> { repository.fetchAllCharacters(page) }
    }

    @Test
    fun `fetchAllCharacters Invalid page number`() = runTest {
        val invalidPage = -1
        val mockResponse = mockk<CharactersResponse> {
            every { info } returns mockk { every { pages } returns -1 }
            every { results } returns emptyList()
        }
        coEvery { apiService.getAllCharacters(invalidPage) } returns mockResponse
        val result = repository.fetchAllCharacters(invalidPage)
        assertTrue(result.first.isEmpty(), "Expected empty list for invalid page number")
    }

    @Test
    fun `fetchAllCharacters Coroutine cancellation`() = runTest {
        val job = launch {
            repository.fetchAllCharacters(1)
        }
        job.cancelAndJoin()
        assertTrue(job.isCancelled)
    }

    @Test
    fun `fetchAllCharacters Instrumented Network unavailable`() = runTest {
        val page = 1
        val exception = IOException("Network error")
        coEvery { apiService.getAllCharacters(page) } throws exception
        assertFailsWith<IOException> { repository.fetchAllCharacters(page) }
    }

    @Test
    fun `getDetails Successful data fetch for a valid ID`() = runTest {
        val characterId = "1"
        coEvery { apiService.getCharacterDetails(characterId) } returns mockCharacterEntity
        val result = repository.getDetails(characterId)
        assertNotNull(result)
        assertEquals(mockCharacterEntity.id, result.id)
        assertEquals(mockCharacterEntity.name, result.name)
        assertEquals(mockCharacterEntity.status, result.status)
        assertEquals(mockCharacterEntity.species, result.species)
        assertEquals(mockCharacterEntity.image, result.image)
    }

    @Test
    fun `getDetails Non existent ID`() {
        runTest {
            val nonExistentId = "9999"
            val errorResponse = mockk<Response<*>>(relaxed = true) { every { code() } returns 404 }
            val httpException = HttpException(errorResponse)
            coEvery { apiService.getCharacterDetails(nonExistentId) } throws httpException
            assertFailsWith<HttpException> { repository.getDetails(nonExistentId) }
        }
    }

    @Test
    fun `getDetails API throws an exception`() = runTest {
        val id = "1"
        val errorResponse = mockk<Response<*>>(relaxed = true) {
            every { code() } returns 404
        }
        val httpException = HttpException(errorResponse)
        coEvery { apiService.getCharacterDetails(id) } throws httpException
        assertFailsWith<HttpException> { repository.getDetails(id) }
    }

    @Test
    fun `getDetails Coroutine cancellation`() = runTest {
        val job = launch {
            repository.getDetails("1")
        }
        job.cancelAndJoin()
        assertTrue(job.isCancelled)
    }
}