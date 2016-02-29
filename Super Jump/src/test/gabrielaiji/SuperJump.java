package test.gabrielaiji;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class SuperJump extends JavaPlugin implements Listener {
	
	 	 
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
		
	}
	
	String users[] = new String[5];//Ce tableau permet de d'enregistrer les joueurs utilisant la commande "/jump" et pouvant ainsi enlever les FallingDamage seulement des joueurs ayant utiliser cette commande.
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
		if (args.length > 1){//check de l'utilisation de la commande
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "Please enter only one argument for the jump command.");
		}
		
		else if (args.length < 1){//check de l'utilisation de la commande
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "Please enter at least one argument for the jump command.");
		}
		
		else if (cmd.getName().equalsIgnoreCase("jump") && sender instanceof Player){
			Player player = (Player) sender;
			Double height = Double.parseDouble(args[0]);
			
			player.setVelocity(player.getVelocity().add(new Vector(0, height, 0)));
			
			if(height > 1){
				for (int i = 0; i < users.length; i++){
					if (users[i] == null){
						users[i] = sender.getName();//Enregistre les joueurs ayants utiliser la commande "/jump" dont l'hauteur peut causer des FallingDamage.
						break;
					}
				}
			}
			return true;
		}
		return false;
	} 
	
	@EventHandler
	public void onFall(EntityDamageEvent e){
		if(e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL){//check de l'entité pour joueur && de la cause pour Fall.
		
			for (int i = 0; i < users.length; i++){//Recherche du Nom du joueur dans le tableau
				if (users[i] == e.getEntity().getName()){
					
					users[i] = null;//Enleve le nom du joueur du tableau
					e.setCancelled(true);
					break;
				}
			}
		}
	}

}
