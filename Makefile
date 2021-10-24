GGC_CORE_PATH=./project/ggc-core
GGC_APP_PATH=./project/ggc-app
CLASSPATH=$(CURDIR)/project/po-uilib/po-uilib.jar:$(CURDIR)/project/ggc-app/ggc-app.jar:$(CURDIR)/project/ggc-core/ggc-core.jar

all:
    $(MAKE) $(MFLAGS) -C $(GGC_CORE_PATH)
    $(MAKE) $(MFLAGS) -C $(GGC_APP_PATH)
    CLASSPATH=$(CLASSPATH) java ggc.app.App
clean:
    $(MAKE) $(MFLAGS) -C $(GGC_CORE_PATH) clean
    $(MAKE) $(MFLAGS) -C $(GGC_APP_PATH) clean