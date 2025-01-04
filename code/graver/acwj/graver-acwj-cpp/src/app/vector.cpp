#include "graver/app/vector.h"

#include <spdlog/spdlog.h>

#include <memory>

#include "graver/util/log_util.h"

Vector2::Vector2(const Vector2& v) {
    this->init(v);
}
Vector2::Vector2(int x, int y) {
    this->init(x, y);
}

void Vector2::init(const Vector2& v) {
    this->init(v.x, v.y);
}
void Vector2::init(int x, int y) {
    this->x = x;
    this->y = y;
    log     = LogUtil::getLogger("Vector2");
    SPDLOG_LOGGER_INFO(log, "init vector2");
}

void Vector2::print() const {
    SPDLOG_LOGGER_INFO(log, "x:{},y:{}", x, y);
}

void Vector2::setX(int x) {
    this->x = x;
}
const int& Vector2::getX() const {
    return this->x;
}
void Vector2::setY(int y) {
    this->y = y;
}
const int& Vector2::getY() const {
    return this->y;
}

void Vector2::add(Vector2& v) {
    this->x += v.x;
    this->y += v.y;
}
