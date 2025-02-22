package banking;

import java.util.ArrayList;
import java.util.List;

public class ValidCommandStorage {

	private List<String> validCommands;

	public ValidCommandStorage() {
		this.validCommands = new ArrayList<>();
	}

	public void addValidCommand(String command) {
		this.validCommands.add(command);
	}

	public List<String> getValidCommands() {
		return new ArrayList<>(validCommands);
	}
};