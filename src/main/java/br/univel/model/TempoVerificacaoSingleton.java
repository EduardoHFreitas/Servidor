package br.univel.model;

/**
 * Singleton para tempo de verificação do servidor
 * @author Eduardo
 *
 */
public class TempoVerificacaoSingleton {

	private static TempoVerificacaoSingleton instancia;

	private Long tempoVerificacao = 1L;

	private TempoVerificacaoSingleton() {}

	/**
	 * @return the instancia
	 */
	public static synchronized TempoVerificacaoSingleton getInstancia() {
		if (instancia == null){
			instancia = new TempoVerificacaoSingleton();
		}
		return instancia;
	}

	/**
	 * @return the tempoVerificacao
	 */
	public Long getTempoVerificacao() {
		return tempoVerificacao;
	}

	/**
	 * @param tempoVerificacao the tempoVerificacao to set
	 */
	public TempoVerificacaoSingleton setTempoVerificacao(Long tempoVerificacao) {
		this.tempoVerificacao = tempoVerificacao;
		return this;
	}

}
