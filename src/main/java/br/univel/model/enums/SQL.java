package br.univel.model.enums;

public enum SQL {

	LISTAR_CLIENTES("select * from cliente"),
	LISTAR_PROFISSIONAIS("select * from profissional"),
	BUSCAR_CLIENTE("select * from cliente where cli_idCliente = "),
	BUSCAR_PROFISSIONAL("select * from profissional where pro_idProfissional = ");

	private String valor;

	private SQL (String valor){
		this.valor = valor;
	}

	private String getValor(){
		return valor;
	}
}
