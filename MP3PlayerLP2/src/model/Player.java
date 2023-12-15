package model;

/**
 * Classe do Player
 */
public class Player {

	private IUser usuariologado;
	
	/**
	 * Construtor
	 */
	public Player() {}
	
	/**
	 * Retorna o usuario logado
	 * @return O usuario logado
	 */
	public IUser getUsuariologado() {
		return usuariologado;
	}
	
	/**
	 * Define o usuario logado
	 * @param usuariologado o Usuario logado
	 */
	public void setUsuariologado(IUser usuariologado) {
		this.usuariologado = usuariologado;
	}
}
