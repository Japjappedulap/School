cmake_minimum_required(VERSION 3.12)
project(Lab1)

set(CMAKE_CXX_STANDARD 14)
SET(CMAKE_CXX_FLAGS -pthread)

add_executable(Lab1 main.cpp Account.cpp Account.h SendTransaction.cpp SendTransaction.h utils.cpp utils.h Bank.cpp Bank.h)