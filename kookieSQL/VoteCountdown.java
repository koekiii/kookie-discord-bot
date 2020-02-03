package Event.Core.Votesystem;

import Files.FileUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.List;

public class VoteCountdown implements Runnable{


    private volatile boolean running = true;
    private List<String> ansOptions;
    private int[] ansCounter = {0,0,0,0,0,0,0,0,0,0};
    private String numEmojisUUID[] = {"U+31U+20e3", "U+32U+20e3", "U+33U+20e3", "U+34U+20e3", "U+35U+20e3", "U+36U+20e3", "U+37U+20e3", "U+38U+20e3", "U+39U+20e3", "U+1F51F"};

    private Message message;
    private long messageID;
    private MessageChannel channel;
    private int time;
    private EmbedBuilder eb;

    @Override
    public void run() {
        while (running) {
            this.countdown();
        }
    }

    public VoteCountdown(Message message, int time, List<String> ansOptions) {
        this.message = message;
        this.time = time;
        eb = new EmbedBuilder();
        eb.setTitle(message.getEmbeds().get(0).getTitle());
        eb.setDescription(message.getEmbeds().get(0).getDescription());
        messageID = message.getIdLong();
        channel = message.getChannel();
        this.ansOptions = ansOptions;

        int index = 0;
        for (String s : ansOptions) {
            message.addReaction(numEmojisUUID[index]).queue();
            index++;
        }
    }

    private void countdown(){
        while (time > 0){
            try {
                if (time <= 5){
                    time--;
                    Thread.sleep(1000L);
                } else {
                    time -= 5;
                    Thread.sleep(5000L);
                }
                eb.clearFields();

                for (String s : ansOptions){
                    eb.addField(s, "", false);
                }
                eb.addField("Sekunden:", String.valueOf(time), true);
                eb.setColor(Color.WHITE);
                channel.editMessageById(messageID, eb.build()).queue();
            }
            catch (InterruptedException e) {

            }
        }
        eb.clearFields();
        for (String s : ansOptions){
            eb.addField(s, "", false);
        }
        eb.addField("Sekunden:", String.valueOf(time), true);
        eb.setColor(Color.WHITE);

        int largestIndex = FileUtil.getIndexOfLargest(ansCounter);
        System.out.println(largestIndex);
        if (largestIndex < 0) {
            eb.setFooter("Das Voting war nicht eindeutig");
            eb.setColor(Color.RED);
        } else {
            String ans = ansOptions.get(largestIndex).substring(1);
            int doubblePoint = ans.indexOf(":");
            ans = ans.substring(doubblePoint + 1, ans.length());
            eb.setColor(Color.GREEN);
            eb.setFooter("Gewonnen hat: " + ans);
        }
        channel.editMessageById(messageID, eb.build()).queue();

        running = false;
    }

    public boolean getRunning(){
        return running;
    }

    public long getMessageID() {
        return messageID;
    }

    public void increase(int i){
        ansCounter[i]++;
    }

    public void decrease(int i){
        ansCounter[i]--;
    }


}
