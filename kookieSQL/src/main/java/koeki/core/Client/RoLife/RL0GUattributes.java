package koeki.core.Client.RoLife;

public class RL0GUattributes {

    private String GuildID = "613319325708976138";
    private String ChannelID = "614507328657555528";
    private String MessageID;
    private int ID;
    private String MessageText;

    public RL0GUattributes(){

    }

    public String getGuildID() {
        return GuildID;
    }

    public String getChannelID() {
        return ChannelID;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
