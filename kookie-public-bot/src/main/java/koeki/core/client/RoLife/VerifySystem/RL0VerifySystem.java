package koeki.core.client.RoLife.VerifySystem;

import koeki.core.client.RoLife.RL0Secrets;
import koeki.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RL0VerifySystem extends ListenerAdapter {

    private static JDA jda = Main.getBuilder();
    private static Role memberRole = jda.getRoleById("613322463652085780");

    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        // Open the Verify System
        String contentOpen = event.getMessage().getContentRaw();

        if(contentOpen.equals("k@openverifysystem")){
            if(event.getAuthor().getId().equals("348781333839085569")){
                EmbedBuilder eb = new EmbedBuilder();
                Message message = event.getMessage();
                eb.setTitle("Verify yourself!");
                eb.setDescription("Simply react to the message, if you read the rules!");
                message = event.getChannel().sendMessage(eb.build()).complete();
                message.addReaction("✅").complete();
            }
        }
    }

    public void onGuildMessageReactionAdd (GuildMessageReactionAddEvent event){
        if(event.getGuild().getId().equals(RL0Secrets.GuildID)){
            if(event.getChannel().getId().equals("688151758349795395")){
                if(event.getReactionEmote().getName().equals("✅") &&
                    !event.getMember().getUser().equals(jda.getSelfUser())){
                    event.getReaction().removeReaction(event.getUser()).complete();
                    event.getGuild().addRoleToMember(event.getMember(), memberRole).complete();
                    return;
                }else if(!event.getReactionEmote().getName().equals("✅")){
                    event.getReaction().removeReaction(event.getUser()).complete();
                    return;
                }
            }else {
                return;
            }
        }else {
            return;
        }
    }
}
