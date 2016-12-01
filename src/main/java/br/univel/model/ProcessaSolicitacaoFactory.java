package br.univel.model;

import br.univel.control.HibernateUtil;
import br.univel.model.dto.Cliente;
import br.univel.model.dto.Profissional;
import br.univel.model.enums.Solicitacao;

/**
 * Factory de processamento de solicitacoes
 *
 * @author Eduardo
 *
 */
public class ProcessaSolicitacaoFactory {

	/**
	 * metodo para processar a request
	 * @param objeto
	 * @return
	 */
	public Object processar(final Object objeto) {
		Object retorno = new Object();
		HibernateUtil.getSession();
		if (objeto.getClass().equals(Cliente.class)) {
			retorno = new ProcessaRequisicaoCliente().processar(objeto);
		} else if (objeto.getClass().equals(Profissional.class)) {
			retorno = new ProcessaRequisicaoProfissional().processar(objeto);
		}
		if (objeto.getClass().equals(Solicitacao.class)) {
			retorno = new String("Ok");
		}
		return retorno;
	}
}
