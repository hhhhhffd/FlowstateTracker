package unknown.main.flowtracker.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;
import unknown.main.flowtracker.FlowstateConfig;
import unknown.main.flowtracker.FlowstateTracker;

public class FlowtrackerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FlowstateConfig config = FlowstateConfig.load();
        FlowstateTracker tracker = new FlowstateTracker();
        HudDragController dragController = new HudDragController(config);
        FlowstateHud hud = new FlowstateHud(tracker, config, dragController);
        dragController.setHud(hud);

        HudElementRegistry.addLast(Identifier.parse("flowtracker:hud"), hud);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) {
                return;
            }
            if (client.player.isDeadOrDying()) {
                tracker.hardReset();
            }
            tracker.tick(client.player.getMainHandItem(), System.currentTimeMillis());
            dragController.tick(client);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            tracker.hardReset();
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            config.save();
        });
    }
}
