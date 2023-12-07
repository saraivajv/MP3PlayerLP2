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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Musica;
import model.Player;
import model.Playlist;
import model.User;

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
	private Button exitButton;
	@FXML
	private ListView<String> listMusicas;
	@FXML
	private ListView<String> listPlaylistAtual;
	@FXML
	private ListView<String> listPlaylists;
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private Timer timer;
	private TimerTask task;
	private boolean running;
	
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

	}
	
	public void DefinirPerfil()
	{
		Username.setText(obterUsuarioLogado().getLogin());
	}
	
	@FXML
	public void tocarMusica(ActionEvent event) {
		beginTimer();
		ArrayList<File> musicas = new ArrayList<File>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/musicas.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				musicas.add(new File(line));
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(listMusicas.getSelectionModel().isEmpty() != true) {
			String musicaselecao = listMusicas.getSelectionModel().getSelectedItem();
			for(File m : musicas) {
				boolean iguais = musicaselecao.equals(m.getName());
				if(iguais == true) {
					media = new Media(m.toURI().toString());
					mediaPlayer = new MediaPlayer(media);
					mediaPlayer.play();
					break;
				}
			}
		}
	}
	
	@FXML
	public void pausarMusica(ActionEvent event) {
		endTimer();
		mediaPlayer.pause();
	}
	
	@FXML
	public void prevMusica(ActionEvent event) {
		if(running) {
			endTimer();
		}
		ArrayList<File> musicas = new ArrayList<File>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/musicas.txt"));
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
		listMusicas.getSelectionModel().selectPrevious();
		String musicaselecao = listMusicas.getSelectionModel().getSelectedItem();
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
	
	public void endTimer() {
		running = false;
		timer.cancel();
	}
	
	@FXML
	public void nextMusica(ActionEvent event) {
		if(running) {
			endTimer();
		}
		ArrayList<File> musicas = new ArrayList<File>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/musicas.txt"));
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
		listMusicas.getSelectionModel().selectNext();;
		String musicaselecao = listMusicas.getSelectionModel().getSelectedItem();
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
	
	@FXML
	public void selectArquivo(ActionEvent event) {
		FileChooser file_chooser = new FileChooser();
		file_chooser.setTitle("Selecione o arquivo a ser adicionado");
		
		FileChooser.ExtensionFilter fileExtensions = 
		new FileChooser.ExtensionFilter("Sound files", "*.mp3", "*.wav", "*.ogg");
		file_chooser.getExtensionFilters().add(fileExtensions);
		File mselecionada = file_chooser.showOpenDialog(null);
		listMusicas.getItems().add(mselecionada.getName());
		
		PrintWriter ps;
		try {
			ps = new PrintWriter(new FileWriter("src/template/musicas.txt", true));
			ps.println(mselecionada.getAbsolutePath());
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
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

	
}
