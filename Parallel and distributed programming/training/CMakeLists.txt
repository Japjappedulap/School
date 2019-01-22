cmake_minimum_required(VERSION 3.13)
project(training)

SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -pthread")
set(CMAKE_CXX_STANDARD 14)

add_executable(training main.cpp util.hpp)