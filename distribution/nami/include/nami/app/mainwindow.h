#pragma once
#include <spdlog/spdlog.h>

#include <QAction>
#include <QCloseEvent>
#include <QMainWindow>
#include <QToolBar>

#include "nami/app/usertoolbutton.h"

namespace nami {
class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow() override = default;

protected:
    void closeEvent(QCloseEvent *event) override;

private:
    std::shared_ptr<spdlog::logger> log;

private:
    void init();

private:
    QToolBar       *m_toolbar{nullptr};
    UserToolButton *m_act_user{nullptr};
    QAction        *m_act_todo{nullptr};
};
};  // namespace nami
