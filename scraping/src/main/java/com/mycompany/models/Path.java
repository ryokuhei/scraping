package com.mycompany.models;

public enum Path {
	Rank("hit/ranking/1-20/"),
	View("hit/view/"),
	Sort("hit/index_sort/");  //Sort == 分類

	private final String path;
	
	private Path(final String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
}
