package com.example.wembleymoviesapp.ui.controllers

import com.example.wembleymoviesapp.data.server.ResponseModel

interface GetMoviesServer {
    fun onSuccess(responseServer: ResponseModel)
    fun onError()
}