package koeki.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class test extends ListenerAdapter {

    private static JDA builder = Main.getBuilder();


    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String content = event.getMessage().getContentRaw();
        String channelID = "734872751583526983";
        if(content.toLowerCase().equals("penis")){
            if(event.getChannel().getId().equals(channelID)){
                Role test1 = event.getGuild().getRoleById("738900449528119356");
                Role test2 = event.getGuild().getRoleById("738900431903785042");
                Member member = event.getMember();
                event.getGuild().addRoleToMember(member, test1).complete();
                event.getGuild().addRoleToMember(member, test2).complete();
                event.getChannel().sendMessage("Roles added!").queue();
                return;
            } else {
                return;
            }
        }
    }
}
