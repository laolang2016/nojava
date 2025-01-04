#include <spdlog/fmt/fmt.h>
#include <spdlog/spdlog.h>

#include <iostream>
#include <memory>

#include "graver/util/log_util.h"

int main() {
    try {
        // 初始化日志系统
        LogUtil::init(spdlog::level::info, "../logs/app.log");

        // 获取日志记录器
        std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");

        // 记录日志消息
        SPDLOG_LOGGER_INFO(log, "Hello Graver");

        // 关闭日志系统
        spdlog::shutdown();
    } catch (const std::exception& e) {
        std::cerr << "Exception caught: " << e.what() << std::endl;
        return 1;
    }
    return 0;
}
