#!/bin/sh

# Set the iOS deployment target
IOS_DEPLOYMENT_TARGET=10.0

# Directories
BUILD_DIR=build
LIB_DIR=$BUILD_DIR/lib

# Ensure the build and library directories exist
mkdir -p $LIB_DIR

# Source files for `libnativeApp`
SRC_FILES="main.cpp" # Add all relevant source files here

# Compile for arm64 architecture (iOS device)
for SRC in $SRC_FILES; do
    OBJ=$BUILD_DIR/$(basename $SRC .cpp)_arm64.o
    clang++ -c -arch arm64 -std=c++11 -isysroot $(xcrun --sdk iphoneos --show-sdk-path) \
        -mios-version-min=$IOS_DEPLOYMENT_TARGET -o $OBJ $SRC
done

# Create static library for arm64
ar rcs $LIB_DIR/libnativeApp_arm64.a $BUILD_DIR/*_arm64.o

# Compile for x86_64 architecture (iOS simulator)
for SRC in $SRC_FILES; do
    OBJ=$BUILD_DIR/$(basename $SRC .cpp)_x86_64.o
    clang++ -c -arch x86_64 -std=c++11 -isysroot $(xcrun --sdk iphonesimulator --show-sdk-path) \
        -mios-version-min=$IOS_DEPLOYMENT_TARGET -o $OBJ $SRC
done

# Create static library for x86_64
ar rcs $LIB_DIR/libnativeApp_x86_64.a $BUILD_DIR/*_x86_64.o

# Combine static libraries into a universal library
lipo -create -output $LIB_DIR/libnativeApp.a $LIB_DIR/libnativeApp_arm64.a $LIB_DIR/libnativeApp_x86_64.a

# Clean up intermediate files (optional)
rm $BUILD_DIR/*_arm64.o $BUILD_DIR/*_x86_64.o $LIB_DIR/*_arm64.a $LIB_DIR/*_x86_64.a
