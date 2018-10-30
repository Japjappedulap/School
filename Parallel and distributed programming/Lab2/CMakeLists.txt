cmake_minimum_required(VERSION 3.10)
project(Lab2)

set(CMAKE_CXX_STANDARD 14)
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -pthread")

message(STATUS "start running cmake...")

find_package(Boost 1.57.0 COMPONENTS thread system filesystem REQUIRED)

if(Boost_FOUND)

    message(STATUS "Boost_INCLUDE_DIRS: ${Boost_INCLUDE_DIRS}")
    message(STATUS "Boost_LIBRARIES: ${Boost_LIBRARIES}")
    message(STATUS "Boost_VERSION: ${Boost_VERSION}")

    include_directories(${Boost_INCLUDE_DIRS})

endif()

add_executable(Lab2asyncprod main.cpp utils.cpp Matrix.cpp)

if(Boost_FOUND)

    target_link_libraries(Lab2asyncprod ${Boost_LIBRARIES})

endif()
