package koeki.core.Server.JoinEvents.JoinedNewServer;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class BotJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = event.getGuild().createTextChannel("kookie-channel").complete();
        channel.sendMessage("Thanks for inviting me, " + event.getGuild().getOwner().getUser().getAsMention() +"!" +
                "\n\nI highly recommend you to save your server in my database! This can be done with k@saveserver.").complete();
    }
}
