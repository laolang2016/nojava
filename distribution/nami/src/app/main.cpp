#include <spdlog/fmt/fmt.h>
#include <spdlog/spdlog.h>

#include <QApplication>
#include <QSysInfo>
#include <cstdlib>

#include "nami/app/mainwindow.h"
#include "nami/util/app_config.h"
#include "nami/util/log_util.h"

void exit();

using namespace nami;
int main(int argc, char *argv[]) {
    LogUtil::init(AppConfig::getInstance()->logLevel(), "../logs/app.log");
    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");
    SPDLOG_LOGGER_DEBUG(log, fmt::format("nami is running..."));

    QApplication a(argc, argv);
    MainWindow   w;
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