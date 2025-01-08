cmake_minimum_required(VERSION 3.16)

if(WIN32)
    set(CMAKE_CXX_COMPILER "D:/program/qt/Tools/mingw1120_64/bin/g++.exe")
    set(WINDEPLOYQT6_EXEC_PATH "D:/program/qt/6.5.3/mingw_64/bin/windeployqt6.exe")
elseif(UNIX)
    set(CMAKE_CXX_COMPILER "/usr/bin/clang++")
endif()

# 语言环境配置 ###########################################################################################################
set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# 项目配置 ##############################################################################################################
project(nami VERSION 0.1 LANGUAGES CXX)

# 构建目录
set(dist_dir ${CMAKE_BINARY_DIR}/dist)

# 二进制文件目录
set(bin_dir ${dist_dir}/bin)

# 启动文件目录
set(sbin_dir ${dist_dir}/sbin)

# 库文件目录
set(lib_dir ${dist_dir}/lib)

# 通用工具类库
set(nami_lib_util_lib_name namiutil)

# qt 库
find_package(QT NAMES Qt6 Qt5 REQUIRED COMPONENTS Widgets)
find_package(Qt${QT_VERSION_MAJOR} REQUIRED COMPONENTS Widgets)

# 编译相关配置 ###########################################################################################################
# 不需要 debug 日志时,注释下面两行
add_compile_options(-DSPDLOG_ACTIVE_LEVEL=SPDLOG_LEVEL_DEBUG)
add_compile_options(-Dnami_LOG_LEVEL=1)

# 项目配置文件
configure_file(nami_configuration.h.in ${PROJECT_SOURCE_DIR}/include/nami/util/configuration.h)
set(QRC_FILES ${PROJECT_SOURCE_DIR}/res.qrc)
set(ICON_FILES ${PROJECT_SOURCE_DIR}/icon.rc)

# 生成 compile_commands.json
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# 包含全局头文件
include_directories(${PROJECT_SOURCE_DIR}/include ${RAPIDJSON_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/third)

# 添加子目录 #############################################################################################################
add_subdirectory(src)

# 启用测试 ##############################################################################################################
enable_testing()
add_subdirectory(test)