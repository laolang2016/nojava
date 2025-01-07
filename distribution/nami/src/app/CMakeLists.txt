set(APP_SOURCES
    ${CMAKE_CURRENT_SOURCE_DIR}/main.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/mainwindow.cpp
    ${PROJECT_SOURCE_DIR}/include/nami/app/mainwindow.h
)

qt_add_executable(nami
    MANUAL_FINALIZATION
    ${APP_SOURCES}
)

target_link_libraries(nami PRIVATE
    ${nami_lib_util_lib_name}
    Qt${QT_VERSION_MAJOR}::Widgets
)

set_target_properties(${PROJECT_NAME} PROPERTIES RUNTIME_OUTPUT_DIRECTORY ${bin_dir})

set_target_properties(nami PROPERTIES
    ${BUNDLE_ID_OPTION}
    WIN32_EXECUTABLE TRUE
)

qt_finalize_executable(nami)

if(WIN32)
    # 构建后动作
    add_custom_command(TARGET ${PROJECT_NAME} POST_BUILD

        # 复制 qt 资源文件
        COMMAND ${CMAKE_COMMAND} -E copy ${PROJECT_SOURCE_DIR}/resources_build/win32/qt/Qt6Core.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy ${PROJECT_SOURCE_DIR}/resources_build/win32/qt/Qt6Gui.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy ${PROJECT_SOURCE_DIR}/resources_build/win32/qt/Qt6Widgets.dll $<TARGET_FILE_DIR:${PROJECT_NAME}>/
        COMMAND ${CMAKE_COMMAND} -E copy_directory ${PROJECT_SOURCE_DIR}/resources_build/win32/qt/plugins $<TARGET_FILE_DIR:${PROJECT_NAME}>/plugins
    )
endif()
