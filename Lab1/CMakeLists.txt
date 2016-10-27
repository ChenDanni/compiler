cmake_minimum_required(VERSION 3.6)
project(Lab1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp recognize.cpp recognize.h utility.cpp utility.h)
add_executable(Lab1 ${SOURCE_FILES})