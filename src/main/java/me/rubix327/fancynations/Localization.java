package me.rubix327.fancynations;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.mineacademy.fo.Common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Localization {

    private static Localization instance;

    private final Plugin plugin;

    private Localization() {
        this.plugin = FancyNations.getInstance();
    }

    public static Localization getInstance(){
        if (instance == null){
            instance = new Localization();
        }
        return instance;
    }

    private final Map<String, Map<String, String>> messagesMap = new HashMap<>();

    public void init(List<String> locales){
        File messagesFolder = new File(this.plugin.getDataFolder() + "/messages");

        saveFiles(locales);
        loadFiles(messagesFolder);
    }

    /**
     * Save all messages files from .jar to plugin's messages/ folder
     * @param files What locales should we save
     */
    private void saveFiles(List<String> files){
        for (String fileName : files){
            fileName = "messages_" + fileName + ".yml";
            File serverFile = new File(this.plugin.getDataFolder() + "/messages", fileName);
            if (!serverFile.exists()){
                plugin.getLogger().info("Localization " + fileName + " does not exist. " +
                        "Copying one from plugin's jar...");
                this.plugin.saveResource("messages/" + fileName, false);
            }
        }
    }

    /**
     * Loads messages from all files from the specified folder
     * @param folder What plugin's folder should we use
     */
    private void loadFiles(final File folder) {
        YamlConfiguration config = new YamlConfiguration();

        for (final File file : Objects.requireNonNull(folder.listFiles())){
            String langTag;
            Map<String, String> messages = new HashMap<>();

            try {
                config.load(file);
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
                this.plugin.getLogger().severe("Unable to load " + file);
            }

            if (file.isDirectory()) continue;
            if (!file.getName().startsWith("messages_")) continue;
            if (!file.getName().endsWith(".yml")) continue;
            langTag = file.getName().replace("messages_", "").replace(".yml", "");
            plugin.getLogger().info("Found '" + langTag + "' localization. Loading all keys from the file...");

            for (String key : config.getKeys(true)) {
                messages.put(key, config.getString(key));
            }
            messagesMap.put(langTag, messages);
        }
        plugin.getLogger().info("All localization files have been successfully loaded.");
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
     * <p>Get a message in sender's language (locale chosen in the game).
     * If sender is a player then he's locale will be used.
     * If sender is anyone else then server's default locale will be used.</p>
     * <b>Suitable when command sender is unknown.</b>
     * @param key The name of the configuration message.
     * @param sender Command sender
     * @return The message.
     */
    public String get(String key, CommandSender sender){
        if (sender instanceof Player && Settings.Messages.USE_PLAYER_BASED_LOCALES){
            String playerLang = ((Player) sender).locale().getLanguage();
            // If player's locale exists then turn it on
            if (messagesMap.containsKey(playerLang)){
                return get(key, playerLang);
            }
        }
        // If server's locale exists then turn it on
        if (messagesMap.containsKey(Settings.LOCALE_PREFIX)){
            return get(key, Settings.LOCALE_PREFIX);
        }
        // If neither player's nor server's locale exists then turn on english
        return get(key, "en");
    }

    /**
     * <p>Get a message in the specified language formatted with prefix.
     * If this key in this locale does not exist then server's default will be used.
     * If server's default key does not exist then english will be used.
     * If the key has not been found in english localization then it will drop an error message.</p>
     * <b>Suitable when you want to use specific language.</b>
     * @param key The name of the configuration message.
     * @return The message.
     */
    public String get(String key, String locale){
        String msg = messagesMap.get(locale).get(key);
        int attempts = 1;

        while (msg == null) {
            if (attempts == 1) {
                this.plugin.getLogger().warning(
                        "Unable to find message with key " + key + " in messages_" + locale + ".yml. " +
                                "Please make sure all keys are defined!");
                msg = messagesMap.get(Settings.LOCALE_PREFIX).get(key);
                attempts += 1;
            }
            else if (attempts == 2) {
                this.plugin.getLogger().warning(
                        "Unable to find message with key " + key + " in server default messages_" +
                                Settings.LOCALE_PREFIX + ".yml. Please make sure all keys are defined!");
                msg = messagesMap.get("en").get(key);
                attempts += 1;
            }
            else if (attempts >= 3) {
                this.plugin.getLogger().warning(
                        "Unable to find message with key " + key + " in english messages_en.yml. " +
                                "Please make sure all keys are defined!");
                msg = "@warn_prefix &cMessage not found in any of the messages files (key: " + key + ")." +
                        " Please contact an administrator.";
            }
        }

        // Replacing all placeholders like @nl and @error_prefix
        msg = replacePlaceholders(msg);

        // Adding a plugin prefix if it is enabled
        if (Settings.Messages.USE_PLUGIN_PREFIX){
            msg = Settings.Messages.PREFIX_PLUGIN + msg;
        }

        // Disabling a message if it starts with @off
        if (msg.startsWith("@off")) { msg = ""; }

        return msg;
    }

    /**
     * Replaces all placeholders in the message with their values.
     * @param message with placeholders
     * @return replaced message
     */
    public String replacePlaceholders(String message){
        return message
                .replace("@chat_line_smooth", Common.chatLineSmooth())
                .replace("@chat_line", Common.chatLine())
                .replace("@error_prefix", Settings.Messages.PREFIX_ERROR)
                .replace("@warn_prefix", Settings.Messages.PREFIX_WARNING)
                .replace("@success_prefix", Settings.Messages.PREFIX_SUCCESS)
                .replace("@info_prefix", Settings.Messages.PREFIX_INFO)
                .replace("@newline ", "\n")
                .replace("@newline", "\n")
                .replace("@nl ", "\n");
    }

    /**
     * Converts list to a string joining it on \n delimiter and then
     * replaces all placeholders in the message with their values.
     * @param messages list with placeholders
     * @return replaced message
     */
    public String replacePlaceholders(List<String> messages){
        return replacePlaceholders(String.join("\n", messages));
    }

}
