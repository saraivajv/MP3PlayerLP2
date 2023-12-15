package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.IUser;
import model.User;
import model.VIPUser;

/**
 * Classe responsavel por controlar a cena de login
 */
public class LoginSceneController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private CheckBox vipUserCheckbox;
	@FXML
	private Button exitButton;
	@FXML
	private TextField loginUserName;
	@FXML
	private TextField cadastroUserName;
	@FXML
	private PasswordField loginSenha;
	@FXML
	private PasswordField cadastroSenha;
	
	/**
	 * Construtor
	 */
	public LoginSceneController() {}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Checa se o usuario inserido existe e troca a cena para o player caso exista.
	 * @param event evento
	 */
	@FXML
	public void logar(ActionEvent event) {
		try{
			IUser usuario = AutenticarUsuario(loginUserName.getText(), loginSenha.getText());
			if (usuario != null) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerScene.fxml"));
				root = loader.load();
				
				PlayerSceneController playerSceneController = loader.getController();
				playerSceneController.definirUsuarioLogado(usuario);
				playerSceneController.DefinirPerfil();
				
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} else {
				System.out.println("Usuario não cadastrado ou senha errada");
			}
		} catch (IOException e) {
			System.out.println("Deu errado");
		}
	}
	/**
	 * Cadastra o usuario dentro do arquivo usuarios.txt
	 * @param event evento
	 * @throws FileNotFoundException Caso tenha falhado em escrever no arquivo
	 */
	@FXML
	public void cadastrar(ActionEvent event) throws FileNotFoundException {
		String nomeusuario = cadastroUserName.getText();
		String senhausuario = cadastroSenha.getText();
		String usuarioVip = "0";
		
		if (vipUserCheckbox.isSelected()) {
			usuarioVip = "1";
		}
		
		try {
			PrintWriter ps;
			ps = new PrintWriter(new FileWriter("src/template/usuarios.txt", true));
			ps.println(usuarioVip + ":" + nomeusuario + ":" + senhausuario);
	        ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Indentifica se um usuario foi cadastrado
	 * @param login login do usuario a ser checado
	 * @param senha senha do usuario a ser checado
	 * @return O usuario cadastrado, ou null caso este não tenha sido cadastrado
	 */
	public IUser AutenticarUsuario(String login, String senha) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/usuarios.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				String data[] = line.split(":", 3);
				System.out.println(data[0] + " " + data[1] + " " + data[2] + " ");
				
				if (login.equals(data[1]) && senha.equals(data[2])) {
					reader.close();
					
					IUser usuario;
					
					if (data[0].equals("1")) {
						usuario = new VIPUser();
						System.out.println("checou que é vip");
					} else {
						usuario = new User();
					}
					
					System.out.println(usuario instanceof User);
					usuario.setLogin(login);
					usuario.setSenha(senha);
					
					return usuario;
				}
				
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Fecha a aplicação
	 * @param event evento
	 */
	@FXML
	public void fecharAplicação(ActionEvent event) {
		System.exit(0);
	}
}
