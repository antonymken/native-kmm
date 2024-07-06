#ifndef MAIN_HPP
#define MAIN_HPP


#ifdef __ANDROID__
#include <jni.h>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_muiltplatform_project_NativeLoader_greetUserNative(JNIEnv *env, jclass _, jstring username);
}
#else
#ifdef __cplusplus
extern "C" {
#endif
    const char* greetUser(const char* username);
    void freeMemoryNative(const char* ptr);
#ifdef __cplusplus
}
#endif
#endif

#endif // MAIN_HPP