package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.main.data.MainScreenRepository
import com.devspace.myapplication.main.data.local.MainScreenLocalDataSource
import com.devspace.myapplication.main.data.remote.MainScreenRemoteDataSource
import com.devspace.myapplication.main.data.remote.MainService

class EasyRecipesApplication: Application() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EasyRecipesDataBase::class.java,"database-easy-recipes"
        ).build()
    }

    private val mainService by lazy {
        RetrofitClient.retrofitInstance.create(MainService::class.java)
    }

    private val localDataSource: MainScreenLocalDataSource by lazy {
        MainScreenLocalDataSource(db.getRecipeDao())
    }

    private val remoteDataSource: MainScreenRemoteDataSource by lazy {
        MainScreenRemoteDataSource(mainService)
    }

    val repository: MainScreenRepository by lazy {
        MainScreenRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }

}