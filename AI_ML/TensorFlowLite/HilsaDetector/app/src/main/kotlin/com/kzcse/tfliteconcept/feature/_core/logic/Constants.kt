package com.kzcse.tfliteconcept.feature._core.logic

import android.graphics.Bitmap

object Constants {
    const val  EXPECTED_IMAGE_WIDTH=224
    const val EXPECTED_IMAGE_HEIGHT=224
    fun  isImageSizeMatched(bitmap: Bitmap):Boolean{
        return  bitmap.width== EXPECTED_IMAGE_WIDTH &&bitmap.height== EXPECTED_IMAGE_HEIGHT
    }
}