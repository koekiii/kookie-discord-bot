package koeki.core.server.JoinEvents.UserJoinedServer;

import koeki.main.Main;
import koeki.util.DatabaseUtil;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class UserJoinEvent extends ListenerAdapter {

    private static List<UserJoinEventAttributes> ujeaList = new ArrayList<>();

    public void onGuildMemberJoin (GuildMemberJoinEvent event){

        long tcID = 0;
        UserJoinEventAttributes ujea = new UserJoinEventAttributes();
        ujea.setGuildID(event.getGuild().getIdLong());
        ujea.setUserID(event.getUser().getIdLong());
        ujeaList.add(ujea);
        try{
            for(UserJoinEventAttributes u : ujeaList){
                if(event.getGuild().getIdLong() == u.getGuildID()){
                    if(event.getUser().getIdLong() == u.getUserID()){
                        tcID = DatabaseUtil.getServerWelcomeChannel(ujea.getGuildID());
                        if(tcID == 0){
                            return;
                        }else{
                            TextChannel tc = Main.getBuilder().getTextChannelById(tcID);
                            tc.sendMessage("Welcome to " + event.getGuild().getName() + ", " + event.getUser().getAsMention()  + "!").complete();
                            ujeaList.clear();
                        }
                    }
                }
            }
        }catch(ConcurrentModificationException e){ }
    }
}
