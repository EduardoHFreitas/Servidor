package br.univel.model;

import br.univel.control.ObjectDao;
import br.univel.model.dto.Profissional;
import br.univel.model.enums.SQL;
import br.univel.model.enums.Solicitacao;

public class ProcessaRequisicaoProfissional {
	private Object objetoRetorno = null;

	public Object processar(Object objeto) {

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

	private boolean validarProfissional(Profissional profissional) {
		if (profissional.getRequisicao().equals(Solicitacao.ALTERAR)
				|| profissional.getRequisicao().equals(Solicitacao.INCLUIR)) {
			if (profissional.getNomeProfissional().isEmpty() || profissional.getDataNascimento().isEmpty()
					|| profissional.getLogin().isEmpty() || profissional.getSenha().isEmpty()) {
				this.objetoRetorno = (Object) new String("Todos os dados deve ser preenchidos!");
				return false;
			}
		} else if (profissional.getRequisicao().equals(Solicitacao.EXCLUIR)) {
			if (profissional.getIdProfissional() == 0) {
				this.objetoRetorno = (Object) new String("ID Obrigatorio!");
				return false;
			}
		} else if (profissional.getRequisicao().equals(Solicitacao.LOGIN)) {
			if (profissional.getLogin().isEmpty() || profissional.getSenha().isEmpty()) {
				this.objetoRetorno = (Object) new String("Login e senha obrigatorios!");
				return false;
			}
		}
		return true;
	}
}
