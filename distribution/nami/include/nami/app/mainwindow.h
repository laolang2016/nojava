#pragma once
#include <spdlog/spdlog.h>

#include <QCloseEvent>
#include <QMainWindow>

namespace nami {
class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow() override = default;

protected:
    void closeEvent(QCloseEvent *event) override;

private:
    std::shared_ptr<spdlog::logger> log;
};
};  // namespace nami
