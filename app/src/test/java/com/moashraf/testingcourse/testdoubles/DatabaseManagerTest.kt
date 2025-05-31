package com.moashraf.testingcourse.testdoubles


import junit.framework.TestCase.assertEquals
import org.junit.Test

class DatabaseManagerTest {

    @Test
    fun `Given database manager when save data return saved data`() {
        val database = DatabaseController()
        val dataBaseManager = DatabaseManager(databaseController = database)

        dataBaseManager.saveData("Mohamed")

        assertEquals("Mohamed", dataBaseManager.retrieveData())
    }

}