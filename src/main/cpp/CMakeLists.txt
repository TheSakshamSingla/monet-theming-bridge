cmake_minimum_required(VERSION 3.18.1)
project(monet_bridge)

# Find the Android NDK
find_library(log-lib log)
find_package(jsoncpp REQUIRED)

# Set source files
set(SOURCES
    monet_bridge.cpp
)

# Create the native library
add_library(monet_bridge SHARED ${SOURCES})

# Link against required libraries
target_link_libraries(monet_bridge
    ${log-lib}
    jsoncpp
    android
)

# Set include directories
target_include_directories(monet_bridge PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
)

# Set compiler flags
target_compile_options(monet_bridge PRIVATE
    -Wall
    -Werror
    -std=c++17
)

# Set installation directory
install(TARGETS monet_bridge
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
)
