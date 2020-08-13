package koeki.core.server.JoinEvents.JoinedNewServer;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class BotJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {

        TextChannel channel = event.getGuild().createTextChannel("kookie-channel").complete();
        channel.sendMessage("Thanks for inviting me, " + event.getGuild().getOwner().getUser().getAsMention() +"!" +
                "\n\nI highly recommend you to save your server in my database! This can be done with k@saveserver.").complete();
    }
}
