package com.hermanek.moviebrowserdemo.network

class CommonResponseError(message: String?, cause: Throwable?) : Throwable(message, cause) {
}