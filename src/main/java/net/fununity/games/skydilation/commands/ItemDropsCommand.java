package net.fununity.games.skydilation.commands;

import net.fununity.games.skydilation.dropping.ItemDroppingManager;
import net.fununity.games.skydilation.language.TranslationKeys;
import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class ItemDropsCommand extends APICommand {

    public ItemDropsCommand() {
        super("items", "", TranslationKeys.COMMANDS_ITEMDROPPING_USAGE, TranslationKeys.COMMANDS_ITEMDROPPING_DESCRIPTION, "itemdropping", "dropping", "item");
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiPlayer.getLanguage().getTranslation(TranslationKeys.COMMANDS_ITEMDROPPING_TITLE));
        for (Map.Entry<Material, Object[]> entry : ItemDroppingManager.getInstance().getDroppings().entrySet()) {
            stringBuilder.append(entry.getKey().name().toLowerCase()).append(" ").append(SpecialChars.DOUBLE_ARROW_RIGHT).append(" ")
                    .append(((Material)entry.getValue()[0]).name().toLowerCase()).append(" ").append((long) entry.getValue()[1] / 20.0).append("s\n");
        }
        apiPlayer.openBook(new ItemBuilder(Material.WRITTEN_BOOK).addPage(stringBuilder.toString()).craft());
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
