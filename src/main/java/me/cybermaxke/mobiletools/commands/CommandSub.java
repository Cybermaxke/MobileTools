/**
 * 
 * This software is part of the MobileTools
 * 
 * MobileTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * MobileTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MobileTools. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.mobiletools.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class CommandSub extends Command {
	private final Map<String, Command> commands = new HashMap<String, Command>();

	public CommandSub(String name) {
		super(name);

		this.addSubCommand(new Command("help") {

			{
				this.setDescription("Shows the available sub commands with a description.");
			}

			@Override
			public boolean execute(CommandSender sender, String label, String[] args) {
				String name1 = this.getName();
				StringBuilder builder1 = new StringBuilder();

				int amount = (60 - name1.length() - 2) / 2;
				for (int i = -amount; i < amount; i++) {
					builder1.append('-');

					if (i == 0) {
						builder1.append(' ');
						builder1.append(name1);
						builder1.append(' ');
					}
				}

				sender.sendMessage(builder1.toString());

				for (Command command : CommandSub.this.commands.values()) {
					String name2 = command.getName();
					String description2 = command.getDescription();

					sender.sendMessage("'/" + name1 + " " + name2 + "' - " + description2);
				}

				return true;
			}

		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args[0].length() == 0) {
			sender.sendMessage("This command uses sub commands, use '/" + this.getName() +
					" help' to show them.");
			return true;
			
		}

		if (!this.commands.containsKey(args[0])) {
			sender.sendMessage("This sub command doesn't exists, use '/" + this.getName() +
					" help' to show them.");
			return true;
		}

		Command subCommand = this.commands.get(args[0]);
		Permission permission = subCommand.getCommandPermission();

		if (!sender.hasPermission(permission)) {
			sender.sendMessage(subCommand.getPermissionMessage());
			return true;
		}

		String[] args1 = Arrays.copyOfRange(args, 1, args.length);
		return this.commands.get(args[0]).execute(sender, label, args1);
	}

	public void addSubCommand(Command command) {
		this.commands.put(command.getName(), command);
	}
}