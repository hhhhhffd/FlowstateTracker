package unknown.main.flowtracker.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import unknown.main.flowtracker.FlowstateConfig;
import unknown.main.flowtracker.FlowstateTracker;

public class FlowstateHud implements HudElement {
    private final FlowstateTracker tracker;
    private final FlowstateConfig config;
    private final HudDragController dragController;
    private int lastWidth;
    private int lastHeight;

    private static final long TIMEOUT_MS = 10000;

    public FlowstateHud(FlowstateTracker tracker, FlowstateConfig config, HudDragController dragController) {
        this.tracker = tracker;
        this.config = config;
        this.dragController = dragController;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, DeltaTracker tracker) {
        Minecraft client = Minecraft.getInstance();
        dragController.updatePosition(client);
        Font font = client.font;

        int progress = this.tracker.getProgress();
        int max = this.tracker.getMaxBlocks();
        int color = this.tracker.isComplete() ? 0xFF55FF55 : 0xFFFFFFFF;

        long now = System.currentTimeMillis();
        long elapsed = this.tracker.getMillisSinceLastMine(now);
        float remaining = Math.max(0, TIMEOUT_MS / 1000f - elapsed / 1000f);

        String line1 = "Flowstate";
        String line2 = progress + " / " + max;
        String line3 = String.format("%.1fs", remaining);

        int x = config.hudX;
        int y = config.hudY;
        int lineH = font.lineHeight + 2;

        extractor.text(font, Component.literal(line1), x, y, color, true);
        extractor.text(font, Component.literal(line2), x, y + lineH, color, true);
        extractor.text(font, Component.literal(line3), x, y + lineH * 2, color, true);

        lastWidth = Math.max(
                Math.max(font.width(line1), font.width(line2)),
                font.width(line3)
        );
        lastHeight = lineH * 3;
    }

    public int getLastWidth() {
        return lastWidth;
    }

    public int getLastHeight() {
        return lastHeight;
    }
}
