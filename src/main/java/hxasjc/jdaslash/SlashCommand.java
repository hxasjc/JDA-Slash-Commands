package hxasjc.jdaslash;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

/**
 * Represents a slash command that can be executed.
 * <p>
 * The main method is {@link SlashCommand#execute(SlashCommandInteractionEvent)} which is fired by the {@link SlashCommandClient} and is responsible for handling and responding to the interaction.
 * <p>
 * This class also contains a few utility methods allowing you to quickly send a response to the interaction without wondering if it has already been acknowledged.
 */
public abstract class SlashCommand {
    /**
     * The method responsible for handling {@link SlashCommandInteractionEvent}s. This is where the majority of a command's functionality should be.
     * <p>
     * <b>The {@link SlashCommandClient} does not acknowledge interactions</b>
     * @param event
     */
    public abstract void execute(SlashCommandInteractionEvent event);

    public static void safeReply(SlashCommandInteractionEvent event, String content) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(content).queue();
        } else {
            event.reply(content).queue();
        }
    }

    public static void safeReply(SlashCommandInteractionEvent event, MessageEmbed embed, MessageEmbed... embeds) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessageEmbeds(embed, embeds).queue();
        } else {
            event.replyEmbeds(embed, embeds).queue();
        }
    }

    public static void safeReply(SlashCommandInteractionEvent event, FileUpload fileUpload, MessageEmbed embed) {
        if (event.isAcknowledged()) {
            event.getHook().sendFiles(fileUpload).addEmbeds(embed).queue();
        } else {
            event.replyFiles(fileUpload).addEmbeds(embed).queue();
        }
    }
}
