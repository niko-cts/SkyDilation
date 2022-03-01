package net.fununity.games.skydilation;

import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.player.APIPlayer;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.bukkit.command.CommandSender;

public class GeneratorCommand extends APICommand {

    public GeneratorCommand() {
        super("skygenerator", "command.generator", "", "");
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] args) {
        SkyDilation.getInstance().getGenerator().generateChunk(apiPlayer.getPlayer().getLocation().getChunk());
        //SkyDilation.getInstance().getSkyDilationGenerator().getOctave()[Integer.parseInt(args[0])] = Double.parseDouble(args[1]);
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {

    }
}
