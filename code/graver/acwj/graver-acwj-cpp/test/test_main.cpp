#include <spdlog/spdlog.h>
#define DOCTEST_CONFIG_IMPLEMENT
#include "doctest/doctest.h"
#include "graver/util/log_util.h"

#if defined(_WIN32) || defined(_WIN64)
    #include <cstdlib>
#endif

int main(int argc, char** argv) {
#if defined(_WIN32) || defined(_WIN64)
    std::system("chcp 65001");
#endif

    LogUtil::init(spdlog::level::info, "app.log");

    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("test");
    SPDLOG_LOGGER_INFO(log, "janna test start...");

    doctest::Context context;

    // !!! THIS IS JUST AN EXAMPLE SHOWING HOW DEFAULTS/OVERRIDES ARE SET !!!

    // defaults
    context.addFilter("test-case-exclude", "*math*");  // exclude test cases with "math" in their name
    context.setOption("abort-after", 5);               // stop test execution after 5 failed assertions
    context.setOption("order-by", "name");             // sort the test cases by their name

    context.applyCommandLine(argc, argv);

    // overrides
    context.setOption("no-breaks", true);  // don't break in the debugger when assertions fail

    int res = context.run();  // run

    SPDLOG_LOGGER_INFO(log, "janna test end...");
    spdlog::shutdown();

    if (context.shouldExit())  // important - query flags (and --exit) rely on the user doing this
        return res;            // propagate the result of the tests

    int client_stuff_return_code = 0;
    // your program - if the testing framework is integrated in your production code

    return res + client_stuff_return_code;  // the result from doctest is propagated here as well
}