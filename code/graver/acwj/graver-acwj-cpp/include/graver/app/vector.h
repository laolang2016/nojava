#pragma once

#include <spdlog/spdlog.h>

#include <memory>

class Vector2 {
public:
    Vector2() = default;
    Vector2(const Vector2& v);
    Vector2(int x, int y);
    ~Vector2() = default;

private:
    void init(const Vector2& v);
    void init(int x = 0, int y = 0);

public:
    void print() const;

    void add(Vector2& v);

private:
    int x{0};
    int y{0};

public:
    void       setX(int x);
    const int& getX() const;
    void       setY(int y);
    const int& getY() const;

private:
    std::shared_ptr<spdlog::logger> log;
};
