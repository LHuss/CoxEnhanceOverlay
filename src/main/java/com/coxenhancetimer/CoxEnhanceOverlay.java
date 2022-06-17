package com.coxenhancetimer;

import net.runelite.client.ui.overlay.components.LineComponent;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.Overlay;

public class CoxEnhanceOverlay extends Overlay
{
    private final CoxEnhanceTimerPlugin plugin;
    private final CoxEnhanceTimerConfig config;
    private final PanelComponent panelComponent;

    @Inject
    private CoxEnhanceOverlay(final CoxEnhanceTimerPlugin plugin, final CoxEnhanceTimerConfig config)
    {
        this.panelComponent = new PanelComponent();
        this.plugin = plugin;
        this.config = config;
        this.setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.setPriority(OverlayPriority.LOW);
        this.setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    public Dimension render(final Graphics2D graphics)
    {
        if (this.config.showPrayerEnhance() && this.plugin.prayerEnhanceActive)
        {
            if (!this.config.onlyInVesp() || (this.config.onlyInVesp() && this.plugin.isVespAlive))
            {
                this.panelComponent.getChildren().clear();
                this.panelComponent.setBackgroundColor(this.config.overlayBackgroundColor());
                this.panelComponent.setPreferredSize(new Dimension(this.config.overlayWidth(), 0));
                this.panelComponent.getChildren().add(LineComponent.builder().left(Integer.toString(this.plugin.prayerEnhanceTicks)).build());
                return this.panelComponent.render(graphics);
            }
        }
        return null;
    }
}
