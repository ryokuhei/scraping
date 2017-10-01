package com.mycompany;

public enum Path {
	Rank("ranking/1-20"),
	View("view/");

	private final String path;
	
	private Path(final String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}
