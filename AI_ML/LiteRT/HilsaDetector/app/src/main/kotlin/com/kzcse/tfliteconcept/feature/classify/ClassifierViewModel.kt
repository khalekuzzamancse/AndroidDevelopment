package com.kzcse.tfliteconcept.feature.classify

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.kzcse.tfliteconcept.core.language.Logger
import com.kzcse.tfliteconcept.core.ml.ClassifierFactory
import com.kzcse.tfliteconcept.core.platfrom.BitmapUtils
import com.kzcse.tfliteconcept.feature._core.logic.Constants
import com.kzcse.tfliteconcept.feature._core.logic.CustomException
import com.kzcse.tfliteconcept.feature._core.presentation.GlobalMessenger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @param context safe for memory, not store the context, instead use as one time for initialization
 */
class ClassifierViewModel(context: Context) : ViewModel() {
    private val classifier = ClassifierFactory.createImageClassifier(context)
    private val _result = MutableStateFlow<String?>(null)
    val result = _result.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    private val _resizedImage = MutableStateFlow<Bitmap?>(null)
    val resizedImage = _resizedImage.asStateFlow()
    val isLoading = _isLoading.asStateFlow()
    private fun setLoading(value: Boolean) = _isLoading.update { value }
    private fun setResult(result: String?) {
        _result.update { result }
    }
    fun resize(original: Bitmap): Bitmap {
        val resized = BitmapUtils.scale(
            original,
            Constants.EXPECTED_IMAGE_WIDTH,
            Constants.EXPECTED_IMAGE_HEIGHT
        )
        _resizedImage.value = resized
        return resized
    }

   suspend fun classify(bitmap: Bitmap) {
        try {
            setLoading(true)
            delay(2_000)// Simulate loading,
            val image=resize(bitmap)
            val outputArray = classifier.classifyOrThrow(image = image, numClasses = 5)
            val indexWithMaxValue = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1
            val classLabels =
                arrayOf("Chondona", "Gurta", "Healthy ilish", "Jhatka ilish", "Others")
            if (indexWithMaxValue != -1) {
                setResult(classLabels[indexWithMaxValue])
            } else {
                Logger.off("ClassifyViewModel","output","$outputArray")
                setResult(null)
            }
        } catch (e: Throwable) {
            Logger.off("ClassifyViewModel","output","$e")
            setResult(null)
            if (e is CustomException)
                GlobalMessenger.updateMessage(e)
        } finally {
            setLoading(false)
        }
    }

}