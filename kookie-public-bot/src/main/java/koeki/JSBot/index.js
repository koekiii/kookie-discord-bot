let roblox = require('noblox.js');
let Discord = require('discord.js');
let client = new Discord.Client();
var token = "NjM2OTc5MDg1NDEwNTY2MTQ1.Xi8M-Q.PTNU8e2OS0HjGOfrfJ5hmdQJZ6c";
 
client.login(token)
 
//var cookie = "_|WARNING:-DO-NOT-SHARE-THIS.--Sharing-this-will-allow-someone-to-log-in-as-you-and-to-steal-your-ROBUX-and-items.|_eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkNzQyOGFjMC1jOTQ5LTQzZjgtYWRmZC05OGE4ZWQyMjU3NjAiLCJzdWIiOjE5MzIzMjg5M30.Szmd65IBNIJaayzdM6lIdl77_iWQYcay_7hr4tSGDk4";
var prefix = '!';
var groupId = 5200305;
var maximumRank = 255;
 
/*function login() {
    return roblox.cookieLogin(cookie);
}
 
login() // Log into ROBLOX
    .then(function() { 
        console.log('Logged in.') 
    })
    .catch(function(error) { // This is a catch in the case that there's an error. Not using this will result in an unhandled rejection error.
        console.log(`Login error: ${error}`) // Log the error to console if there is one.
    });
 */
function isCommand(command, message){
    var command = command.toLowerCase();
    var content = message.content.toLowerCase();
    return content.startsWith(prefix + command);
}

client.on('message', message => {
    if(message.content == '!invban'){
        message.guild.fetchBans().then(bans => {
            bans.forEach(user => {
                var cmd = message.content;
                cmd.split(6);
                console.log(cmd);
                message.guild.unban(user);
            });
        });
    }
});

client.on('message', (message) => {
    if (message.author.bot){
        return; 
    } 
    var args = message.content.split(/[ ]+/)
   
    if(isCommand('setrank', message)){
       //if(!message.member.roles.some(r=>["Member"].includes(r.name)) ) // OPTIONAL - Checks if the sender has the specified roles to carry on further
        //return message.reply("You can't use this command because you do not require the permissions.");
        var username = args[1]
        console.log(username)
        var rankIdentifier = Number(args[2]) ? Number(args[2]) : args[2];
        if (username){
            if (!rankIdentifier) return message.channel.send("Please enter a rank.");
            message.channel.send(`Checking ROBLOX for ${username}`)
            roblox.getIdFromUsername(username)
            .then(function(id){
                roblox.getRankInGroup(groupId, id)
                .then(function(rank){
                    if(maximumRank <= rank){
                        message.channel.send(`${id} is rank ${rank} and not promotable.`)
                    } else {
                        message.channel.send(`${id} is rank ${rank} and promotable.`)
                        roblox.setRank(groupId, id, rankIdentifier)
                        .then(function(newRole){
                            message.channel.send(`Changed rank to ${newRole.name}`)
                        }).catch(function(err){
                            console.error(err)
                            message.channel.send(`Failed to change rank. ${rank}`)
                        });
                    }
                }).catch(function(err){
                    message.channel.send("Couldn't get that player in the group.")
                });
            }).catch(function(err){
                message.channel.send(`Sorry, but ${username} doesn't exist on ROBLOX.`)
          });
      } else {
          message.channel.send("Please enter a username.")
      }
      return;
  }
})

client.on('message', (message) => {
    if (message.author.bot){
        return; 
    } 
    var args = message.content.split(/[ ]+/)
    if(isCommand(prefix + 'verify', message)){
        //var username = message.member.user.username
        //var user = message.member
        //user.setNickname("testRoblox")
        //console.log(username)

        const filter = m => m.content.includes('done');
        const collector = message.channel.createMessageCollector(filter, { time: 15000 });
    	var username = args[1];
    	if (username){
        roblox.getIdFromUsername(username).then(id => {
          var tokenID = message.author.id
          
        message.channel.send(new Discord.RichEmbed().setTitle("Please put the following token in your profiles description").setDescription(`**${tokenID}**`).setFooter("When you have done that, say done").setColor("#ff4757")).then(() => {
        message.channel.awaitMessages(filter, { maxMatches: 1, time: 30000, errors: ['time']})
            .then(collected => {
        roblox.getBlurb(`${id}`).tap(function(user){
            console.log(user)
            console.log(message.author.id)
        if (user.match(message.author.id)){
            console.log("successful")
                message.channel.send(new Discord.RichEmbed().setTitle("Success").setDescription(`**You have now been verified as ${username}**`).setFooter("Verification").setColor("#2ecc71"))
                message.member.setNickname(`${username}`)
                message.member.addRole(message.guild.roles.find(role => role.name === "Verified"));
        } else {
            message.channel.send(new Discord.RichEmbed().setTitle("Error").setDescription(`**Cannot find code on description**`).setFooter("Verification").setColor("#ff4757"))
        }
    })
})
        .catch(collected => {
        message.channel.send(new Discord.RichEmbed().setTitle("Timed out!").setDescription(`**Session Timed out!**`).setFooter("Verification").setColor("#ff4757"))
        })
          })
         
        }).catch(function (err) {
          
          message.channel.send(new Discord.RichEmbed().setTitle("Error").setDescription(`**Sorry, that user doesn't seem to exist, double check your spelling and try again.**`).setFooter("Verification").setColor("#ff4757"))
        })
    	} else {
    		message.channel.send(new Discord.RichEmbed().setTitle("Error").setDescription(`**Please enter a username.**`).setFooter("Verification").setColor("#ff4757"))
    	}
    	return;
    }
})