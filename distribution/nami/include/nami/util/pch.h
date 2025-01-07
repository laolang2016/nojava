#pragma once

#include <QSysInfo>

#ifdef Q_OS_WIN
    #ifdef NAMI_UTIL_LIBRARY_EXPORT
        #define NAMI_LIB_UTIL __declspec(dllexport)
    #else
        #define NAMI_LIB_UTIL __declspec(dllimport)
    #endif
#else
    #define NAMI_LIB_UTIL
#endif
