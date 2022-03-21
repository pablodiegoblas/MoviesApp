package com.example.wembleymoviesapp.domain

import com.example.wembleymoviesapp.data.server.ResponseModel

interface GetMoviesServer {
    fun onSuccess(responseServer: ResponseModel)
    fun onError()
}