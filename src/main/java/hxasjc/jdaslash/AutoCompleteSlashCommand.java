package hxasjc.jdaslash;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.Collection;

/**
 * This interface adds autocomplete functionality to a command.
 * <br/>
 * Autocomplete can be enabled for {@code STRING},
 * {@code INTEGER} and {@code NUMBER} options. JDA will fire a {@link CommandAutoCompleteInteractionEvent} and requires
 * a collection of choice objects to be returned.
 */
public interface AutoCompleteSlashCommand {
    /**
     * Accepts a {@link CommandAutoCompleteInteractionEvent} and returns a collection of choice objects. Up to 25 choices can be returned.
     * @param event The current event to be processed
     * @return A collection of choices that will be presented to the user
     */
    Collection<Command.Choice> autocomplete(CommandAutoCompleteInteractionEvent event);
}
