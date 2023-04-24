package com.hds.ccwpgtb.commands;

import org.springframework.stereotype.Component;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import java.util.List;
import java.util.ArrayList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReportCommand implements SlashCommand {
    @Override
    public String getName() {
        return "report";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        
        String name = event.getOption("id")
            .flatMap(ApplicationCommandInteractionOption::getValue)
            .map(ApplicationCommandInteractionOptionValue::asString)
            .get();
        
        String deed = event.getOption("deed")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        
        String punishment = event.getOption("punishment")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        
        String evidence = "";
        
        try {
        		evidence = event.getOption("evidence")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        }		
        catch(Exception e) { /* do nothing since this is an optional argument */ }
        
        User user = null;
        String tag = "";
        String id = "";
        String report = "";
        
        try {
        	user = event.getClient().getUserById(Snowflake.of(name)).block();
        	tag = user.getTag();
        	id = name;
        	report = "**Name**: " + tag + "\n"
            		+ "**ID:** " + id + "\n"
            		+ "**Deed/Offense:** " + deed + "\n"
            		+ "**Punishment:** " + punishment + "\n"
            		+ "**Proof:** " + (evidence==null?"":evidence) + "\n";
        }
        catch(Exception f) {
        	report = "User with ID \"" + name + "\" not found";
        }
        
        //String id = user.getTag();
        
        
        
        //Reply to the slash command, with the name the user supplied
        return  event.reply()
            .withEphemeral(false)
            .withContent(report);
    }
}
