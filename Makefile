GGC_CORE_PATH=./project/ggc-core
GGC_APP_PATH=./project/ggc-app
CLASSPATH=/Users/guilhermealmeida/Documents/IST/LEIC/2º\ Ano/1º\ Semestre/PO/Project/PROJPO//project/po-uilib/po-uilib.jar:/Users/guilhermealmeida/Documents/IST/LEIC/2º\ Ano/1º\ Semestre/PO/Project/PROJPO//project/ggc-app/ggc-app.jar:/Users/guilhermealmeida/Documents/IST/LEIC/2º\ Ano/1º\ Semestre/PO/Project/PROJPO//project/ggc-core/ggc-core.jar

all:
	$(MAKE) $(MFLAGS) -C $(GGC_CORE_PATH)
	$(MAKE) $(MFLAGS) -C $(GGC_APP_PATH)
	CLASSPATH=$(CLASSPATH) java ggc.app.App
clean:
	$(MAKE) $(MFLAGS) -C $(GGC_CORE_PATH) clean
	$(MAKE) $(MFLAGS) -C $(GGC_APP_PATH) clean