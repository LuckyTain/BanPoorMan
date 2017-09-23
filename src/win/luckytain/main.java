package win.luckytain;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import net.milkbowl.vault.economy.Economy;
import java.util.Collection;


public class main extends JavaPlugin implements Listener {
    private Economy economy;
    public void setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider =  getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }
    @Override
    public void onEnable() {
        setupEconomy();
        getLogger().info("BanPoorMan插件开启");
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable(){
            @Override
            public void run() {
                Collection<? extends Player> pl = Bukkit.getOnlinePlayers();
                for (Player p : pl){
                    if (economy.getBalance(p) <= 0){
                        if (!p.hasPermission("nobanpoorman") || !p.isOp()){
                            p.kickPlayer("你没钱被Ban啦");
                            p.setBanned(true);
                        }
                    }
                }
            }
        }.runTaskTimer(this,0,20);
    }

    @Override
    public void onDisable() {
        getLogger().info("BanPoorMan插件关闭");
    }
}