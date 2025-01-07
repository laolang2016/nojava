#pragma once

#include <spdlog/spdlog.h>

#include <QCoreApplication>
#include <QDir>
#include <QObject>
#include <QString>

#include "nami/util/configuration.h"

namespace nami {
#define nami_VERSION_MAJOR_COPY (nami_VERSION_MAJOR)
#define nami_VERSION_MINOR_COPY (nami_VERSION_MINOR)
#define nami_VERSION_PATCH_COPY (nami_VERSION_PATCH)

#define nami_VERSION_STR(R) #R
#define nami_VERSION_STR2(R) nami_VERSION_STR(R)

class AppConfig : public QObject {
    Q_OBJECT

public:
    static AppConfig* getInstance();

    AppConfig();

public:
    QString appVersion() {
        return this->m_app_version;
    }
    [[nodiscard]] int appVersionMajor() const {
        return this->m_app_version_major;
    }
    [[nodiscard]] int appVersionMinor() const {
        return this->m_app_version_major;
    }
    [[nodiscard]] int appVersionPatch() const {
        return this->m_app_version_major;
    }
    QDir appDir() {
        return this->m_app_dir;
    }

    spdlog::level::level_enum logLevel() {
        return this->m_log_level;
    }

private:
    // app 版本信息
    QString m_app_version;
    int     m_app_version_major;
    int     m_app_version_minor;
    int     m_app_version_patch;

    /**
     * @brief 应用程序所在目录
     */
    QDir m_app_dir;

    /**
     * @brief 日志级别
     */
    spdlog::level::level_enum m_log_level;
};
};  // namespace nami