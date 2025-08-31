cmake_minimum_required(VERSION 3.10)
project(NativeCode LANGUAGES C CXX)
find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)
# 1st argument=libray name that we want
# 2nd argument=type of library, SHARED=dynamic
add_library(nativecode SHARED
        src/calculator.cpp
        src/calculator_adapter.cpp
        src/greetings.cpp
        src/main.cpp
)
