package koeki.core.server.Moderation.InviteModeration.InviteCount;

import koeki.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class InviteCount extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){
        String content = event.getMessage().getContentRaw();

        if(content.equals("k@test")) {
            Guild guild = event.getGuild();
            List<Invite> inv = event.getGuild().retrieveInvites().complete();
            System.out.println(event.getGuild().retrieveInvites().toString());
        }
    }

    public void onGuildMemberJoin (GuildMemberJoinEvent event){


    }
}
