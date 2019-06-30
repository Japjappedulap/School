cmake_minimum_required(VERSION 3.14)
project(untitled C)

set(CMAKE_C_STANDARD 11)

add_executable(untitled
#        pipe.c
#        fifo_opening.c
#        pipe_opening.c
        fifo_examples.c
        )