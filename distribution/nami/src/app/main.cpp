#include <spdlog/fmt/fmt.h>
#include <spdlog/spdlog.h>

#include <QApplication>
#include <QSysInfo>
#include <cstdlib>

#include "nami/app/mainwindow.h"
#include "nami/util/app_config.h"
#include "nami/util/log_util.h"

void exit();

void setWindowOnCenter(QMainWindow* win);

using namespace nami;
int main(int argc, char* argv[]) {
    LogUtil::init(AppConfig::getInstance()->logLevel(), "../logs/app.log");
    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");
    SPDLOG_LOGGER_DEBUG(log, fmt::format("nami is running..."));

    QApplication a(argc, argv);
    MainWindow   w;
    setWindowOnCenter(&w);
    w.show();

    auto result = QApplication::exec();
    SPDLOG_LOGGER_DEBUG(log, fmt::format("nami result code:{}", result));
    log->flush();
    spdlog::shutdown();
    exit();
    return result;
}

void exit() {
#ifdef Q_OS_WIN
    std::string exit_cmd = "taskkill /t /f /PID " + std::to_string(_getpid());
    system(exit_cmd.c_str());
#endif
}

void setWindowOnCenter(QMainWindow* win) {
#if SHOW_WINDOW_IN_PRIMARY_SCREEN
    // 获取主屏幕的指针
    QScreen* primaryScreen = QGuiApplication::primaryScreen();
    // 获取主屏幕的可用工作区几何信息
    QRect availableGeometry = primaryScreen->availableGeometry();
#else
    // 获取当前屏幕的可用工作区几何信息
    QScreen* currentScreen     = QGuiApplication::screenAt(win->pos());
    QRect    availableGeometry = currentScreen->availableGeometry();
#endif
    // 计算窗口居中显示的位置
    int x = availableGeometry.x() + (availableGeometry.width() - win->width()) / 2;
    int y = availableGeometry.y() + (availableGeometry.height() - win->height()) / 2;

    // 移动窗口到居中的位置
    win->move(x, y);
}