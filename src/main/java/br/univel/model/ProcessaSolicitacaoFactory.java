package br.univel.model;

import br.univel.control.HibernateUtil;
import br.univel.model.dto.Cliente;
import br.univel.model.dto.Profissional;

public class ProcessaSolicitacaoFactory {

	public Object processar(final Object objeto) {
		HibernateUtil.getSession();
		if (objeto.getClass().equals(Cliente.class)) {
			return new ProcessaRequisicaoCliente().processar(objeto);
		} else if (objeto.getClass().equals(Profissional.class)) {
			return new ProcessaRequisicaoProfissional().processar(objeto);
		}
		return new Object();
	}
}
