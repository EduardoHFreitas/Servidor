package br.univel.model;

import br.univel.control.ObjectDao;
import br.univel.model.dto.Cliente;
import br.univel.model.enums.SQL;

public class ProcessaRequisicaoCliente<T> {
	private T objetoRetorno = null;

	public T processar(Object objeto) {

		Cliente cliente = (Cliente) objeto;

		if (validarCliente(cliente)) {
			switch (cliente.getRequisicao().getValor()) {
			case 1:
				ObjectDao.getObjectDao().incluir(cliente);
				objetoRetorno = (T) new String("Objeto incluido com sucesso!");
				break;
			case 2:
				ObjectDao.getObjectDao().alterar(cliente);
				objetoRetorno = (T) new String("Objeto alterado com sucesso!");
				break;
			case 3:
				objetoRetorno = (T) ObjectDao.getObjectDao()
						.consultarByQuery(SQL.BUSCAR_CLIENTE.toString() + cliente.getIdCliente());
				break;
			case 4:
				ObjectDao.getObjectDao().excluir(cliente);
				objetoRetorno = (T) new String("Objeto excluido com sucesso!");
				break;
			case 5:
				objetoRetorno = (T) ObjectDao.getObjectDao().listar(SQL.LISTAR_CLIENTES.toString());
				break;
			default:
				break;
			}
		}

		return objetoRetorno;
	}

	private boolean validarCliente(final Cliente cliente) {
		if (cliente.getNomeCliente().isEmpty() || cliente.getDataNascimento().isEmpty()
				|| cliente.getNumeroCPF().isEmpty() || cliente.getNumeroRG().isEmpty()) {
			this.objetoRetorno = (T) new String("Todos os dados deve ser preenchidos!");
			return false;
		}
		return true;
	}

}
