package unknown.main.flowtracker.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import org.lwjgl.glfw.GLFW;
import unknown.main.flowtracker.FlowstateConfig;

public class HudDragController {
    private final FlowstateConfig config;
    private FlowstateHud hud;
    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    public HudDragController(FlowstateConfig config) {
        this.config = config;
    }

    public void setHud(FlowstateHud hud) {
        this.hud = hud;
    }

    public void tick(Minecraft client) {
        if (client.gui.screen() instanceof ChatScreen) {
            double mouseX = client.mouseHandler.getScaledXPos(client.getWindow());
            double mouseY = client.mouseHandler.getScaledYPos(client.getWindow());
            long handle = client.getWindow().handle();
            boolean pressed = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;

            if (pressed && !dragging) {
                int hudW = hud.getLastWidth();
                int hudH = hud.getLastHeight();
                if (mouseX >= config.hudX && mouseX <= config.hudX + hudW
                        && mouseY >= config.hudY && mouseY <= config.hudY + hudH) {
                    dragging = true;
                    dragOffsetX = (int) mouseX - config.hudX;
                    dragOffsetY = (int) mouseY - config.hudY;
                }
            } else if (!pressed && dragging) {
                dragging = false;
                config.save();
            }
        } else {
            dragging = false;
        }
    }

    public void updatePosition(Minecraft client) {
        if (dragging && client.gui.screen() instanceof ChatScreen) {
            double mouseX = client.mouseHandler.getScaledXPos(client.getWindow());
            double mouseY = client.mouseHandler.getScaledYPos(client.getWindow());
            config.hudX = Math.max(0, (int) mouseX - dragOffsetX);
            config.hudY = Math.max(0, (int) mouseY - dragOffsetY);
        }
    }
}
