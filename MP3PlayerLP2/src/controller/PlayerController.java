package controller;
import model.*;
import java.util.*;

public class PlayerController {
	
	private Player player;
	
	public PlayerController() {
		this.player = new Player();
	}
	
	public void adicionarMusica(Musica musica) {
        player.addMusica(musica);
    }
	
	public void adicionarPlaylist(Playlist playlist) {
        player.addPlaylist(playlist);
    }
	
	public void adicionarDiretorio(String diretorio) {
        player.addDiretorio(diretorio);
    }
	
	public List<Playlist> obterPlaylists() {
        return player.getPlaylists();
    }
	
	public List<Musica> obterMusicas() {
        return player.getMusicas();
    }
	
	public List<String> obterDiretorios() {
        return player.getDiretorios();
    }
	
	public void definirUsuarioLogado(User usuario) {
        player.setUsuariologado(usuario);
    }
	
	public User obterUsuarioLogado() {
        return player.getUsuariologado();
    }

	public void tocarMusica() {
		Player p = new Player();
	}
	
	public void logarUsuario() {
		
	}
	
	public void cadastrarUsuario() {
		
	}
}
