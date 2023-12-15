package model;

/**
 * Classe do usuario
 */
public class User implements IUser{

	private String login;
	private String senha;
	
	/**
	 * Construtor
	 */
	public User(){}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
