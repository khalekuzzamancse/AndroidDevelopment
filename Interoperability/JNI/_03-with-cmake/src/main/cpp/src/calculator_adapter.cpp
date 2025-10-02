#include <jni.h>
#include "calculator.h"
using namespace Calculator;

jdouble JNICALL add_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b) {
return Calculator::add(a, b);
}

jdouble JNICALL sub_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b) {
return Calculator::sub(a, b);
}

jdouble JNICALL mult_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b) {
return Calculator::mult(a, b);
}

jdouble JNICALL div_from_cpp(JNIEnv* env, jobject obj, jdouble a, jdouble b) {
return Calculator::div(a, b);
}