#include "../include/kzcse_jni_register_RegisterNativeBasic.h"
#include <jni.h>
#include "calculator_adapter.h"
#include "greetings.h"
void register_method_hello(JNIEnv* env, jclass clazz);
void register_method_welcome(JNIEnv* env, jclass clazz);

JNIEXPORT void JNICALL Java_kzcse_jni_register_NativeUtils_registerNative(JNIEnv* env, jclass clazz) {
   register_method_hello(env,clazz);
   register_method_welcome(env,clazz);

JNINativeMethod methods[] = {
        { const_cast<char*>("add"),  const_cast<char*>("(DD)D"), (void*)add_from_cpp },
        { const_cast<char*>("sub"),  const_cast<char*>("(DD)D"), (void*)sub_from_cpp },
        { const_cast<char*>("mult"), const_cast<char*>("(DD)D"), (void*)mult_from_cpp },
        { const_cast<char*>("div"),  const_cast<char*>("(DD)D"), (void*)div_from_cpp }
};
  jclass clazz2 = env->FindClass("kzcse/jni/Calculator");
  env->RegisterNatives(clazz2, methods, sizeof(methods) / sizeof(methods[0]));
}

void register_method_hello(JNIEnv* env, jclass clazz){
    JNINativeMethod method;
    method.name=(char*) "hello"; //Java method name
    method.signature=(char*) "()V"; //Able to get from generate header or byte code
    method.fnPtr=(void*)hello_from_cpp;
    jclass clazz2 = env->FindClass("kzcse/jni/register/RegisterNativeBasic");
    env->RegisterNatives(clazz2, &method, 1);
}


void register_method_welcome(JNIEnv* env, jclass clazz){
    JNINativeMethod methods[] = {
            { const_cast<char*>("welcome"), const_cast<char*>("()V"), (void*)welcome }
    };
    jclass clazz2 = env->FindClass("kzcse/jni/register/RegisterNativeBasic");
    env->RegisterNatives(clazz2, methods, sizeof(methods) / sizeof(methods[0]));
}