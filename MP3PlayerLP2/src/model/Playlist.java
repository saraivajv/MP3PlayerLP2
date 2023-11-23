package model;
import java.util.*;

public class Playlist {

	private List<Musica> musicas;
	private String nome;
	private User dono;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public User getDono() {
		return dono;
	}
	public void setDono(User dono) {
		this.dono = dono;
	}
	
	public void addMusica(Musica m) {
		this.musicas.add(m);
	}
	
	public List<Musica> getMusicas() {
		return this.musicas;
	}
}
