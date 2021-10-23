GGC_CORE_PATH=./project/ggc-core
GGC_APP_PATH=./project/ggc-app
CLASSPATH=$(shell pwd)/po-uilib/po-uilib.jar:$(shell pwd)/project/ggc-app/ggc-app.jar:$(shell pwd)/project/ggc-core/ggc-core.jar

all::
    $(MAKE) $(MFLAGS) -C $(GGC_CORE_PATH)
    $(MAKE) $(MFLAGS) -C $(GGC_APP_PATH)
    CLASSPATH=$(CLASSPATH) java ggc.app.App
clean:
    $(MAKE) $(MFLAGS) -C $(GGC_CORE_PATH) clean
    $(MAKE) $(MFLAGS) -C $(GGC_APP_PATH) clean