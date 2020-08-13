package Event.Core.Votesystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

public class VoteMessage {

    private int sec;
    private String desc;
    private EmbedBuilder eb;


    public VoteMessage(String description, int sec, List<String> ansOptions, Member member, JDA jda) {
        this.sec = sec;
        this.desc = description;
        eb = new EmbedBuilder();
        eb.setTitle("Vote von " + member.getEffectiveName());
        eb.setDescription(desc);

        for (int i = 0; i < ansOptions.size(); i++){
            eb.addField(ansOptions.get(i), "", false);
        }

        eb.addField("Sekunden:", String.valueOf(sec), true);
        eb.setColor(Color.white);
    }

    public MessageEmbed getEmbed(){
        return eb.build();
    }


}
