MAVEN = mvn
APP_NAME = CalculatorApp.jar

BIN_DIR = /usr/local/bin
LIB_DIR=  /usr/local/lib
ARCHITECTURE ?= $(shell uname -m)

ifeq ($(ARCHITECTURE), x86_64)
	MAVEN_FLAGS = -Dsystem.arch=x86_64
else
	MAVEN_FLAGS = -Dsystem.arch=arm
endif

.PHONY: default debug release clean install uninstall

default: release

debug:
	$(MAVEN) compile -Dmaven.compiler.debug=true $(MAVEN_FLAGS)
release:
	$(MAVEN) package -Dmaven.compiler.debug=false $(MAVEN_FLAGS)
clean:
	$(MAVEN) clean

install: release
	install -m 0644 target/*.jar $(BIN_DIR)/$(APP_NAME)
	install -m 0644 target/*.jar $(LIB_DIR)/$(APP_NAME)

	if [ -d "libs" ]; then \
		echo "Installing the additional libraries." \
		install -m 0644 libs/* $(LIB_DIR)/; \
	fi

uninstall: $(BIN_DIR) $(LIB_DIR)
	rm -f $(BIN_DIR)/$(APP_NAME)
	rm -f $(LIB_DIR)/$(APP_NAME)

$(BIN_DIR):
	@test -d $(BIN_DIR) || (echo "BIN Folder does not exist!" && exit 1)
$(LIB_DIR):
	@test -d $(LIB_DIR) || (echo "LIB Folder does not exist!" && exit 1)