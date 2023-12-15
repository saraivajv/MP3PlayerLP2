package model;
/**
 * Interface dos usuarios
 */
public interface IUser {
	
	/**
	 * Retorna uma String contendo o login do usuario
	 * @return String contendo o login do usuario
	 */
	public String getLogin();
	
	/**
	 * Define o login do usuario
	 * @param login String contendo o login do usuario
	 */
	public void setLogin(String login);
	
	/**
	 * Retorna uma String contendo a senha do usuario
	 * @return String contendo a senha do usuario
	 */
	public String getSenha();
	
	/**
	 * Define a senha do usuario
	 * @param senha String contendo a senha do usuario
	 */
	public void setSenha(String senha);
}
