#pragma once

#ifdef _MSC_VER
    #ifdef JANNA_LIB_UTIL_EXPORT
        #define JANNA_LIB_UTIL __declspec(dllexport)
    #else
        #define JANNA_LIB_UTIL __declspec(dllimport)
    #endif
#else
    #define JANNA_LIB_UTIL
#endif
