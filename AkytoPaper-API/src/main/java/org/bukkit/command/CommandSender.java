package org.bukkit.command;

import java.util.Locale;
import app.ashcon.sportpaper.api.text.PersonalizedComponent;
import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public interface CommandSender extends Permissible {

    /**
     * Sends this sender a message
     *
     * @param message Message to be displayed
     */
    public void sendMessage(String message);

    /**
     * Sends this sender multiple messages
     *
     * @param messages An array of messages to be displayed
     */
    public void sendMessage(String[] messages);

    /**
     * Returns the server instance that this command is running on
     *
     * @return Server instance
     */
    public Server getServer();

    /**
     * Gets the name of this command sender
     *
     * @return Name of the sender
     */
    public String getName();

    /**
     * Return this sender's name as viewed by the given sender. Used by
     * {@link org.bukkit.entity.Player}s to support fake names.
     */
    public String getName(CommandSender viewer);

    // Paper start
    /**
     * Sends the component to the sender
     *
     * <p>If this sender does not support sending full components then
     * the component will be sent as legacy text.</p>
     *
     * @param component the component to send
     */
    default void sendMessage(net.md_5.bungee.api.chat.BaseComponent component) {
        this.sendMessage(component.toLegacyText());
    }

    /**
     * Sends an array of components as a single message to the sender
     *
     * <p>If this sender does not support sending full components then
     * the components will be sent as legacy text.</p>
     *
     * @param components the components to send
     */
    default void sendMessage(net.md_5.bungee.api.chat.BaseComponent... components) {
        this.sendMessage(new net.md_5.bungee.api.chat.TextComponent(components).toLegacyText());
    }
    // Paper end

    // SportPaper start

    /**
     * Gets the locale this command source is currently using.
     *
     * <p>This is {@link java.util.Locale#US} by default.</p>
     *
     * @return the locale
     */
    default java.util.Locale getLocale() {
        return Locale.ENGLISH;
    }

    /**
     * Sends a translatable component to this command source using its {@link #getLocale() locale}.
     *
     * @param translatable the translatable component
    */
    default void sendMessage(PersonalizedComponent translatable) {
        this.sendMessage(translatable.render(this));
    }

    /**
     * Sends an array of translatable components to this command source using its {@link #getLocale() locale}.
     *
     * @param translatables the translatable components
     */
    default void sendMessage(PersonalizedComponent... translatables) {
        for (PersonalizedComponent translatable : translatables) {
            this.sendMessage(translatable.render(this));
        }
    }
    // SportPaper end
}
