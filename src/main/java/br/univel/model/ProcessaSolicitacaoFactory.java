package br.univel.model;

import br.univel.control.HibernateUtil;
import br.univel.model.dto.Cliente;
import br.univel.model.dto.Profissional;
import br.univel.model.enums.Solicitacao;

public class ProcessaSolicitacaoFactory {

	public Object processar(final Object objeto) {
		HibernateUtil.getSession();
		if (objeto.getClass().equals(Cliente.class)) {
			return new ProcessaRequisicaoCliente().processar(objeto);
		} else if (objeto.getClass().equals(Profissional.class)) {
			return new ProcessaRequisicaoProfissional().processar(objeto);
		} if (objeto.getClass().equals(Solicitacao.class)){
			return new String("Ok");
		}
		return new Object();
	}
}
