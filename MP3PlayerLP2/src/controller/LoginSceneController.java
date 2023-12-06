package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginSceneController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button exitButton;
	@FXML
	private TextField loginUserName;
	@FXML
	private PasswordField loginSenha;
	@FXML
	private TextField cadastroUserName;
	@FXML
	private PasswordField cadastroSenha;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void logar(ActionEvent event) {
		try{
			User usuario = obterUsuariologado();
			if (AutenticarUsuario(usuario)) {
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
	
	@FXML
	public void cadastrar(ActionEvent event) throws FileNotFoundException {
		String nomeusuario = cadastroUserName.getText();
		String senhausuario = cadastroSenha.getText();
		
		try {
			PrintWriter ps;
			ps = new PrintWriter(new FileWriter("src/template/usuarios.txt", true));
			ps.println(nomeusuario + ":" + senhausuario);
	        ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean AutenticarUsuario(User usuario) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/template/usuarios.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				String data[] = line.split(":", 2);
				
				if (usuario.getLogin().equals(data[0]) && usuario.getSenha().equals(data[1])) {
					reader.close();
					return true;
				}
				
				line = reader.readLine();
			}
			
			reader.close();
			return false;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@FXML
	public void fecharAplicação(ActionEvent event) {
		System.exit(0);
	}
	
	public User obterUsuariologado() {
		User usuario = new User();
		usuario.setLogin(loginUserName.getText());
		usuario.setSenha(loginSenha.getText());
		return usuario;
	}
}
