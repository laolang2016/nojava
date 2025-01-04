aux_source_directory(. SRCS_MAIN)
add_executable(${PROJECT_NAME} ${SRCS_MAIN})

# 链接动态库
target_link_libraries(${PROJECT_NAME}
    ${lib_util_name}
)

if(UNIX)
    # 设置 rpath
    set_target_properties(${PROJECT_NAME} PROPERTIES INSTALL_RPATH "\${ORIGIN}/../lib")
endif()

# 可执行程序生成目录
set_target_properties(${PROJECT_NAME} PROPERTIES RUNTIME_OUTPUT_DIRECTORY ${bin_dir})
