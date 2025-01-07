#include "nami/app/mainwindow.h"

#include <qnamespace.h>

#include <QMessageBox>
#include <QPixmap>

#include "nami/util/log_util.h"

namespace nami {
MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent) {
    this->log = LogUtil::getLogger("mainwindow");

    init();

    connect(Pubsub::getInstance(), &Pubsub::signalPublish, this, &MainWindow::subscribe);
}

void MainWindow::closeEvent(QCloseEvent *event) {
    SPDLOG_LOGGER_DEBUG(log, fmt::format("nami is closing..."));
    // 如果你想阻止窗口关闭，取消事件
    // event->ignore();

    event->accept();
}

void MainWindow::init() {
    resize(1140, 780);

    m_toolbar = new QToolBar();
    m_toolbar->setMovable(false);
    addToolBar(Qt::LeftToolBarArea, m_toolbar);

    m_act_user = new UserToolButton();
    m_act_user->setIcon(QPixmap(":icon/user_default_16"));
    m_toolbar->addWidget(m_act_user);

    m_act_todo = new QAction("帮助");
    m_act_todo->setIcon(QPixmap(":icon/todo_16"));
    m_toolbar->addAction(m_act_todo);
}

void MainWindow::subscribe(const NamiTopic &topic, [[maybe_unused]] const QMap<QString, QVariant> &param) {
    QMessageBox::aboutQt(nullptr);
    SPDLOG_LOGGER_DEBUG(log, fmt::format("topic:{}", topic.topic.toStdString()));
}

}  // namespace nami
