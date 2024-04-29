package com.example.myunittest

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun before(){
        cuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

    @Test
    fun testVolume(){
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth,dummyLength,dummyHeight)
        val volume = mainViewModel.getVolume()
        assertEquals(dummyVolume,volume,0.0001)
    }

    @Test
    fun testCircumference(){
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth,dummyLength,dummyHeight)
        val circumference = mainViewModel.getCircumference()
        assertEquals(dummyCircumference,circumference,0.0001)
    }

    @Test
    fun testSurfaceArea (){
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth,dummyLength,dummyHeight)
        val surfaceArea = mainViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea,surfaceArea,0.0001)
    }

    @Test
    fun testMockVolume(){
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
        val volume = mainViewModel.getVolume()
        verify(cuboidModel).getVolume()
        assertEquals(dummyVolume,volume,0.0001)
    }


    @org.junit.jupiter.api.Test
    fun getCircumference() {
    }

    @org.junit.jupiter.api.Test
    fun getSurfaceArea() {
    }

    @org.junit.jupiter.api.Test
    fun getVolume() {
    }

    @org.junit.jupiter.api.Test
    fun save() {
    }
}