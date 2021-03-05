package com.example.twitchandroidproject.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


// In order to test coroutine functions we need to be able to overwrite dispatches in our tests
// https://craigrussell.io/2019/11/unit-testing-coroutine-suspend-functions-using-testcoroutinedispatcher/

// This class contains all dispatches used in the application to allow overwriting them in tests
interface DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined

}