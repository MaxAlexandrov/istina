package ru.aleksandrov.news;


public class Photo {

	private int pid;
	private int aid;
	private int owner_id;
	private String src;
	private String src_big;
	private String src_small;
	private String src_xbig;

	public String getSrc() {
		return this.src;
	}

	public String getSrcBig() {
		return this.src_big;
	}

	public String getSrcXBig() {
		return this.src_small;
	}

	public String getSrcSmall() {
		return this.src_small;
	}

}
