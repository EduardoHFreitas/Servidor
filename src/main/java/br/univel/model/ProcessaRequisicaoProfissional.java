package br.univel.model;

import br.univel.control.ObjectDao;
import br.univel.model.dto.Profissional;
import br.univel.model.enums.SQL;
import br.univel.model.enums.Solicitacao;

/**
 * Processa a requisicao de Profissional
 *
 * @author Eduardo
 *
 */
public class ProcessaRequisicaoProfissional {
	private Object objetoRetorno;

	/**
	 * metodo para processar a request
	 *
	 * @param objeto
	 * @return
	 */
	public Object processar(final Object objeto) {

		Profissional profissional = (Profissional) objeto;

		if (validarProfissional(profissional)) {
			switch (profissional.getRequisicao()) {
			case INCLUIR:
				ObjectDao.getObjectDao().incluir(profissional);
				objetoRetorno = (Object) new String("Profissional incluido com sucesso!");
				break;
			case ALTERAR:
				ObjectDao.getObjectDao().alterar(profissional);
				objetoRetorno = (Object) new String("Profissional alterado com sucesso!");
				break;
			case EXCLUIR:
				ObjectDao.getObjectDao().excluir(profissional);
				objetoRetorno = (Object) new String("Profissional excluido com sucesso!");
				break;
			case LISTAR:
				objetoRetorno = (Object) ObjectDao.getObjectDao().listar(SQL.LISTAR_PROFISSIONAIS.getValor());
				break;
			case LOGIN:
				objetoRetorno = (Object) ObjectDao.getObjectDao()
						.consultarByQuery(SQL.BUSCAR_LOGIN.getValor() + "pro_login = '" + profissional.getLogin()
								+ "' and pro_senha = '" + profissional.getSenha() + "'");
				break;
			default:
				break;
			}
		}
		return objetoRetorno;
	}

	private boolean validarProfissional(final Profissional profissional) {
		boolean retorno = true;
		if (validarAlterarIncluir(profissional)) {
			retorno = false;
		}
		if (validarExcluir(profissional)) {
			retorno = false;
		}

		if (validarLogin(profissional)) {
			retorno = false;
		}
		return retorno;
	}

	private boolean validarLogin(final Profissional profissional) {
		boolean retorno = true;
		if (profissional.getRequisicao().equals(Solicitacao.LOGIN)
				&& (profissional.getLogin().isEmpty() || profissional.getSenha().isEmpty())) {
			this.objetoRetorno = (Object) new String("Login e senha obrigatorios!");
			retorno = false;
		}
		return retorno;
	}

	private boolean validarExcluir(final Profissional profissional) {
		boolean retorno = true;
		if (profissional.getRequisicao().equals(Solicitacao.EXCLUIR) && profissional.getIdProfissional() == 0) {
			this.objetoRetorno = (Object) new String("ID Obrigatorio!");
			retorno = false;
		}
		return retorno;
	}

	private boolean validarAlterarIncluir(final Profissional profissional) {
		boolean retorno = true;
		if ((profissional.getRequisicao().equals(Solicitacao.ALTERAR)
				|| profissional.getRequisicao().equals(Solicitacao.INCLUIR))
				&& (profissional.getNomeProfissional().isEmpty() || profissional.getDataNascimento().isEmpty()
						|| profissional.getLogin().isEmpty() || profissional.getSenha().isEmpty())) {
			this.objetoRetorno = (Object) new String("Todos os dados deve ser preenchidos!");
			retorno = false;
		}
		return retorno;
	}
}
