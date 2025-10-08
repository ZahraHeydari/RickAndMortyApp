package com.android.domain.usecase

import com.android.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.coVerify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharacterUseCaseTest {

    private val repository = mockk<CharacterRepository>()
    private val getCharacterUseCase = GetCharacterUseCase(repository)

    @Test
    fun `getDetails success with valid id`() = runTest {
        val characterId = "1"
        val expectedCharacter = rickCharacter
        coEvery { repository.getDetails(characterId) } returns expectedCharacter

        val result = getCharacterUseCase.getDetails(characterId)
        assertEquals(Result.success(expectedCharacter), result)
    }

    @Test
    fun `getDetails failure with non-existent id`() = runTest {
        val characterId = "-1"
        val exception = Exception("Character not found")
        coEvery { repository.getDetails(characterId) } throws exception

        val result = getCharacterUseCase.getDetails(characterId)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getDetails with empty id`() = runTest {
        val characterId = ""
        coEvery { repository.getDetails(characterId) } throws IllegalArgumentException("ID cannot be empty")

        val result = getCharacterUseCase.getDetails(characterId)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `repository returns null character object`() {
        runTest {
            val characterId = "1"
            coEvery { repository.getDetails(characterId) } throws NullPointerException()

            val result = getCharacterUseCase.getDetails(characterId)

            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is NullPointerException)
        }
    }

    @Test
    fun `concurrent calls to getDetails`() = runTest {
        val character1Id = 1
        val character2Id = 2

        coEvery { repository.getDetails(character1Id.toString()) } returns rickCharacter
        coEvery { repository.getDetails(character2Id.toString()) } returns mortyCharacter

        val deferred1 = async { getCharacterUseCase.getDetails(character1Id.toString()) }
        val deferred2 = async { getCharacterUseCase.getDetails(character2Id.toString()) }
        val deferred3 =
            async { getCharacterUseCase.getDetails(character1Id.toString()) }

        val result1 = deferred1.await()
        val result2 = deferred2.await()
        val result3 = deferred3.await()

        assertEquals(Result.success(rickCharacter), result1)
        assertEquals(Result.success(mortyCharacter), result2)
        assertEquals(Result.success(rickCharacter), result3)
    }

    @Test
    fun `cancellation of the coroutine scope`() = runTest {
        val job = launch {
            getCharacterUseCase.getDetails("1")
        }
        job.cancel()
        job.join()
        assertTrue(job.isCancelled)
    }

    @Test
    fun `verify repository method invocation`() = runTest {
        val characterId = "1"
        coEvery { repository.getDetails(characterId) } returns rickCharacter
        getCharacterUseCase.getDetails(characterId)
        coVerify(exactly = 1) { repository.getDetails(characterId) }
    }
}