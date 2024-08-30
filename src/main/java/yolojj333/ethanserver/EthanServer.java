package yolojj333.ethanserver;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EthanServer implements ModInitializer {
    public static final Logger log = LoggerFactory.getLogger("ethan-server-mod");

    public void onInitialize() {
        log.info("ethan's server custom mod initialized");
    }
}
