package br.univel.model;

import br.univel.control.HibernateUtil;
import br.univel.model.dto.Cliente;
import br.univel.model.dto.Profissional;

public class ProcessaSolicitacao<T> {

	public T processar(final T objeto) {
		HibernateUtil.getSession();
		if (objeto.getClass().equals(Cliente.class)) {
			return (T) new ProcessaRequisicaoCliente().processar(objeto);
		} else if (objeto.getClass().equals(Profissional.class)) {
			return (T) new ProcessaRequisicaoProfissional().processar(objeto);
		}

		return (T) new Object();
	}
}
