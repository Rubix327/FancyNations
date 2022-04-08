package me.rubix327.fancynations;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Localization {

    @Getter
    private static Localization instance;
    private final Plugin plugin;

    /**
     * A map of the configurable messages
     */
    private final Map<String, String> MESSAGES_EN = new HashMap<>();
    private final Map<String, String> MESSAGES_RU = new HashMap<>();

    /**
     * Creates a new message handler instance for this plugin
     */
    protected Localization() {
        instance = this;
        this.plugin = FancyNations.getInstance();
    }

    protected void loadAllMessages(){
        loadMessages("messages_en.yml", MESSAGES_EN);
        loadMessages("messages_ru.yml", MESSAGES_RU);
    }

    /**
     * Loads the messages from the config file
     */
    private void loadMessages(String file, Map<String, String> messages) {
        File configFile = new File(this.plugin.getDataFolder() + "/messages", file);
        YamlConfiguration config = new YamlConfiguration();

        if (!configFile.exists())
            this.plugin.saveResource("messages/" + file, false);

        try {
            config.load(configFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().severe("Unable to load " + file);
        }

        for (String key : config.getKeys(false)) {
            messages.put(key, config.getString(key));
        }
    }

    /**
     * Get a message without specifying a localization language.
     * Default language from Settings.Locale will be used.
     * @param key The name of the configuration message
     * @return The message.
     */
    public String get(String key){
        return get(key, Settings.LOCALE_PREFIX);
    }

    /**
     * Get a message in sender's language (locale chosen in the game).
     * If sender is a player then he's locale will be used.
     * If sender is anyone else then server's default locale will be used.
     * Suitable for using when command sender is unknown.
     * @param key The name of the configuration message.
     * @param sender Command sender
     * @return The message.
     */
    public String get(String key, CommandSender sender){
        String locale = (sender instanceof Player ? ((Player) sender).locale().getLanguage() : Settings.LOCALE_PREFIX);
        return get(key, locale);
    }

    /**
     * Get a message in the specified language.
     * If this locale does not exist then server default will be used.
     * Suitable when you want to use specific language.
     * @param key The name of the configuration message.
     * @return The message.
     */
    public String get(String key, String locale){
        Map<String, String> messages = getMessages(locale, false);

        String msg = messages.get(key);
        if (msg == null){
            this.plugin.getLogger().severe(
                    "Unable to find message with key " + key + ". Please make sure all keys are defined!");
            return "";
        }

        return msg;
    }

    /**
     * Get all messages in the specified language.
     * If this locale does not exist then server default will be used.
     * If this server's locale does not exist (mistake in settings.yml) then english will be used.
     * @param locale The name of the configuration message.
     * @param useServerDefault Should we use server's default locale or not.
     * @return All messages.
     */
    private Map<String, String> getMessages(String locale, boolean useServerDefault){
        return switch (locale) {
            case ("ru") -> MESSAGES_RU;
            case ("en") -> MESSAGES_EN;
            default -> (useServerDefault ? MESSAGES_EN : getMessages(Settings.LOCALE_PREFIX, true));
        };
    }

}
