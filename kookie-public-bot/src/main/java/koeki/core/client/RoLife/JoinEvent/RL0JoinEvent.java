package koeki.core.client.RoLife.JoinEvent;

import koeki.core.client.RoLife.RL0Secrets;
import koeki.main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RL0JoinEvent extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();

    public void onGuildMemberJoin (GuildMemberJoinEvent event){
        if(event.getGuild().getId().equals(RL0Secrets.GuildID)){
            User joinedUser = event.getUser();
            TextChannel tc = jda.getTextChannelById("613319325708976140");
            TextChannel verifytc = jda.getTextChannelById("688151758349795395");
            TextChannel rulestc = jda.getTextChannelById("639030302005526528");
            tc.sendMessage("Welcome to " + event.getGuild().getName() + ", " + joinedUser.getAsMention() + "!\n" +
                    "Read the rules in the " + rulestc.getAsMention() + "-channel and"
            + " make sure to verify yourself in the " + verifytc.getAsMention() + "-channel, to be able to interact with the Server!").complete();
        }else{
            return;
        }
    }
}
