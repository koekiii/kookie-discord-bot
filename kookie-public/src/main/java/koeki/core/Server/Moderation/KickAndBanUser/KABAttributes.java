package koeki.core.Server.Moderation.KickAndBanUser;

public class KABAttributes {

    private long GuildID;
    private long UserID;
    private long AuthorID;
    private long MessageID;
    private long UserMessageID;
    private int Days;

    public long getGuildID() {
        return GuildID;
    }

    public void setGuildID(long guildID) {
        GuildID = guildID;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public long getMessageID() {
        return MessageID;
    }

    public void setMessageID(long messageID) {
        MessageID = messageID;
    }

    public long getUserMessageID() {
        return UserMessageID;
    }

    public void setUserMessageID(long userMessageID) {
        UserMessageID = userMessageID;
    }

    public void setDays(int days) {
        Days = days;
    }

    public int getDays() {
        return Days;
    }

    public void setAuthorID(long authorID) {
        AuthorID = authorID;
    }

    public long getAuthorID() {
        return AuthorID;
    }
}
