package ru.aleksandrov.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Wall {

	private int totalPostsCount;
	private List<Post> posts = new ArrayList<Post>();
	ArrayList<HashMap<String, String>> arrayPosts = new ArrayList<HashMap<String, String>>();

	public void addPost(Post post) {
		this.posts.add(post);
	}
	
	public List<Post> getPosts() {
		return this.posts;
	}
	
	public int getTotalPostsCount() {
		return this.totalPostsCount;
	}

}
