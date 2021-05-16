package com.skuad.talent.base.common

enum class AppErrorCodes(val code: Int) {
    SUCCESS(200),
    INCORRECT_PASSWORD(400),
    TOKEN_EXPIRED(401),
    FORBIDDEN(403),
    USER_NOT_FOUND(404),
    FAILURE(500),
    NO_INTERNET_CONNECTION(1001),
    TOKEN_FAILURE(1002),
}

enum class ErrorResponseType(val value:String){
    WRONG_PASSWORD(":auth.error/wrong-password"),
    USER_NOT_FOUND(":auth.error/user-not-found"),
    USER_GOOGLE_ALREADY_EXIST("auth/user-with-gmail-already-exists")
}

const val SOMETHING_WENT_WRONG_MESSAGE = "Something went wrong"
const val NO_INTERNET_ERROR_MESSAGE = "Failed to execute http call"