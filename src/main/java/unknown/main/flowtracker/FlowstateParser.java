package unknown.main.flowtracker;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class FlowstateParser {

    public static int parseCompactBlocks(ItemStack stack) {
        CustomData component = stack.get(DataComponents.CUSTOM_DATA);
        if (component == null || component.isEmpty()) {
            return -1;
        }
        var nbt = component.copyTag();
        if (!nbt.contains("compact_blocks")) {
            return -1;
        }
        return nbt.getIntOr("compact_blocks", 0);
    }

    private FlowstateParser() {}
}
