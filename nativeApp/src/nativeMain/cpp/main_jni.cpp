#include "main.hpp"
#include <string>

#ifdef __ANDROID__
extern "C"{
JNIEXPORT jstring JNICALL
Java_com_muiltplatform_project_NativeLoader_greetUserNative(JNIEnv *env, jclass _, jstring username) {
    const char* user = env->GetStringUTFChars(username, 0);
    std::string greeting = "Hello " + std::string(user);
    env->ReleaseStringUTFChars(username, user);
    return env->NewStringUTF(greeting.c_str());
}
}
#endif