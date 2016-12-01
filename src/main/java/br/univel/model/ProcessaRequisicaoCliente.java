package br.univel.model;

import br.univel.control.ObjectDao;
import br.univel.model.dto.Cliente;
import br.univel.model.enums.SQL;
import br.univel.model.enums.Solicitacao;

/**
 * classe para processar a requisicao do cliente
 *
 * @author Eduardo
 *
 */
public class ProcessaRequisicaoCliente {
	private Object objetoRetorno;

	/**
	 * processa a requisicao do cliente
	 *
	 * @param objeto
	 * @return
	 */
	public Object processar(final Object objeto) {

		Cliente cliente = (Cliente) objeto;

		if (validarCliente(cliente)) {
			switch (cliente.getRequisicao()) {
			case INCLUIR:
				ObjectDao.getObjectDao().incluir(cliente);
				objetoRetorno = (Object) new String("Cliente incluido com sucesso!");
				break;
			case ALTERAR:
				ObjectDao.getObjectDao().alterar(cliente);
				objetoRetorno = (Object) new String("Cliente alterado com sucesso!");
				break;
			case EXCLUIR:
				ObjectDao.getObjectDao().excluir(cliente);
				objetoRetorno = (Object) new String("Cliente excluido com sucesso!");
				break;
			case LISTAR:
				objetoRetorno = (Object) ObjectDao.getObjectDao().listar(SQL.LISTAR_CLIENTES.getValor());
				break;
			default:
				break;
			}
		}

		return objetoRetorno;
	}

	private boolean validarCliente(final Cliente cliente) {
		boolean retorno = true;
		if (cliente.getRequisicao().equals(Solicitacao.ALTERAR)
				|| cliente.getRequisicao().equals(Solicitacao.INCLUIR)) {
			if (cliente.getNomeCliente().isEmpty() || cliente.getDataNascimento().isEmpty()
					|| cliente.getNumeroCPF().isEmpty() || cliente.getNumeroRG().isEmpty()) {
				this.objetoRetorno = (Object) new String("Todos os dados deve ser preenchidos!");
				retorno = false;
			}
		} else if (cliente.getRequisicao().equals(Solicitacao.EXCLUIR) && cliente.getIdCliente() == 0) {
			this.objetoRetorno = (Object) new String("ID Obrigatorio!");
			retorno = false;
		}
		return retorno;
	}

}
