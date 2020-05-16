package net.lapismc.afkpluspapi;

import net.lapismc.afkplus.AFKPlus;
import net.lapismc.afkplus.api.AFKPlusPlayerAPI;
import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplus.util.core.placeholder.PlaceholderAPIExpansion;
import org.bukkit.entity.Player;

public class PAPIHook extends PlaceholderAPIExpansion {

    private final AFKPlusPlayerAPI api;
    private final AFKPlusPAPI plugin;

    public PAPIHook(AFKPlusPAPI plugin) {
        super(AFKPlus.getInstance());
        this.plugin = plugin;
        api = new AFKPlusPlayerAPI();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if ("AFKPlus_Status".equalsIgnoreCase(identifier)) {
            return api.getPlayer(player).isAFK() ? plugin.getConfig().getString("status.true")
                    : plugin.getConfig().getString("status.false");
        } else if ("AFKPlus_AFKTime".equalsIgnoreCase(identifier)) {
            AFKPlusPlayer p = api.getPlayer(player);
            Long afkStart = p.getAFKStart();
            if (afkStart == null) {
                return plugin.getConfig().getString("afktime.notafk");
            }
            long millis = System.currentTimeMillis() - afkStart;
            Long minutes = millis / 1000 / 60;
            return minutes + " " + plugin.getConfig().getString("afktime.minutes");
        }
        return null;
    }
}
