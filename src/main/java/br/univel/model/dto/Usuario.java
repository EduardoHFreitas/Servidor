/**
 *
 */
package br.univel.model.dto;

import java.io.Serializable;

/**
 * Classe persistida que contem as informações do usuario logado
 *
 * @author Eduardo
 *
 */
public class Usuario implements Serializable {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	private String loginUsuario;
	private String senhaUsuario;

	/**
	 * Construtor de usuario com parametros
	 *
	 * @param loginUsuario
	 * @param senhaUsuario
	 */
	public Usuario(final String loginUsuario, final String senhaUsuario) {
		this.loginUsuario = loginUsuario;
		this.senhaUsuario = senhaUsuario;
	}

	/**
	 * @return the login
	 */
	public String getLoginUsuario() {
		return loginUsuario;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public Usuario setLoginUsuario(final String login) {
		loginUsuario = login;
		return this;
	}

	/**
	 * @return the senha
	 */
	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public Usuario setSenhaUsuario(final String senha) {
		senhaUsuario = senha;
		return this;
	}

}
