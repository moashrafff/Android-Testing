package com.moashraf.testingcourse.testdoubles

interface Database{
    fun saveData(data : String)
    fun retrieveData() : String
}


// Fake Impl
class DatabaseController : Database{
    var databaseHolder = ""
    override fun saveData(data: String) {
        databaseHolder = data
    }

    override fun retrieveData() : String = databaseHolder
}

class DatabaseManager(val databaseController: Database) {
    fun saveData(data : String) {
        databaseController.saveData(data)
    }

    fun retrieveData() : String = databaseController.retrieveData()
}