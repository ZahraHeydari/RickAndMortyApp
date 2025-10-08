package com.android.domain.usecase

import com.android.domain.model.Character
import com.android.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.async
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetAllCharactersUseCaseTest {

    private val repository = mockk<CharacterRepository>()
    private val getAllCharactersUseCase = GetAllCharactersUseCase(repository)

    @Test
    fun `fetchAllCharacters success with valid page number`() = runTest {
        val page = 1
        val expectedCharacters = listOf(rickCharacter, mortyCharacter)
        val expectedPages = 42
        val repositoryResult = expectedCharacters to expectedPages
        coEvery { repository.fetchAllCharacters(page) } returns repositoryResult

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isSuccess)
        assertEquals(expectedCharacters, result.getOrNull()?.first)
        assertEquals(expectedPages, result.getOrNull()?.second)
    }

    @Test
    fun `fetchAllCharacters success with empty character list`() = runTest {
        val page = 1
        val expectedCharacters = emptyList<Character>()
        val expectedPages = 42
        val repositoryResult = expectedCharacters to expectedPages
        coEvery { repository.fetchAllCharacters(page) } returns repositoryResult

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isSuccess)
        assertEquals(expectedCharacters, result.getOrNull()?.first)
        assertEquals(expectedPages, result.getOrNull()?.second)
    }

    @Test
    fun `fetchAllCharacters success on last page`() = runTest {
        val lastPage = 42
        val expectedCharacters = listOf(rickCharacter, mortyCharacter)
        val expectedPages = 42
        val repositoryResult = expectedCharacters to expectedPages
        coEvery { repository.fetchAllCharacters(lastPage) } returns repositoryResult

        val result = getAllCharactersUseCase.fetchAllCharacters(lastPage)
        assertTrue(result.isSuccess)
        assertEquals(expectedCharacters, result.getOrNull()?.first)
        assertEquals(expectedPages, result.getOrNull()?.second)
    }

    @Test
    fun `fetchAllCharacters repository throws exception`() = runTest {
        val page = 1
        val exception = Exception("Error happened!")
        coEvery { repository.fetchAllCharacters(page) } throws exception

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `fetchAllCharacters with zero as page number`() = runTest {
        val page = 0
        val expectedCharacters = listOf(rickCharacter, mortyCharacter)
        val expectedPages = 42
        val repositoryResult = expectedCharacters to expectedPages
        coEvery { repository.fetchAllCharacters(page) } returns repositoryResult

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isSuccess)
        assertEquals(expectedCharacters, result.getOrNull()?.first)
        assertEquals(expectedPages, result.getOrNull()?.second)
    }

    @Test
    fun `fetchAllCharacters with negative page number`() = runTest {
        val page = -1
        val expectedCharacters = listOf(rickCharacter, mortyCharacter)
        val expectedPages = 42
        val repositoryResult = expectedCharacters to expectedPages
        coEvery { repository.fetchAllCharacters(page) } returns repositoryResult

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isSuccess)
        assertEquals(expectedCharacters, result.getOrNull()?.first)
        assertEquals(expectedPages, result.getOrNull()?.second)
    }

    @Test
    fun `fetchAllCharacters with invalid page number`() = runTest {
        val page = 43
        val exception = Exception("Error happened!")
        coEvery { repository.fetchAllCharacters(page) } throws exception

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `fetchAllCharacters with large page number`() = runTest {
        val page = 1000
        val exception = Exception("Error happened!")
        coEvery { repository.fetchAllCharacters(page) } throws exception

        val result = getAllCharactersUseCase.fetchAllCharacters(page)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `fetchAllCharacters concurrency check`() = runTest {
        val page = 1
        val expectedCharacters = listOf(rickCharacter, mortyCharacter)
        val expectedPages = 42
        val repositoryResult = expectedCharacters to expectedPages
        coEvery { repository.fetchAllCharacters(page) } coAnswers {
            kotlinx.coroutines.delay(100)
            repositoryResult
        }

        val job1 = async { getAllCharactersUseCase.fetchAllCharacters(page) }
        val job2 = async { getAllCharactersUseCase.fetchAllCharacters(page) }

        val result1 = job1.await()
        val result2 = job2.await()

        assertTrue(result1.isSuccess && result2.isSuccess)
        assertEquals(result1.getOrNull(), result2.getOrNull())
        coVerify(exactly = 2) { repository.fetchAllCharacters(page) }
    }
}