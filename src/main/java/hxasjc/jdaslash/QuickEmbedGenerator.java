package hxasjc.jdaslash;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class QuickEmbedGenerator {
    private String title;
    private Color colour;

    public QuickEmbedGenerator defaultColour(Color colour) {
        this.colour = colour;
        return this;
    }

    public QuickEmbedGenerator defaultTitle(String title) {
        this.title = title;
        return this;
    }

    public MessageEmbed embedWithDescription(String description) {
        return new EmbedBuilder()
                .setColor(colour)
                .setTitle(title)
                .setDescription(description)
                .build();
    }
}
