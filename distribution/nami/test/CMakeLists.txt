cmake_minimum_required(VERSION 3.16)

# 语言环境配置 ###########################################################################################################
set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# 项目配置 ##############################################################################################################
project(nami_test VERSION 0.1 LANGUAGES CXX)

# qt 库
find_package(QT NAMES Qt6 Qt5 REQUIRED COMPONENTS Test Widgets)
find_package(Qt${QT_VERSION_MAJOR} REQUIRED COMPONENTS Test Widgets)

# 编译相关配置 ###########################################################################################################
# 不需要 debug 日志时,注释下面两行
add_compile_options(-DSPDLOG_ACTIVE_LEVEL=SPDLOG_LEVEL_DEBUG)
add_compile_options(-Dnami_LOG_LEVEL=1)

# 生成 compile_commands.json
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# 包含全局头文件
include_directories(${PROJECT_SOURCE_DIR}/include ${RAPIDJSON_INCLUDE_DIRS} ${PROJECT_SOURCE_DIR}/third ${CMAKE_CURRENT_SOURCE_DIR})

# 生成测试程序 #############################################################################################################
aux_source_directory(. test_srcs)
qt_standard_project_setup()
qt_add_executable(${PROJECT_NAME}
    MANUAL_FINALIZATION
    ${test_srcs}
)

set_target_properties(${PROJECT_NAME} PROPERTIES RUNTIME_OUTPUT_DIRECTORY ${dist_dir}/test)
target_link_libraries(${PROJECT_NAME} PRIVATE
    ${nami_lib_util_lib_name}
    Qt${QT_VERSION_MAJOR}::Core
    Qt${QT_VERSION_MAJOR}::Gui
    Qt${QT_VERSION_MAJOR}::Test
    Qt${QT_VERSION_MAJOR}::Widgets
)

if(WIN32)
    # 构建后动作
    add_custom_command(TARGET ${PROJECT_NAME} POST_BUILD

        # 复制 qt 资源文件
        COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/resources_build/win32/qt_test/Qt6Core.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/resources_build/win32/qt_test/Qt6Gui.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/resources_build/win32/qt_test/Qt6Widgets.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/resources_build/win32/qt_test/Qt6Test.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy_directory ${CMAKE_SOURCE_DIR}/resources_build/win32/qt_test/plugins $<TARGET_FILE_DIR:${PROJECT_NAME}>/plugins
    )
endif()

# 执行所有测试
# rm -rf build && cmake --preset=ninja-release && cmake --build --preset=ninja-release && ctest --test-dir build/ninja-release
add_test(NAME test_all COMMAND ${PROJECT_NAME})
set_tests_properties(test_all PROPERTIES WORKING_DIRECTORY ${dist_dir}/test)

# test add_custom_command
# rm -rf build && cmake --preset=ninja-release && cmake --build --preset=ninja-release && ctest --test-dir build/ninja-release -R test_common
add_test(NAME test_common COMMAND ${PROJECT_NAME} testCommon)
set_tests_properties(test_common PROPERTIES WORKING_DIRECTORY ${dist_dir}/test)