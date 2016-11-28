package br.univel.model.dto;

import java.io.Serializable;

public class ObjetoRetorno <T> implements Serializable{

	/**
	 * Serival version
	 */
	private static final long serialVersionUID = 1L;

	private T objetoRetorno;

	public T getObjetoRetorno() {
		return objetoRetorno;
	}

	public ObjetoRetorno setObjetoRetorno(T objetoRetorno) {
		this.objetoRetorno = objetoRetorno;
		return this;
	}

}
