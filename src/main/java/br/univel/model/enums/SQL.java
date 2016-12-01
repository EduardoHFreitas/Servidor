package br.univel.model.enums;

public enum SQL {

	LISTAR_CLIENTES("from Cliente"),
	LISTAR_PROFISSIONAIS("from Profissional"),
	BUSCAR_CLIENTE("from Cliente where cli_idCliente = "),
	BUSCAR_PROFISSIONAL("from Profissional where pro_idProfissional = "),
	BUSCAR_LOGIN("from Profissional where ");

	private String valor;

	private SQL (final String valor){
		this.valor = valor;
	}

	/**
	 * Retorna o valor da enum
	 * @return
	 */
	public String getValor(){
		return valor;
	}
}
