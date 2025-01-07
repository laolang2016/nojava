
#pragma once
#include <spdlog/spdlog.h>

namespace nami {
class TestCommon {
public:
    explicit TestCommon();
    ~TestCommon();

private:
    std::shared_ptr<spdlog::logger> log;

public:
    bool testCommonOne();
    void testCommonAll();
};

}  // namespace nami