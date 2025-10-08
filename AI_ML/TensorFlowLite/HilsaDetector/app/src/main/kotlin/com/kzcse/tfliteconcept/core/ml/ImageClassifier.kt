package com.kzcse.tfliteconcept.core.ml

import android.graphics.Bitmap

interface ImageClassifier {
    fun classifyOrThrow(image: Bitmap, numClasses: Int): List<Float>
}
