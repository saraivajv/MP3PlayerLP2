package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.IUser;
import model.Player;
import model.VIPUser;
/**
 * Classe responsavel por controlar a cena do player
 */
public class PlayerSceneController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Player player;
	
	//player
	@FXML
	private Label musicasLabel;
	@FXML
	private Label playlistAtualLabel;
	@FXML
	private Label Username;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button pauseButton;
	@FXML
	private Button playButton;
	@FXML
	private Button previousButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button selectDirectoryButton;
	@FXML
	private Button selectFileButton;
	@FXML
	private Button addPlayistButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button changelistButton;
	@FXML
	private ListView<String> listMusicas;
	@FXML
	private ListView<String> listPlaylistAtual;
	@FXML
	private ListView<String> listPlaylists;
	@FXML
	private TextField addPlaylistName;
	
	private ListView<String> listAtual;
	private String AtualFile = "src/template/musicas.txt";
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private Timer timer;
	private TimerTask task;
	private boolean running;
	
	/**
	 * construtor
	 */
	public PlayerSceneController() {
		this.player = new Player();
	}
	
	/**
	 * Inicia o player
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/musicas.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				File file = new File(line);
				listMusicas.getItems().add(file.getName());
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listAtual = listMusicas;
	}
	
	/**
	 * Exibe o usuario logado, e desabilita funções caso o mesmo não seja vip
	 */
	public void DefinirPerfil()
	{
		IUser usuarioLogado = obterUsuarioLogado();
		Username.setText(usuarioLogado.getLogin());
		listPlaylistAtual.setDisable(true);
		if (!(usuarioLogado instanceof VIPUser)) {
			addPlayistButton.setDisable(true);
			listPlaylists.setDisable(true);
			playlistAtualLabel.setText("Experimente o VIP");
			changelistButton.setDisable(true);
		}
	}
	
	/**
	 * Reproduz a musica selecionada
	 * @param event evento
	 */
	@FXML
	public void tocarMusica(ActionEvent event) {
		ArrayList<File> musicas = new ArrayList<File>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(AtualFile));
			String line = reader.readLine();
			
			while (line != null) {
				musicas.add(new File(line));
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if(listAtual.getSelectionModel().isEmpty() != true) {
			String musicaselecao = listAtual.getSelectionModel().getSelectedItem();
			for(File m : musicas) {
				boolean iguais = musicaselecao.equals(m.getName());
				if(iguais == true) {
					media = new Media(m.toURI().toString());
					beginTimer();
					mediaPlayer = new MediaPlayer(media);
					mediaPlayer.play();
					break;
				}
			}
		}
	}
	
	/**
	 * pausa a musica reproduzida
	 * @param event evento
	 */
	@FXML
	public void pausarMusica(ActionEvent event) {
		endTimer();
		mediaPlayer.pause();
	}
	
	/**
	 * Troca a musica selecionada e reproduzida para a anterior
	 * @param event evento
	 */
	@FXML
	public void prevMusica(ActionEvent event) {
		if(running) {
			endTimer();
		}
		ArrayList<File> musicas = new ArrayList<File>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(AtualFile));
			String line = reader.readLine();
			
			while (line != null) {
				musicas.add(new File(line));
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		mediaPlayer.pause();
		listAtual.getSelectionModel().selectPrevious();
		String musicaselecao = listAtual.getSelectionModel().getSelectedItem();
		for(File m : musicas) {
			boolean iguais = musicaselecao.equals(m.getName());
			if(iguais == true) {
				media = new Media(m.toURI().toString());
				mediaPlayer = new MediaPlayer(media);
				mediaPlayer.play();
				beginTimer();
				break;
			}
		}
	}
	
	/**
	 * inicia o timer da musica reproduzida;
	 */
	public void beginTimer() {
		timer = new Timer();
		
		task = new TimerTask() {
			public void run() {
				running = true;
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				progressBar.setProgress(current/end);
				
				if(current/end == 1) {
					endTimer();
				}
			}
		};
		
		timer.schedule(task, 500, 1000);
		
	}
	
	/**
	 * Finaliza o timer da musica reproduzida
	 */
	public void endTimer() {
		running = false;
		timer.cancel();
	}
	
	
	/**
	 * Troca a musica selecionada e reproduzida para a proxima
	 * @param event evento
	 */
	@FXML
	public void nextMusica(ActionEvent event) {
		if(running) {
			endTimer();
		}
		ArrayList<File> musicas = new ArrayList<File>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(AtualFile));
			String line = reader.readLine();
			
			while (line != null) {
				musicas.add(new File(line));
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		mediaPlayer.pause();
		listAtual.getSelectionModel().selectNext();;
		String musicaselecao = listAtual.getSelectionModel().getSelectedItem();
		for(File m : musicas) {
			boolean iguais = musicaselecao.equals(m.getName());
			if(iguais == true) {
				media = new Media(m.toURI().toString());
				mediaPlayer = new MediaPlayer(media);
				mediaPlayer.play();
				beginTimer();
				break;
			}
		}
	}
	
	/**
	 * Atualiza a lista de musicas na playlist
	 */
	public void atualizarPlaylist() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/playlist_" + listPlaylists.getSelectionModel().getSelectedItem() + ".txt"));
			String line = reader.readLine();
			reader.readLine();
			reader.readLine();
			listPlaylistAtual.getItems().clear();
			
			while (line != null) {
				File file = new File(line);
				listPlaylistAtual.getItems().add(file.getName());
				line = reader.readLine();
			}
			System.out.println("atualizado");
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Altera entre escutar a musica da playlist ou escutar musica da tabela de musicas
	 * @param event evento
	 */
	@FXML
	public void TrocarList(ActionEvent event) {
		if (mediaPlayer != null) {
			endTimer();
			mediaPlayer.pause();	
		}
		
		if (AtualFile.equals("src/template/musicas.txt")) {
			listAtual = listPlaylistAtual;
			AtualFile = "src/template/playlist_" + listPlaylists.getSelectionModel().getSelectedItem() + ".txt";
			changelistButton.setText("Musica");
			
			listPlaylistAtual.setDisable(false);
			listMusicas.setDisable(true);
			
			System.out.println("Teste Musica");
		} else {
			listAtual = listMusicas;
			AtualFile = "src/template/musicas.txt";
			changelistButton.setText("Playlist");
			listPlaylistAtual.setDisable(true);
			listMusicas.setDisable(false);
			
			System.out.println("Teste Playlist");
		}
	}
	
	/**
	 * Abre uma aba para o usuario selecionar uma musica ou playlist e a adiciona a lista de musica ou playlist
	 * @param event evento
	 */
	@FXML
	public void selectArquivo(ActionEvent event) {
		FileChooser file_chooser = new FileChooser();
		file_chooser.setTitle("Selecione o arquivo a ser adicionado");
		
		FileChooser.ExtensionFilter fileExtensions = 
		new FileChooser.ExtensionFilter("Sound and playlist files", "*.mp3", "*.wav", "*.ogg", "*.txt");
		file_chooser.getExtensionFilters().add(fileExtensions);
		File mselecionada = file_chooser.showOpenDialog(null);
		
		PrintWriter ps;
		if (getFileExtension(mselecionada).equals(".txt") && mselecionada.getName().contains("playlist_")) {
			listPlaylists.getItems().add(mselecionada.getName());
		}
		else if (!getFileExtension(mselecionada).equals(".txt")){
			listMusicas.getItems().add(mselecionada.getName());
			try {
				ps = new PrintWriter(new FileWriter("src/template/musicas.txt", true));
				ps.println(mselecionada.getAbsolutePath());
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Arquivo não suportado");
		}
	}
	
	/**
	 * Abre uma aba para o usuario selecionar uma pasta e adiciona seu conteudo a aplicação
	 * @param event evento
	 */
	@FXML
	public void selectDiretorio(ActionEvent event) {
		
		File diretorioadicionado;
		DirectoryChooser directory_chooser = new DirectoryChooser();
		directory_chooser.setTitle("Selecione o diretorio a ser adicionado");
		diretorioadicionado = directory_chooser.showDialog(stage);		
		PrintWriter ps;
		try {
			ps = new PrintWriter(new FileWriter("src/template/diretorios.txt", true));
			ps.println(diretorioadicionado.getAbsolutePath());
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		File listamusicas[] = diretorioadicionado.listFiles();
		for (File m : listamusicas) {
			String fileExtension = m.getName().split("\\.", 2)[1];
			
			if (fileExtension.equals("mp3") || fileExtension.equals("wav") || fileExtension.equals("ogg")) {
				listMusicas.getItems().add(m.getName());
				try {
					ps = new PrintWriter(new FileWriter("src/template/musicas.txt", true));
					ps.println(m.getAbsolutePath());
					ps.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * Abre uma aba para o usuario selecionar uma musica e a adiciona na playlist atual
	 * @param event evento
	 */
	@FXML
	public void adicionarMusicaPlaylist(ActionEvent event) {
		FileChooser file_chooser = new FileChooser();
		file_chooser.setTitle("Selecione o arquivo a ser adicionado");
		
		FileChooser.ExtensionFilter fileExtensions = 
		new FileChooser.ExtensionFilter("Sound files", "*.mp3", "*.wav", "*.ogg");
		file_chooser.getExtensionFilters().add(fileExtensions);
		File mselecionada = file_chooser.showOpenDialog(null);
		listPlaylistAtual.getItems().add(mselecionada.getName());
		try {
			PrintWriter ps = new PrintWriter(new FileWriter("src/template/playlist_" + listPlaylists.getSelectionModel().getSelectedItem() + ".txt", true));
			ps.println(mselecionada.getAbsolutePath());
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * retoma para a tela de login
	 * @param event evento
	 */
	@FXML
	public void deslogar(ActionEvent event) {
		try{
			root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * adiciona uma nova playlist
	 */
	@FXML
	public void adicionarPlaylist() {
		String playlistName = addPlaylistName.getText();
		listPlaylists.getItems().add(playlistName);
		
		try {
			PrintWriter ps = new PrintWriter(new FileWriter("src/template/playlist_" + playlistName + ".txt", true));
			ps.println(obterUsuarioLogado().getLogin() + ":" + obterUsuarioLogado().getSenha());
			ps.println();
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Retorna o usuario logado
	 * @return o usuario logado
	 */
	public IUser obterUsuarioLogado() {
        return player.getUsuariologado();
    }
	
	/**
	 * define o usuario logado
	 * @param usuario usuario logado
	 */
	public void definirUsuarioLogado(IUser usuario) {
		player.setUsuariologado(usuario);
	}
	
	/** 
	 * indentifica a extensão de um arquivo
	 * @param file Arquivo
	 * @return String contendo a extensão
	 */
	private String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}

	
}
