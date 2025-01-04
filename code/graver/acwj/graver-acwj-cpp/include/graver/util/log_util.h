#pragma once

#include <spdlog/sinks/basic_file_sink.h>
#include <spdlog/sinks/daily_file_sink.h>
#include <spdlog/sinks/rotating_file_sink.h>
#include <spdlog/sinks/stdout_color_sinks.h>
#include <spdlog/sinks/stdout_sinks.h>
#include <spdlog/spdlog.h>

#include <string>
#include <vector>

class LogUtil {
public:
    static void                          init(spdlog::level::level_enum level = spdlog::level::debug, std::string log_file = "app.log");
    static spdlog::level::level_enum     getGlobalLevel();
    static std::vector<spdlog::sink_ptr> createSinks(const std::string &log_file_name);
    static void createLogger(const std::string &logger_name, spdlog::level::level_enum level = spdlog::level::debug);
    static std::shared_ptr<spdlog::logger> getLogger(const std::string        &logger_name,
                                                     spdlog::level::level_enum level = spdlog::level::debug);

private:
    static spdlog::level::level_enum global_level;
    static std::string               log_file;
};
