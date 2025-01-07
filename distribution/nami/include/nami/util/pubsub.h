#pragma once

#include <spdlog/logger.h>

#include <QMap>
#include <QObject>
#include <QString>
#include <QVariant>

#include "nami/util/pch.h"

namespace nami {
/**
 * @brief 消息主题
 */
typedef struct {
    /**
    * @brief 主题来源
    */
    QString source;
    /**
     * @brief 主题
     */
    QString topic;
    /**
     * @brief 处理消息后需要发布的消息
     */
    QString publish;
} NamiTopic;

class NAMI_LIB_UTIL Pubsub : public QObject {
    Q_OBJECT

public:
    static Pubsub* getInstance();

    explicit Pubsub();

private:
    std::shared_ptr<spdlog::logger> log;

public:
    void publish(const NamiTopic& topic, const QMap<QString, QVariant>& param);

signals:
    void signalPublish(const NamiTopic& topic, const QMap<QString, QVariant>& param);
};
};  // namespace nami
