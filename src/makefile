##########
####  wangwenbo@alibaba-inc.com
##########

include ./make.inc

WORKSPACE=.
TARGET=com.agent.test.jar
APPNAME=test
TMP=$(WORKSPACE)/build/main.groovy
VERSION=1.0.0.1
$(TMP):clean
	[ -d "./build" ] && echo "has dist dir" || mkdir -p ./build

####all build 
concat:$(TMP)
	echo -e "//verion=$(VERSION) \n" > $(TMP)
	$(call __definedir__,$(WORKSPACE)/com/agent/, $(TMP));
	$(call __definedir__,$(WORKSPACE)/com/agent/handler/, $(TMP));

###repalce 
replace:
	$(call __replaceWindowsImport__ ,$(TMP))
	
clean:
	rm -rf $(WORKSPACE)/build/* 

all:clean concat replace
	cd ./build && /bin/groovy/bin/groovyc main.groovy && rm -f main.groovy
	tar zcf $(TARGET) ./build && rm -rf ./build/* && mv $(TARGET) ./build
	/bin/groovy/bin/groovy upload.groovy $(APPNAME) $(TARGET)
	echo -e "done";
