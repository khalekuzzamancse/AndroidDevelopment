package com.kzcse.tfliteconcept.feature._core.logic

 class CustomException(override val message: String, val debugMessage: String) :
    Throwable(message = message, cause = Throwable(debugMessage)) {
    override fun toString(): String {
        return "CustomException: $message\nDebug Message: $debugMessage"
    }

}