package com.uitest.feature._core.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
interface LoadingAndFeedbackController {
    val isLoading: StateFlow<Boolean>
    fun startLoading()
    fun stopLoading()
    fun updateFeedback(message: String)
    companion object{
        fun create():LoadingAndFeedbackController=LoadingAndFeedbackControllerImpl()
    }
}
class LoadingAndFeedbackControllerImpl:LoadingAndFeedbackController{
    private val scope= CoroutineScope(Dispatchers.Default)
    private val _isLoading= MutableStateFlow(false)
    private val _feedbackMessage=MutableStateFlow<String?>(null)
    override val isLoading=_isLoading.asStateFlow()
    override  fun startLoading()=_isLoading.update { true }
    override fun stopLoading()=_isLoading.update { false }
    override fun updateFeedback(message:String){
        scope.launch {
            _feedbackMessage.update { message }
            delay(5_000)
            _feedbackMessage.update { null }
        }
    }
}