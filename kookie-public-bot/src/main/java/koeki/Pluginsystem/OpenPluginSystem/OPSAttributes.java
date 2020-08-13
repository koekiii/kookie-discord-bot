package koeki.Pluginsystem.OpenPluginSystem;

public class OPSAttributes {

    private String AuthorUserID;
    private String GuildID;
    private int Page;
    private String MessageID;
    private String ChannelID;

    public OPSAttributes(){
    }

    public void setGuildID(String guildID) {
        GuildID = guildID;
    }

    public String getGuildID() {
        return GuildID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public String getMessageID() {
        return MessageID;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public String getAuthorUserID() {
        return AuthorUserID;
    }

    public void setAuthorUserID(String authorUserID) {
        AuthorUserID = authorUserID;
    }

    public String getChannelID() {
        return ChannelID;
    }

    public void setChannelID(String channelID) {
        ChannelID = channelID;
    }
}
