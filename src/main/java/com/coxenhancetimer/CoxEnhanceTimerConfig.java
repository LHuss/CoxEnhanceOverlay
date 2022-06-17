package com.coxenhancetimer;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("Cox Enhance Timer")
public interface CoxEnhanceTimerConfig extends Config
{
    @ConfigItem
    (
        keyName = "showPrayerEnhance",
        name = "Show Prayer Enhance",
        description = "Displays the ticks left until prayer enhance regens",
        position = 1
    )
    default boolean showPrayerEnhance()
    {
        return true;
    }

    @ConfigItem
    (
        keyName = "onlyInVesp",
        name = "Only show in Vespula",
        description = "Only shows infobox in Vespula room",
        position = 2
    )
    default boolean onlyInVesp() {
        return false;
    }

    @Alpha
    @ConfigItem
    (
        keyName = "overlayBackgroundColor",
        name = "Overlay Background Color",
        position = 3,
        description = "Background color of the prayer enhance overlay"
    )
    default Color overlayBackgroundColor()
    {
        return new Color(171, 104, 223, 100);
    };

    @ConfigItem
    (
        keyName = "overlayWidth",
        name = "Overlay Width",
        description = "Sets overlay width",
        position = 4
    )
    default int overlayWidth() {
        return 20;
    }
}
