package koeki.core.server.Moderation.Roles.UpdateRole;

import koeki.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UpdateRole extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        String content = event.getMessage().getContentRaw();
        if(content.startsWith("k@updaterole")){
            if(event.getMember().hasPermission(Permission.MANAGE_ROLES)){
                String command = content.substring(0, 11);
                Role RoleUpdate = jda.getRoleById("0");
                if(content.contains("<@&")){
                    RoleUpdate = jda.getRoleById(content.substring(16, 34));
                }else{
                    RoleUpdate = jda.getRoleById(content.substring(13, 31));
                }
                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle("Role Update: " + RoleUpdate.getName());
//                RoleUpdate.getManager().givePermissions(Permission.);
                event.getChannel().sendMessage(eb.build()) .complete();
                return;
            }else{
                event.getChannel().sendMessage("You don't have enough Permissions!").complete();
                return;
            }
        }
    }
}
