package net.rinorclient.client.impl.manager.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.impl.command.*;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.util.Globals;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CommandManager implements Globals {

    private final List<Command> commands = new ArrayList<>();
    private String prefix = ".";
    private int prefixKey = GLFW.GLFW_KEY_PERIOD;
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
    private final CommandSource source = new ClientCommandSource(null, mc);

    public CommandManager() {
        Rinor.EVENT_HANDLER.subscribe(this);
        register(
                new BindCommand(),
                new ConfigCommand(),
                new DisableAllCommand(),
                new DrawnCommand(),
                new FriendCommand(),
                new HClipCommand(),
                new HelpCommand(),
                new HideAllCommand(),
                new ModulesCommand(),
                new NbtCommand(),
                new OpenFolderCommand(),
                new PrefixCommand(),
                new ResetCommand(),
                new SoundReloadCommand(),
                new ToggleCommand(),
                new VanishCommand(),
                new VClipCommand()
        );
        //
        for (Module module : Managers.MODULE.getModules()) {
            register(new ModuleCommand(module));
        }
        Rinor.info("Registered {} commands!", commands.size());
        for (Command command : commands) {
            command.buildCommand(command.getCommandBuilder());
            dispatcher.register(command.getCommandBuilder());
        }
    }

    /*@EventListener
    public void onChatMessage(ChatMessageEvent.Client event) {
        final String text = event.getMessage().trim();
        if (text.startsWith(prefix)) {
            String literal = text.substring(1);
            event.cancel();
            mc.inGameHud.getChatHud().addToMessageHistory(text);
            try {
                dispatcher.execute(dispatcher.parse(literal, source));
            } catch (Exception exception) {
                // exception.printStackTrace();
            }
        }
    }*/

    @SuppressWarnings("unchecked")
    private LiteralArgumentBuilder<Object> redirectBuilder(String alias, LiteralCommandNode<?> destination) {
        LiteralArgumentBuilder<Object> literalArgumentBuilder = LiteralArgumentBuilder.literal(alias.toLowerCase()).requires((Predicate<Object>) destination.getRequirement())
                .forward((CommandNode<Object>) destination.getRedirect(), (RedirectModifier<Object>) destination.getRedirectModifier(), destination.isFork())
                .executes((com.mojang.brigadier.Command<Object>) destination.getCommand());
        for (CommandNode<?> child : destination.getChildren()) {
            literalArgumentBuilder.then((CommandNode<Object>) child);
        }
        return literalArgumentBuilder;
    }

    private void register(Command... commands) {
        for (Command command : commands) {
            register(command);
        }
    }

    private void register(Command command) {
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix, int prefixKey) {
        this.prefix = prefix;
        this.prefixKey = prefixKey;
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return dispatcher;
    }

    public CommandSource getSource() {
        return source;
    }
}
