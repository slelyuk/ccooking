package com.slelyuk.android.ccooking.exception

import java.io.IOException

const val AUTH_ERROR = 401

class AppException(message: String?) : RuntimeException(message)

class AuthException(message: String) : IOException(message)