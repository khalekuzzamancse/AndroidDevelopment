package com.kzcse.tfliteconcept.feature._core.logic

import android.util.Log

class Logger {
    companion object{
        private const val ID="CustomerLogger";
        fun on(tag:String,message:String){
            Log.d("$ID::$tag", message)
        }
        fun off(tag:String,message:String){
        }
        fun error(tag:String,error: Throwable){
            Log.d("$ID::$tag","error:$error")
        }
    }
}