#include "test_common.h"

#include <QtTest>
#include <string>

#include "nami/util/log_util.h"

namespace nami {

TestCommon::TestCommon() {
    this->log = LogUtil::getLogger("test_common");
}

bool TestCommon::testCommonOne() {
    SPDLOG_LOGGER_DEBUG(this->log, fmt::format("test_common one start"));
    std::string hello = "hello";
    SPDLOG_LOGGER_DEBUG(this->log, fmt::format("hello {}", hello));
    SPDLOG_LOGGER_DEBUG(this->log, fmt::format("test_common one end"));
    return true;
}
void TestCommon::testCommonAll() {
    QVERIFY(TestCommon::testCommonOne());
}

TestCommon::~TestCommon() {
    this->log->flush();
}

};  // namespace nami