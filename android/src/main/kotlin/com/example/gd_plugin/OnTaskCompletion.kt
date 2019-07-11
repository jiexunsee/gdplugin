package com.example.gd_plugin

interface OnTaskCompletion<T> {
    fun onSuccess(output: T)
    fun onFailure(e: Exception)
}