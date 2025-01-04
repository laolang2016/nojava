aux_source_directory(. SRCS_UTIL)

if(MSVC)
    add_compile_options(-DJANNA_LIB_UTIL_EXPORT)
endif()

add_library(${lib_util_name} SHARED ${SRCS_UTIL})

if(UNIX)
    # 设置动态库生成目录
    set_target_properties(${lib_util_name} PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${lib_dir})

    # 设置库版本
    set_target_properties(${lib_util_name} PROPERTIES VERSION 1.0.0 SOVERSION 1)
endif()

if(WIN32)
    add_custom_command(TARGET ${lib_util_name} POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy $<TARGET_FILE:${lib_util_name}> ${bin_dir}/
        COMMAND ${CMAKE_COMMAND} -E copy $<TARGET_FILE:${lib_util_name}> ${bin_test_dir}/
        VERBATIM
    )
endif()
