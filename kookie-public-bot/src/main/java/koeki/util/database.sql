CREATE TABLE Server (
  ServerID LONG PRIMARY KEY NOT NULL,
  ServerName TEXT NOT NULL,
  MemberCount INT NOT NULL,
  ServerPrefix TEXT NOT NULL,
  ServerLastUpdated TEXT NOT NULL
);

CREATE TABLE ServerRoles (
    RoleID LONG NOT NULL,
    ServerID LONG NOT NULL,
    Online BOOLEAN,
    FOREIGN KEY (ServerID)
      REFERENCES Server (ServerID)
);

CREATE TABLE DiscordUser (
  UserID LONG PRIMARY KEY NOT NULL,
  UserName TEXT NOT NULL,
  UserDiscriminator TEXT NOT NULL
);

CREATE TABLE ServerDiscordUser (
  ServerDiscordUserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  DCUserID LONG NOT NULL,
  DCServerID LONG NOT NULL,
  FOREIGN KEY (DCUserID)
    REFERENCES DiscordUser (UserID),
  FOREIGN KEY (DCServerID)
    REFERENCES Server (ServerID)
);

CREATE TABLE ServerPluginSystem (
  ServerPluginSystem INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  Online BOOLEAN NOT NULL,
  DCServerID LONG NOT NULL,
  FOREIGN KEY (DCServerID)
    REFERENCES Server (ServerID)
);

CREATE TABLE ServerWelcomeSystem (
  ServerWelcomeSystemID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  Online BOOLEAN NOT NULL,
  DCServerID LONG NOT NULL,
  WTCID LONG NOT NULL,
  FOREIGN KEY (DCServerID)
    REFERENCES Server (ServerID)
);

CREATE TABLE ServerSuggestionSystem (
  ServerSuggestionSystemID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  Online BOOLEAN NOT NULL,
  DCServerID LONG NOT NULL,
  ITCID LONG NOT NULL,
  PTCID LONG NOT NULL,
  FOREIGN KEY (DCServerID)
    REFERENCES Server (ServerID)
);
