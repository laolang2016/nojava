#include <spdlog/fmt/fmt.h>
#include <spdlog/spdlog.h>

#include <argparse/argparse.hpp>
#include <cstdlib>
#include <memory>

#include "graver/util/log_util.h"

/**
 * @brief 初始化日志
 * 
 */
void init_log();

/**
 * @brief 初始化命令行参数
 * 
 */
void init_argparse(int argc, char* argv[]);

int main(int argc, char* argv[]) {
    init_log();
    // 获取日志记录器
    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");
    // 记录日志消息
    SPDLOG_LOGGER_INFO(log, "Hello Graver");

    init_argparse(argc, argv);

    // 关闭日志系统
    spdlog::shutdown();
    return 0;
}

void init_log() {
    // 初始化日志系统
    LogUtil::init(spdlog::level::info, "../logs/app.log");
}

void init_argparse(int argc, char* argv[]) {
    argparse::ArgumentParser program("graver", "0.0.1");

    program.add_argument("-i", "--input").nargs(argparse::nargs_pattern::at_least_one).help("源文件");
    program.add_argument("-l", "--lex").help("打印词法分析结果");

    try {
        program.parse_args(argc, argv);
    } catch (const std::exception& err) {
        std::cerr << err.what() << std::endl;
        std::cerr << program;
        std::exit(EXIT_FAILURE);
    }

    if (1 == argc) {
        std::cout << program;
    }
}