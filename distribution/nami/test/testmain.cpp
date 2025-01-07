#include <QtTest>
#include <memory>

#include "nami/util/app_config.h"
#include "nami/util/log_util.h"
#include "test_common.h"

using namespace nami;

class TestMain : public QObject {
    Q_OBJECT
public:
    TestMain() {
        LogUtil::init(AppConfig::getInstance()->logLevel(), "./logs/app.log");
        this->log = LogUtil::getLogger("test");
        SPDLOG_LOGGER_DEBUG(log, fmt::format("nami test is running..."));

        m_test_common = std::make_unique<TestCommon>();
    }
    ~TestMain() override {
        this->log->flush();
        spdlog::shutdown();
    }
private slots:
    void testCommon() {
        this->m_test_common->testCommonAll();
    }

private:
    std::unique_ptr<TestCommon>     m_test_common;
    std::shared_ptr<spdlog::logger> log;
};

QTEST_MAIN(TestMain)
#include "testmain.moc"