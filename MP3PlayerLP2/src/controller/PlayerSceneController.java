package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ProgressBar;
import model.Musica;
import model.Player;
import model.Playlist;
import model.User;

public class PlayerSceneController {
	@FXML
	private Button playButton;
	@FXML
	private Label musicasLabel;
	@FXML
	private Label playlistAtualLabel;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button pauseButton;
	@FXML
	private Button previousButton;
	@FXML
	private Button nextButton;

	// Event Listener on Button[#playButton].onAction
	@FXML
	public void tocarMusica(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#pauseButton].onAction
	@FXML
	public void pausarMusica(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#previousButton].onAction
	@FXML
	public void prevMusica(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#nextButton].onAction
	@FXML
	public void nextMusica(ActionEvent event) {
		// TODO Autogenerated
	}
	
private Player player;
	
	public PlayerSceneController() {
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
	
//	public void logarUsuario() {
//		
//	}
//	
//	public void cadastrarUsuario() throws IOException{
//		FileReader fr = new FileReader("template/usuarios");
//		BufferedReader br = new BufferedReader(fr);
//		String login;
//		while((login = br.readLine()) != null){
//			String senha = br.readLine();
//			User u = new User();
//			u.setLogin(login);
//			u.setSenha(senha);
//			player.addUsuario(u);
//		}
//	}
	
}