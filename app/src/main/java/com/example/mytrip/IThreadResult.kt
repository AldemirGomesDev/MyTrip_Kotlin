package com.example.mytrip

interface IThreadResult<T> {
    fun onResult(arg: T)
}