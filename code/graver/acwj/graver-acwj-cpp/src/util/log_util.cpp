#include "graver/util/log_util.h"

spdlog::level::level_enum LogUtil::global_level = spdlog::level::info;
std::string               LogUtil::log_file     = "app.log";  // NOLINT

spdlog::level::level_enum LogUtil::getGlobalLevel() {
    return global_level;
}

std::vector<spdlog::sink_ptr> LogUtil::createSinks(const std::string &log_file_name) {
    std::vector<spdlog::sink_ptr> sinks;

    auto sink1 = std::make_shared<spdlog::sinks::stdout_color_sink_mt>();
    sink1->set_level(LogUtil::getGlobalLevel());
    sinks.push_back(sink1);

    auto sink2 = std::make_shared<spdlog::sinks::rotating_file_sink_mt>(log_file_name, 1024 * 1024 * 10, 100, false);
    sink2->set_level(spdlog::level::debug);
    sinks.push_back(sink2);
    return sinks;
}

void LogUtil::createLogger(const std::string &logger_name, spdlog::level::level_enum level) {
    std::string log_file_name = LogUtil::log_file;
    auto        sinks         = LogUtil::createSinks(log_file_name);

    auto logger = std::make_shared<spdlog::logger>(logger_name, begin(sinks), end(sinks));
    logger->set_level(level);
#if 0  // 不带文件名与行号
    logger->set_pattern("%Y-%m-%d %H:%M:%S [%l] [thread %t] [%n] - %v");

    /* 使用方法
    LogUtil::init(spdlog::level::info, "../logs/app.log");
    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");
    log->info("cpp hello");
    */
#endif

#if 1                                                                                  // 带文件名与行号
    logger->set_pattern("%Y-%m-%d %H:%M:%S [%l] [thread %t] [%n] - %s:%# - %! - %v");  // 文件名
    // logger->set_pattern("%Y-%m-%d %H:%M:%S [%l] [thread %t] [%n] - %g:%# - %! - %v");  // 文件全路径

    /* 使用示例
    LogUtil::init(spdlog::level::info, "../logs/app.log");
    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");
    SPDLOG_LOGGER_INFO(log, "cpp hello");
    */
#endif

    spdlog::register_logger(logger);
}

std::shared_ptr<spdlog::logger> LogUtil::getLogger(const std::string &logger_name, spdlog::level::level_enum level) {
    auto logger = spdlog::get(logger_name);
    if (!logger) {  // looger指向为空
        createLogger(logger_name, level);
        logger = spdlog::get(logger_name);
    }
    return logger;
}

void LogUtil::init(spdlog::level::level_enum level, std::string log_file) {
    LogUtil::global_level = level;
    LogUtil::log_file     = log_file;  // NOLINT

    spdlog::flush_every(std::chrono::seconds(1));
    spdlog::flush_on(spdlog::level::debug);
}
