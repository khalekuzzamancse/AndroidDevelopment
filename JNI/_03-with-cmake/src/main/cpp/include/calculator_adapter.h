#pragma once
#include <jni.h>

jdouble JNICALL add_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b);
jdouble JNICALL sub_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b);
jdouble JNICALL mult_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b);
jdouble JNICALL div_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b);
