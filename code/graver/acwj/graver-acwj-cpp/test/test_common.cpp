#include <spdlog/spdlog.h>

#include "doctest/doctest.h"
#include "graver/util/log_util.h"

TEST_SUITE("test_common") {
    TEST_CASE("test_common_one") {
        std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("test_common_one");
        SPDLOG_LOGGER_INFO(log, "test_common_one start...");
        SPDLOG_LOGGER_INFO(log, "哈哈哈哈");
        SPDLOG_LOGGER_INFO(log, "test_common_one end...");
    }
}