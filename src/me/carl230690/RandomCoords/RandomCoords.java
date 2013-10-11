 package me.carl230690.RandomCoords;
 
 import java.util.Random;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Location;
 import org.bukkit.Material;
 import org.bukkit.block.Sign;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.Listener;
 import org.bukkit.event.block.Action;
 import org.bukkit.event.block.SignChangeEvent;
 import org.bukkit.event.player.PlayerInteractEvent;
 import org.bukkit.plugin.java.JavaPlugin;
 
 public class RandomCoords extends JavaPlugin
   implements Listener
 {
	 
	 double x;
	 double z;
	 double y = 0;
   public void onEnable()
   {
     Bukkit.getPluginManager().registerEvents(this, this);
     getConfig().options().copyDefaults(true);
     saveConfig();
   }
 
   public void onDisable()
   {
   }
 
   @EventHandler
   public void PlayerInteractEvent(PlayerInteractEvent e)
   {
     Player player = e.getPlayer();
     if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (
       (e.getClickedBlock().getType() == Material.SIGN) || 
       (e.getClickedBlock().getType() == Material.SIGN_POST) || 
       (e.getClickedBlock().getType() == Material.WALL_SIGN))) {
       Sign sign = (Sign)e.getClickedBlock().getState();
       if (sign.getLine(0).equalsIgnoreCase("[RC]"))
       {
         if (player.hasPermission("rc.use")) {
           sign.setLine(0, "[RC]");
           while (y==0) {
             Random r = new Random();
             double range = getConfig().getDouble("MaxCoordinate");
             x = r.nextDouble() * range - 500.0D;
             z = r.nextDouble() * range - 500.0D;
             y = player.getWorld().getHighestBlockYAt((int)x, (int)z);
           }
 
           Location loc = new Location(player.getWorld(), x, y, z);
           player.teleport(loc);
           player.sendMessage(ChatColor.GREEN + "Teleporting To Random Coords");
         }
         else {
           player.sendMessage("You don't have permission to");
         }
       }
     }
   }
 
   @EventHandler
   public void onSignChange(SignChangeEvent event) {
     Player player = event.getPlayer();
     if ((event.getLine(0).equalsIgnoreCase("[RC]")) && 
       (!player.hasPermission("rc.create")))
     {
       event.setLine(0, " ");
       player.sendMessage(ChatColor.RED + "You Do Not Have Permission To Create This Sign");
     }
   }
 
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
   {
     if (cmd.getName().equalsIgnoreCase("rc")) {
       Player p = (Player)sender;
       Random r = new Random();
       double range = getConfig().getDouble("MaxCoordinate");
       double x = r.nextDouble() * range - 500.0D;
       double z = r.nextDouble() * range - 500.0D;
       double y = p.getWorld().getHighestBlockYAt((int)x, (int)z);
       Location loc = new Location(p.getWorld(), x, y, z);
       p.teleport(loc);
       p.sendMessage(ChatColor.GREEN + "Teleporting To Random Coords");
     } else if (cmd.getName().equalsIgnoreCase("rcreload")) {
         Player p = (Player)sender;
       if (p.hasPermission("rc.reload")) {
          reloadConfig();
          p.sendMessage("Config Reloaded");
    } else {
              p.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
          return true;
   }
 }
	return false;}}

