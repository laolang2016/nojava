#pragma once

#include <qaction.h>
#include <spdlog/spdlog.h>

#include <QAction>
#include <QMenu>
#include <QToolButton>

namespace nami {
class UserToolButton : public QToolButton {
    Q_OBJECT

public:
    explicit UserToolButton(QWidget *parent = nullptr);
    ~UserToolButton() override = default;

private:
    std::shared_ptr<spdlog::logger> log;

private slots:
    void on_menu_clicked();

    void on_act_setting_triggered();
    void on_act_statistics_triggered();
    void on_act_exit_triggered();

private:
    QMenu   *m_menu{nullptr};
    QAction *m_act_setting;
    QAction *m_act_statistics;
    QAction *m_act_exit;
};
};  // namespace nami