package br.univel.model.enums;

public enum Solicitacao {
	INCLUIR   (1),
	ALTERAR   (2),
	CONSULTAR (3),
	EXCLUIR   (4),
	LISTAR    (5);

	private int valor;

	Solicitacao (int valor){
		this.valor = valor;
	}

	public int getValor(){
		return this.valor;
	}
}
