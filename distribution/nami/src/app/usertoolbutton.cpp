#include "nami/app/usertoolbutton.h"

#include <spdlog/fmt/fmt.h>

#include <QCursor>
#include <QPixmap>

#include "nami/util/log_util.h"
#include "nami/util/pubsub.h"

namespace nami {

UserToolButton::UserToolButton(QWidget *parent) : QToolButton(parent) {
    this->log = LogUtil::getLogger("usertoolbutton");

    m_menu = new QMenu(this);
    // m_menu->setObjectName("menu");

    m_act_setting = m_menu->addAction("设置");
    m_act_setting->setObjectName("act_setting");
    m_act_setting->setIcon(QPixmap(":icon/setting_16"));

    m_act_statistics = m_menu->addAction("统计");
    m_act_statistics->setObjectName("act_statistics");
    m_act_statistics->setIcon(QPixmap(":icon/statistics_16"));

    m_act_exit = m_menu->addAction("退出");
    m_act_exit->setObjectName("act_exit");
    m_act_exit->setIcon(QPixmap(":icon/exit_16"));

    connect(this, &QToolButton::clicked, this, &UserToolButton::on_menu_clicked);

    QMetaObject::connectSlotsByName(this);
}

void UserToolButton::on_menu_clicked() {
    m_menu->exec(QCursor::pos());
}

void UserToolButton::on_act_setting_triggered() {
    SPDLOG_LOGGER_DEBUG(log, fmt::format("act setting print"));
    NamiTopic topic;
    topic.source = "nami";
    topic.topic  = "open_setting_dialog";
    QMap<QString, QVariant> param;
    param.insert("msg", "hello janna");
    Pubsub::getInstance()->publish(topic, param);
}
void UserToolButton::on_act_statistics_triggered() {
    SPDLOG_LOGGER_DEBUG(log, fmt::format("act statistics print"));
    NamiTopic topic;
    topic.source = "nami";
    topic.topic  = "open_statistics_dialog";
    QMap<QString, QVariant> param;
    param.insert("msg", "hello janna");
    Pubsub::getInstance()->publish(topic, param);
}
void UserToolButton::on_act_exit_triggered() {
    SPDLOG_LOGGER_DEBUG(log, fmt::format("act exit print"));
    NamiTopic topic;
    topic.source = "nami";
    topic.topic  = "exit";
    QMap<QString, QVariant> param;
    param.insert("msg", "hello janna");
    Pubsub::getInstance()->publish(topic, param);
}

};  // namespace nami
