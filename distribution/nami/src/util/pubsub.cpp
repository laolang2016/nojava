#include "nami/util/pubsub.h"

#include <QMutex>

#include "nami/util/log_util.h"

namespace nami {
Q_GLOBAL_STATIC(Pubsub, pubsub)

Pubsub::Pubsub() {
    this->log = LogUtil::getLogger("pubsub");
}

Pubsub* Pubsub::getInstance() {
    return pubsub();
}

void Pubsub::publish(const NamiTopic& topic, const QMap<QString, QVariant>& param) {
    SPDLOG_LOGGER_DEBUG(log, fmt::format("get msg. topic:{}", topic.topic.toStdString()));
    emit signalPublish(topic, param);
}
};  // namespace nami