package hxasjc.jdaslash;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A basic implementation of {@link SlashCommand} and {@link AutoCompleteSlashCommand} that accepts functional interfaces.
 * <br/>
 * The {@link FunctionalSlashCommand#execute(SlashCommandInteractionEvent)} method requires a {@link Consumer<SlashCommandInteractionEvent>} that accepts a
 * {@link SlashCommandInteractionEvent}
 * while {@link FunctionalSlashCommand#autocomplete(CommandAutoCompleteInteractionEvent)} requires a {@link Function} that accepts an
 * {@link CommandAutoCompleteInteractionEvent} and returns a {@link Collection} of {@link net.dv8tion.jda.api.interactions.commands.Command.Choice}s.
 * <br/>
 * The {@link FunctionalSlashCommand#execute(SlashCommandInteractionEvent)} and {@link FunctionalSlashCommand#autocomplete(CommandAutoCompleteInteractionEvent)}
 * methods will call their respective functional interface.
 */
public class FunctionalSlashCommand extends SlashCommand implements AutoCompleteSlashCommand {
    private final Consumer<SlashCommandInteractionEvent> executeConsumer;
    private final Function<CommandAutoCompleteInteractionEvent, Collection<Command.Choice>> autoCompleteFunction;

    /**
     * Creates a new FunctionalSlashCommand that includes support for autocomplete
     * @param executeConsumer The consumer that will handle {@link SlashCommandInteractionEvent}s
     * @param autoCompleteFunction The function that will handle {@link CommandAutoCompleteInteractionEvent}s and return a collection of {@link net.dv8tion.jda.api.interactions.commands.Command.Choice}s
     */
    public FunctionalSlashCommand(Consumer<SlashCommandInteractionEvent> executeConsumer, Function<CommandAutoCompleteInteractionEvent, Collection<Command.Choice>> autoCompleteFunction) {
        this.executeConsumer = executeConsumer;
        this.autoCompleteFunction = autoCompleteFunction;
    }

    /**
     * Creates a new FunctionalSlashCommand that does not include support for autocomplete. If an {@link CommandAutoCompleteInteractionEvent} is passed to it, the command will return {@code null}
     * @param executeConsumer The consumer that will handle {@link SlashCommandInteractionEvent}s
     */
    public FunctionalSlashCommand(Consumer<SlashCommandInteractionEvent> executeConsumer) {
        this.executeConsumer = executeConsumer;
        this.autoCompleteFunction = event -> null;
    }

    @Override
    public Collection<Command.Choice> autocomplete(CommandAutoCompleteInteractionEvent event) {
        return autoCompleteFunction.apply(event);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        executeConsumer.accept(event);
    }
}
