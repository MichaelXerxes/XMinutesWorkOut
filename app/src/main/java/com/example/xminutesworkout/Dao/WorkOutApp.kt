package com.example.xminutesworkout.Dao

import android.app.Application

class WorkOutApp:Application() {
    val db by lazy {
        HistoryDataBase.getInstance(this)
    }
}