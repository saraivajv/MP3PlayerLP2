package model;
import java.util.*;

public class Player {

	private List<Playlist> playlists;
	private List<Musica> musicas;
	private List<String> diretorios;
	private List<User> usuarios;
	private User usuariologado;
	
	public User getUsuariologado() {
		return usuariologado;
	}
	public void setUsuariologado(User usuariologado) {
		this.usuariologado = usuariologado;
	}
	
	public void addMusica(Musica m) {
		this.musicas.add(m);
	}
	
	public void addPlaylist(Playlist p) {
		this.playlists.add(p);
	}
	
	public void addDiretorio(String d) {
		this.diretorios.add(d);
	}
	public void addUsuario(User u) {
		this.usuarios.add(u);
	}
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public List<Musica> getMusicas() {
		return musicas;
	}
	
	public List<String> getDiretorios() {
		return diretorios;
	}
	public List<User> getUsuarios() {
		return usuarios;
	}
	
}
