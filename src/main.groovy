import com.agent.TairManager
def time_t = System.currentTimeMillis()
def tair = new TairManager();
tair.connect("182.254.145.145", 10086, "namespace", "daily");
def aa = [:]
for(def i = 0; i< 100; i++) {
    aa[i+"k"] = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + i;
}

tair.setObject("A1", aa);
println(tair.getObject("A1"));
println(System.currentTimeMillis()- time_t)