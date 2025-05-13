package app.ashcon.sportpaper.api.text;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.Locale;

/**
 * A component which can be rendered for a specific {@link CommandSender}.
 */
public interface PersonalizedComponent {

	/**
	 * Translate this component into a {@link BaseComponent} in the specified {@link Locale}.
	 *
	 * @param sender the person to render this component for
	 * @return the translated component
	 */
	BaseComponent render(CommandSender sender);
}