#include <jni.h>
#include <stdio.h>

void hello_from_cpp(JNIEnv* env, jobject obj) {
    printf("Hello from C++\n");
}
void  welcome(JNIEnv* env, jobject obj) {
    printf("Welcome from C++, How are you\n");
}
