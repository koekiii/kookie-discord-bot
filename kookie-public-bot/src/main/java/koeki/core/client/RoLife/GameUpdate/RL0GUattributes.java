package koeki.core.client.RoLife.GameUpdate;

public class RL0GUattributes {

    private String GuildID = "674583308256149514";
    private String ChannelID = "674583308918587405";
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
