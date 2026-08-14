package com.andrei1058.bedwars.metrics;

import cc.arrowstats.metrics.Metrics;
import com.andrei1058.bedwars.BedWars;

public class MetricsManager {

    /**
     * Id of this plugin on ArrowStats.
     */
    private static final int ARROWSTATS_PLUGIN_ID = 10;

    private static Metrics metrics;

    private MetricsManager() {
    }

    public static void initService(BedWars plugin) {
        if (null != metrics) {
            return;
        }
        metrics = new Metrics(plugin, ARROWSTATS_PLUGIN_ID);
    }
}
