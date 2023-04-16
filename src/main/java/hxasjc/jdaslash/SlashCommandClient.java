package hxasjc.jdaslash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class SlashCommandClient extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlashCommandClient.class);

    private final HashMap<SlashCommandData, SlashCommand> commandMap = new HashMap<>();
    private final JDA jda;
    private BiConsumer<Throwable, SlashCommandInteractionEvent> slashCommandExecutionExceptionConsumer = (throwable, event) -> {
        LOGGER.error("Error while executing command", throwable);

        if (event.isAcknowledged()) {
            event.getHook().sendMessageEmbeds(
                    new EmbedBuilder()
                            // TODO: 2023-03-07 add colour
                            .setTitle("Error while executing command")
                            .setDescription(throwable.getMessage())
                            .build()
            ).queue();
        } else {
            event.replyEmbeds(
                    new EmbedBuilder()
                            // TODO: 2023-03-07 add colour
                            .setTitle("Error while executing command")
                            .setDescription(throwable.getMessage())
                            .build()
            ).queue();
        }
    };

    public SlashCommandClient(JDA jda) {
        this.jda = jda;
    }

    public void addCommand(SlashCommandData data, SlashCommand command) {
        commandMap.put(data, command);
    }

    public void addCommands(Map.Entry<SlashCommandData, SlashCommand>... entries) {
        commandMap.putAll(Map.ofEntries(entries));
    }

    public void registerGlobalCommands() {
        jda
                .updateCommands()
                .addCommands(commandMap.keySet())
                .queue();
    }

    public void registerGuildCommands(Guild guild) {
        guild
                .updateCommands()
                .addCommands(commandMap.keySet())
                .queue();
    }

    public void registerGuildCommands(long guildId) {
        registerGuildCommands(Objects.requireNonNull(jda.getGuildById(guildId)));
    }

    public void onException(BiConsumer<Throwable, SlashCommandInteractionEvent> consumer) {
        this.slashCommandExecutionExceptionConsumer = consumer;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        try {
            for (Map.Entry<SlashCommandData, SlashCommand> entry : commandMap.entrySet()) {
                if (event.getName().equals(entry.getKey().getName())) {
                    entry.getValue().execute(event);
                    return;
                }
            }
        } catch (Throwable t) {
            slashCommandExecutionExceptionConsumer.accept(t, event);
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        try {
            for (Map.Entry<SlashCommandData, SlashCommand> entry : commandMap.entrySet()) {
                if (event.getName().equals(entry.getKey().getName())) {
                    try {
                        AutoCompleteSlashCommand command = (AutoCompleteSlashCommand) entry.getValue();
                        event.replyChoices(command.autocomplete(event)).queue();
                    } catch (ClassCastException ignored) {}
                }
            }
        } catch (Throwable t) {
            //slashCommandExecutionExceptionConsumer.accept(t, event);
        }
    }
}
