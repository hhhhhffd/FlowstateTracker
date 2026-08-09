package unknown.main.flowtracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FlowstateConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("flowtracker.json");

    public int hudX = 10;
    public int hudY = 10;

    public static FlowstateConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                return GSON.fromJson(Files.readString(CONFIG_PATH), FlowstateConfig.class);
            } catch (IOException e) {
                return new FlowstateConfig();
            }
        }
        return new FlowstateConfig();
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException ignored) {}
    }
}
