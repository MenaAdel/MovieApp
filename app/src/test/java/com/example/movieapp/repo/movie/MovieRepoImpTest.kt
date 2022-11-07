package com.example.movieapp.repo.movie

import org.junit.Assert
import org.junit.Test

class MovieRepoImpTest {

    @Test
    fun `canUpdateData() return true when the difference between current and saved time equal or greater than 4`() {
        //arrange
        val currentTime = 6
        val savedTime = 1

        //act
        val result = canUpdateDataUseCase(savedTime, currentTime)

        //assert
        Assert.assertTrue(result)
    }

    @Test
    fun `canUpdateData() return false when the difference between current and saved time smaller than 4`() {
        //arrange
        val currentTime = 3
        val savedTime = 1

        //act
        val result = canUpdateDataUseCase(savedTime, currentTime)

        //assert
        Assert.assertFalse(result)
    }
}