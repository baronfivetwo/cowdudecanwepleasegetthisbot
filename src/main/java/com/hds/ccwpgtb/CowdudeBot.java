package com.hds.ccwpgtb;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;
import discord4j.rest.RestClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CowdudeBot {
    public static void main(String[] args) {
        //Start spring application
        new SpringApplicationBuilder(CowdudeBot.class)
            .build()
            .run(args);
    }


    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return DiscordClientBuilder.create(System.getenv("BOT_TOKEN")).build()
            .gateway()
            .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.listening("to /commands")))
            .login()
            .block();
    }

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}
