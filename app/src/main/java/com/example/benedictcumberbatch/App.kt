package com.example.benedictcumberbatch

import android.app.Application
import com.example.benedictcumberbatch.di.AppContainer
import com.example.benedictcumberbatch.di.DefaultAppContainer

interface AppProvider { val container: AppContainer }

class App : Application(), AppProvider {
    override val container: AppContainer by lazy { DefaultAppContainer() }
}