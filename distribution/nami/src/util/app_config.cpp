#include "nami/util/app_config.h"

#include <spdlog/fmt/fmt.h>

#include <QMutex>
#include <cstdlib>

namespace nami {

Q_GLOBAL_STATIC(AppConfig, appConfig)

AppConfig::AppConfig() {
    // app 版本信息
    this->m_app_version_major = static_cast<int>(strtol(nami_VERSION_STR2(nami_VERSION_MAJOR), nullptr, 10));
    this->m_app_version_minor = static_cast<int>(strtol(nami_VERSION_STR2(nami_VERSION_MINOR), nullptr, 10));
    this->m_app_version_patch = static_cast<int>(strtol(nami_VERSION_STR2(nami_VERSION_PATCH), nullptr, 10));
    this->m_app_version =
        QString::fromStdString(fmt::format("{}.{}.{}", this->m_app_version_major, this->m_app_version_minor, this->m_app_version_patch));

    // 应用程序所在目录
    this->m_app_dir = QDir(QCoreApplication::applicationDirPath());

    // 日志级别
#ifdef nami_LOG_LEVEL
    // 定义映射关系
    const std::map<int, spdlog::level::level_enum> levelMap{
        {0, spdlog::level::off}, {1, spdlog::level::debug},    {2, spdlog::level::info}, {3, spdlog::level::warn},
        {4, spdlog::level::err}, {5, spdlog::level::critical}, {6, spdlog::level::trace}};

    // 根据宏定义的值获取相应的日志级别
    m_log_level = levelMap.at(nami_LOG_LEVEL);
#else
    m_log_level = spdlog::level::info;
#endif
}

AppConfig* AppConfig::getInstance() {
    return appConfig();
}
};  // namespace nami
