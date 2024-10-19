package application.model.credentials;

public enum UserRole {
	ADMIN,
	DOCTOR,
	NURSE,
	NONE;
	
	@Override
	public String toString() {
		String name = name();
		return this.toTitleCase(name);
	}
	
	private String toTitleCase(String input) {
		StringBuilder titleCase = new StringBuilder();
		boolean nextTitleCase = true;
		
		for (char character : input.toCharArray()) {
			if (Character.isSpaceChar(character)) {
				nextTitleCase = true;
			} else if (nextTitleCase) {
				character = Character.toTitleCase(character);
				nextTitleCase = false;
			}

			titleCase.append(character);
		}
		return titleCase.toString();
	}
	
	
}
