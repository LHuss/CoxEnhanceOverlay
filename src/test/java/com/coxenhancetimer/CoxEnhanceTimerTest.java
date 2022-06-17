package com.coxenhancetimer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CoxEnhanceTimerTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CoxEnhanceTimerPlugin.class);
		RuneLite.main(args);
	}
}