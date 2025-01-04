
ifeq ($(shell uname -s), Linux)
	OS = Linux
	PRESET_DEBUG = linux-debug
	PRESET_RELEASE = linux-release
else
	OS = Windows
	PRESET_DEBUG = windows-debug
	PRESET_RELEASE = windows-release
endif

BUILD_DIR_DEBUG = build/$(PRESET_DEBUG)
BUILD_DIR_RELEASE = build/$(PRESET_RELEASE)


ALL: app

t:
	@echo $(PRESET_DEBUG)
	@echo $(PRESET_RELEASE)

app: print config_release build_release

config_debug:
	cmake --preset=$(PRESET_DEBUG)
build_debug:
	cmake --build --preset=$(PRESET_DEBUG)
config_release:
	cmake --preset=$(PRESET_RELEASE)
build_release:
	cmake --build --preset=$(PRESET_RELEASE)

print:
	@echo "OS:$(OS)"

clean_debug:
	-rm -rf $(BUILD_DIR_DEBUG)

clean_release:
	-rm -rf $(BUILD_DIR_RELEASE)

clean_all: clean_debug clean_release

.PHONY: app config_debug build_debug config_release build_release print clean_debug clean_release clean_all