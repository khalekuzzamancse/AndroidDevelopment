package com.kzcse.tfliteconcept.core.ml

import android.content.Context
import com.kzcse.tfliteconcept.core.ml.impl.ImageClassifierImpl

object ClassifierFactory {
    fun createImageClassifier(context: Context): ImageClassifier{
         return ImageClassifierImpl(context,modelPath = "model.tflite")
    }
}

