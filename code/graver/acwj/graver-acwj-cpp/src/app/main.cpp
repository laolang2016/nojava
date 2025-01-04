#include <spdlog/fmt/fmt.h>
#include <spdlog/spdlog.h>

#include <memory>

#include "graver/app/vector.h"
#include "graver/util/log_util.h"

int main() {
    LogUtil::init(spdlog::level::info, "../logs/app.log");
    std::shared_ptr<spdlog::logger> log = LogUtil::getLogger("app");

    Vector2 v1(1, 2);
    Vector2 v2(v1);

    v1.print();
    v2.print();

    v1.add(v2);

    v1.print();

    spdlog::shutdown();
    return 0;
}
