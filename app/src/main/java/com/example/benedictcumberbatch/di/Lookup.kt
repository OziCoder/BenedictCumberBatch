package com.example.benedictcumberbatch.di

import android.content.Context
import com.example.benedictcumberbatch.AppProvider

val Context.appContainer: AppContainer
    get() = (applicationContext as AppProvider).container