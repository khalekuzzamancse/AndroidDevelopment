#include "kzcse_jni_NativeCode.h"
#include <iostream>

extern "C" {
    JNIEXPORT void JNICALL Java_kzcse_jni_NativeCode_hello(JNIEnv *env, jclass clazz) {
        std::cout << "Hello from C++!" << std::endl;
    }
}