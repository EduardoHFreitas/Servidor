package br.univel.model;

import br.univel.control.ObjectDao;
import br.univel.model.dto.Profissional;
import br.univel.model.enums.SQL;

public class ProcessaRequisicaoProfissional<T> {
	private T objetoRetorno = null;

	public T processar(T objeto) {

		Profissional profissional = (Profissional) objeto;

		if (!validarProfissional(profissional)) {
			switch (profissional.getRequisicao().getValor()) {
			case 1:
				ObjectDao.getObjectDao().incluir(profissional);
				objetoRetorno = (T) new String("Profissional incluido com sucesso!");
				break;
			case 2:
				ObjectDao.getObjectDao().alterar(profissional);
				objetoRetorno = (T) new String("Profissional alterado com sucesso!");
				break;
			case 3:
				objetoRetorno = (T) ObjectDao.getObjectDao()
						.consultarByQuery(SQL.BUSCAR_PROFISSIONAL.toString() + profissional.getIdProfissional());
				break;
			case 4:
				ObjectDao.getObjectDao().excluir(profissional);
				objetoRetorno = (T) new String("Profissional excluido com sucesso!");
				break;
			case 5:
				objetoRetorno = (T) ObjectDao.getObjectDao().listar(SQL.LISTAR_PROFISSIONAIS.toString());
				break;
			default:
				break;
			}
		}
		return objetoRetorno;
	}

	private boolean validarProfissional(Profissional profissional) {
		if (profissional.getNomeProfissional().isEmpty() || profissional.getDataNascimento().isEmpty()
				|| profissional.getLogin().isEmpty() || profissional.getSenha().isEmpty()) {
			this.objetoRetorno = (T) new String("Todos os dados deve ser preenchidos!");
			return false;
		}
		return true;
	}
}
