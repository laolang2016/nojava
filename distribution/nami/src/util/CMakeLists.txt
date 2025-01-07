aux_source_directory(. nami_LIB_UTIL_SOURCES)
list(APPEND nami_LIB_UTIL_SOURCES
    ${CMAKE_SOURCE_DIR}/include/nami/util/app_config.h
    ${CMAKE_CURRENT_SOURCE_DIR}/app_config.cpp
    ${CMAKE_SOURCE_DIR}/include/nami/util/configuration.h
    ${CMAKE_SOURCE_DIR}/include/nami/util/log_util.h
    ${CMAKE_CURRENT_SOURCE_DIR}/log_util.cpp
    ${CMAKE_SOURCE_DIR}/include/nami/util/pubsub.h
    ${CMAKE_CURRENT_SOURCE_DIR}/pubsub.cpp
)

if(WIN32)
    add_compile_options(-DNAMI_UTIL_LIBRARY_EXPORT)
endif()

qt_add_library(${nami_lib_util_lib_name} SHARED ${nami_LIB_UTIL_SOURCES})

target_link_libraries(${nami_lib_util_lib_name} PUBLIC
    Qt${QT_VERSION_MAJOR}::Core
)

if(UNIX)
    set_target_properties(${nami_lib_util_lib_name} PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${lib_dir})
endif()

if(WIN32)
    # 构建后动作
    add_custom_command(TARGET ${nami_lib_util_lib_name} POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E echo "===> nami util library path: $<TARGET_FILE:${nami_lib_util_lib_name}>"
        COMMAND ${CMAKE_COMMAND} -E copy $<TARGET_FILE:${nami_lib_util_lib_name}> ${bin_dir}

        COMMAND ${CMAKE_COMMAND} -E copy $<TARGET_FILE:${nami_lib_util_lib_name}> ${dist_dir}/test/
    )
endif()
