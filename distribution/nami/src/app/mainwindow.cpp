#include "nami/app/mainwindow.h"

#include "nami/util/log_util.h"

namespace nami {
MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent) {
    this->log = LogUtil::getLogger("app");
    resize(800, 600);
}

void MainWindow::closeEvent(QCloseEvent *event) {
    SPDLOG_LOGGER_DEBUG(log, fmt::format("nami is closing..."));
    // 如果你想阻止窗口关闭，取消事件
    // event->ignore();

    event->accept();
}

}  // namespace nami