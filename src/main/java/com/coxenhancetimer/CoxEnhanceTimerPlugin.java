package com.coxenhancetimer;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Varbits;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@PluginDescriptor(
        name = "Cox Enhance Timer",
        description = "Prayer Enhance Tickup Infobox",
        tags = {"cox", "prayer", "enhance", "vesp", "vespula", "chamber"},
        enabledByDefault = false
)
public class CoxEnhanceTimerPlugin extends Plugin {

    @Inject
    private Client client;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private CoxEnhanceOverlay coxEnhanceOverlay;

    public boolean isVespAlive;
    public int prayerEnhanceTicks;
    public boolean prayerEnhanceActive;
    private final List<Integer> VESPIDS = Arrays.asList(7530, 7531, 7532, 7533);

    public CoxEnhanceTimerPlugin() {
        reset();
    }

    @Subscribe
    private void onChatMessage(final ChatMessage event)
    {
        final String msg = Text.standardize(event.getMessageNode().getValue());
        if (this.client.getVarbitValue(Varbits.IN_RAID) == 1)
        {
            if (msg.equalsIgnoreCase("you drink some of your strong prayer enhance potion."))
            {
                this.prayerEnhanceTicks = 7;
                this.prayerEnhanceActive = true;
            }
            else if (msg.equalsIgnoreCase("your prayer enhance effect has worn off."))
            {
                reset();
            }
        }
    }

    @Subscribe
    private void onGameTick(final GameTick event)
    {
        if (this.prayerEnhanceActive)
        {
            --this.prayerEnhanceTicks;
            if (this.prayerEnhanceTicks <= 0)
            {
                this.prayerEnhanceTicks = 6;
            }
        }
    }

    @Subscribe
    private void onNpcSpawned(final NpcSpawned event)
    {
        if (playerInRaid())
        {
            final NPC npc = event.getNpc();
            if (npc == null) return;

            final int id = npc.getId();
            if (VESPIDS.contains(id))
            {
                this.isVespAlive = true;
            }
        }
    }

    @Subscribe
    private void onNpcDespawned(final NpcDespawned event) {
        if (playerInRaid()) {
            final NPC npc = event.getNpc();
            if (npc == null) return;

            final int id = npc.getId();
            if (VESPIDS.contains(id)) {
                this.isVespAlive = false;
            }
        }
    }

    @Subscribe
    public void onVarbitChanged(final VarbitChanged event) {
        if (!playerInRaid()) {
            reset();
        }
    }

    @Override
    protected void startUp() throws Exception
    {
        reset();
        this.overlayManager.add((Overlay)this.coxEnhanceOverlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        reset();
        this.overlayManager.remove((Overlay)this.coxEnhanceOverlay);
    }

    @Provides
    CoxEnhanceTimerConfig provideConfig(final ConfigManager configManager) {
        return (CoxEnhanceTimerConfig)configManager.getConfig((Class)CoxEnhanceTimerConfig.class);
    }

    private boolean playerInRaid() {
        return this.client.getVarbitValue(Varbits.IN_RAID) == 1;
    }

    private void reset() {
        this.prayerEnhanceTicks = 7;
        this.prayerEnhanceActive = false;
    }
}
