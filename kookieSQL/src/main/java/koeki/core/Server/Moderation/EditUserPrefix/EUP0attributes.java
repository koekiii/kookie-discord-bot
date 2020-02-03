package koeki.core.Server.Moderation.EditUserPrefix;

public class EUP0attributes {

    private long GuildID;
    private long MessageID;
    private long AuthorID;

    public long getGuildID() {
        return GuildID;
    }

    public void setGuildID(long guildID) {
        GuildID = guildID;
    }

    public long getMessageID() {
        return MessageID;
    }

    public void setMessageID(long messageID) {
        MessageID = messageID;
    }

    public long getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(long authorID) {
        AuthorID = authorID;
    }
}
