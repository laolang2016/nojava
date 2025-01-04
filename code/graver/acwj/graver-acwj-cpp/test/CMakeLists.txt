# 指定 cmake 最小版本, 意指 cmake 版本低于指定版本则构建过程终止
cmake_minimum_required(VERSION 3.26)

# 语言环境配置 ###########################################################################################################
# 语言版本
set(CMAKE_CXX_STANDARD 17)

# 如果指定的语言版本不受支持, 则构建过程终止
set(CMAKE_CXX_STANDARD_REQUIRED True)

# 只弃用 ISO C++ 标准的编译器标志, 而不使用特定编译器的扩展
set(CMAKE_CXX_EXTENSIONS OFF)

# 项目配置 ##############################################################################################################
project(graver_test CXX)

# 编译相关配置 ###########################################################################################################

# 生成 compile_commands.json
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

aux_source_directory(. test_srcs)
add_executable(${PROJECT_NAME} ${test_srcs})

# 链接动态库
target_link_libraries(${PROJECT_NAME}
    ${lib_util_name}
)

if(UNIX)
    # 设置 rpath
    set_target_properties(${PROJECT_NAME} PROPERTIES INSTALL_RPATH "\${ORIGIN}/../lib")
endif()

# 可执行文件路径
set_target_properties(${PROJECT_NAME} PROPERTIES RUNTIME_OUTPUT_DIRECTORY ${bin_test_dir})

# 测试相关配置 ###########################################################################################################
# 执行所有测试
# ctest --test-dir build
add_test(NAME test_all COMMAND ${PROJECT_NAME})
set_tests_properties(test_all PROPERTIES WORKING_DIRECTORY ${dist_dir}/test)

# test add_custom_command
# ctest --test-dir build -R test_common
add_test(NAME test_common COMMAND ${PROJECT_NAME} -ts=test_common)
set_tests_properties(test_common PROPERTIES WORKING_DIRECTORY ${dist_dir}/test)