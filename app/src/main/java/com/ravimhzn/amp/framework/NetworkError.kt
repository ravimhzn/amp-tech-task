package com.ravimhzn.amp.framework

import java.io.Serializable

//Dummy Error class
data class NetworkError(
    var statusCode: String?,
    var statusMessage: String?,
    var error: String? = "",
    var error_description: String? = ""
) : Serializable