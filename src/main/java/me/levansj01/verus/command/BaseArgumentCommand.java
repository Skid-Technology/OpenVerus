package me.levansj01.verus.command;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import me.levansj01.verus.VerusPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseArgumentCommand extends BaseCommand {
    protected Map<String, BaseArgumentCommand.CommandArgument> arguments = new ConcurrentHashMap<>();

    protected void sendHelp(CommandSender sender) {
        String str = VerusPlugin.COLOR + "Help Command for /" + this.getName() + ":\n";

        for (BaseArgumentCommand.CommandArgument commandArgument : this.arguments.values()) {
            str = str
                + String.format(
                    ChatColor.GRAY + "- " + VerusPlugin.COLOR + "/%s %s %s: %s\n",
                    this.getName(),
                    commandArgument.argument,
                    commandArgument.usage,
                    ChatColor.WHITE + commandArgument.description
                );
        }

        sender.sendMessage(str);
    }

    protected void addArgument(BaseArgumentCommand.CommandArgument commandArgument) {
        this.arguments.put(commandArgument.argument, commandArgument);
    }

    public BaseArgumentCommand(String str) {
        super(str);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            this.sendHelp(sender);
        } else {
            BaseArgumentCommand.CommandArgument commandArgument = this.arguments.get(args[0]);
            if (commandArgument == null) {
                this.sendHelp(sender);
            } else {
                commandArgument.run(sender, args);
            }
        }
    }

    public BaseArgumentCommand(String str, String str2, String str3, List<String> list) {
        super(str, str2, str3, list);
    }

    public class CommandArgument {
        private final BiConsumer<CommandSender, String[]> consumer;
        private String usage = "";
        protected final String argument;
        protected final String description;

        public CommandArgument(String argument, String description, BiConsumer<CommandSender, String[]> consumer) {
            this.argument = argument;
            this.description = description;
            this.consumer = consumer;
        }

        public void run(CommandSender sender, String[] args) {
            if (this.consumer != null) {
                this.consumer.accept(sender, args);
            }
        }

        public CommandArgument(String argument, String description, String usage, BiConsumer<CommandSender, String[]> consumer) {
            this.argument = argument;
            this.description = description;
            this.usage = usage;
            this.consumer = consumer;
        }
    }
}
