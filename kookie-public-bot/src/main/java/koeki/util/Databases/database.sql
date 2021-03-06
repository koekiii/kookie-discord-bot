CREATE TABLE Server(
  ServerID LONG PRIMARY KEY NOT NULL,
  ServerName TEXT NOT NULL,
  MemberCount INT NOT NULL,
  ServerPrefix TEXT NOT NULL,
  ServerLastUpdated TEXT NOT NULL,
  ServerWelcomeChannel LONG
);

CREATE TABLE Roles(
    RoleID LONG PRIMARY KEY NOT NULL,
    RoleName TEXT NOT NULL
);

CREATE TABLE ServerRoles(
    RoleID LONG NOT NULL,
    ServerID LONG NOT NULL,
    Availabe BOOLEAN,
    FOREIGN KEY (RoleID)
        REFERENCES Roles (RoleID),
    FOREIGN KEY (ServerID)
        REFERENCES Server (ServerID)
);

CREATE TABLE DiscordUser(
  UserID LONG PRIMARY KEY NOT NULL,
  UserName TEXT NOT NULL,
  UserDiscriminator TEXT NOT NULL
);

CREATE TABLE ServerDiscordUser(
  ServerDiscordUserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  DCUserID LONG NOT NULL,
  DCServerID LONG NOT NULL,
  FOREIGN KEY (DCUserID)
    REFERENCES DiscordUser (UserID),
  FOREIGN KEY (DCServerID)
    REFERENCES Server (ServerID)
);

CREATE TABLE SuggestionSystem(
  SuggestionSystemID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  InitiationTextChannelID LONG NOT NULL,
  PostingTextChannelID LONG NOT NULL
)

CREATE TABLE ServerSuggestionSystem(
  ServerSuggestionSystemID INTEGER PRIMARY KEY AUTOINCREMENT NO NULL,
  ITCID LONG NOT NULL,
  PTCID LONG NOT NULL,
  FOREIGN KEY (ITCID)
    REFERENCES SuggestionSystem (InitiationTextChannelID),
  FOREIGN KEY (PTCID)
    REFERENCES SuggestionSystem (PostingTextChannelID)
)
