package unknown.main.flowtracker;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FlowstateTracker {
    private static final long TIMEOUT_MS = 10000;

    private int lastCompact;
    private int progress;
    private long lastMineTime;
    private Item lastItem;
    private boolean active;
    private boolean changed;

    public FlowstateTracker() {
        hardReset();
    }

    public void tick(ItemStack stack, long nowMillis) {
        if (stack == null || stack.isEmpty()) {
            if (active) hardReset();
            return;
        }

        Item currentItem = stack.getItem();
        if (currentItem != lastItem) {
            hardReset();
            lastItem = currentItem;
        }

        int compact = FlowstateParser.parseCompactBlocks(stack);
        if (compact < 0) {
            if (active) hardReset();
            return;
        }

        if (lastCompact < 0) {
            lastCompact = compact;
            progress = 0;
            active = true;
            changed = true;
            lastMineTime = nowMillis;
            return;
        }

        if (compact < lastCompact) {
            hardReset();
            lastItem = currentItem;
            return;
        }

        int delta = compact - lastCompact;
        lastCompact = compact;

        if (delta > 0) {
            progress += delta;
            if (progress > FlowstateLimits.MAX_BLOCKS) {
                progress = FlowstateLimits.MAX_BLOCKS;
            }
            lastMineTime = nowMillis;
            active = true;
            changed = true;
        }

        if (active && progress > 0 && (nowMillis - lastMineTime) > TIMEOUT_MS) {
            progress = 0;
            changed = true;
        }
    }

    public void hardReset() {
        progress = 0;
        active = false;
        changed = true;
        lastCompact = -1;
        lastItem = null;
        lastMineTime = 0;
    }

    public long getMillisSinceLastMine(long nowMillis) {
        if (lastMineTime == 0) return 0;
        return nowMillis - lastMineTime;
    }

    public boolean consumeChanged() {
        boolean c = changed;
        changed = false;
        return c;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxBlocks() {
        return FlowstateLimits.MAX_BLOCKS;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isComplete() {
        return progress >= FlowstateLimits.MAX_BLOCKS;
    }
}
