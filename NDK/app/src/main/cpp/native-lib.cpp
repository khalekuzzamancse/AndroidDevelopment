#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_kzcse_ndkfundamental_NativeAdapter_greetings(JNIEnv *env, jclass clazz) {
    std::string hello = "Welcome from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_kzcse_ndkfundamental_Hello_hello(JNIEnv *env, jclass clazz, jstring name) {
    const char *nameChars = env->GetStringUTFChars(name, nullptr);
    std::string hello = "Hello " + std::string(nameChars);
    env->ReleaseStringUTFChars(name, nameChars);
    return env->NewStringUTF(hello.c_str());
}