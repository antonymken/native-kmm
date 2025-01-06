#ifndef __ANDROID__ //not android
#include "main.hpp"
#include <string>

#ifdef __cplusplus
extern "C" {
#endif
const char* greetUser(const char* username) {
    std::string greeting = "Hello ";
    greeting += username;
    // Return a heap-allocated copy of the greeting
    char *result = new char[greeting.length() + 1];
    strcpy(result, greeting.c_str());
    return result;
}

void freeMemoryNative(const char* ptr) {
    delete[] ptr;
}
#ifdef __cplusplus
}
#endif
#endif //__ANDROID__
