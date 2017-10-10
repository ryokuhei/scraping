package com.mycompany.model;

public enum Path {
	Rank("hit/ranking/1-20/"),
	View("hit/view/");

	private final String path;
	
	private Path(final String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}
