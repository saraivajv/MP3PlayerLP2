package model;
import java.util.*;

public class VIPUser extends User {

	private List<Playlist> playlists;
	
	public void addPlaylist(Playlist p) {
		this.playlists.add(p);
	}
	
	public List<Playlist> getPlaylists(){
		return this.playlists;
	}
}
