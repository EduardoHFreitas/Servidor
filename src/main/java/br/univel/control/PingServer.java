package br.univel.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import br.univel.model.enums.Solicitacao;

/**
 * Pinger para o servidor
 *
 * @author Eduardo
 *
 */
public final class PingServer {

	private PingServer() {
	}

	/**
	 * Verifica se existe conexao
	 *
	 * @return
	 */
	public static Boolean testarConexao() {
		try (Socket socket = new Socket("localhost", 5000);
				OutputStream output = socket.getOutputStream();
				ObjectOutputStream objOutput = new ObjectOutputStream(output);
				InputStream input = socket.getInputStream();
				ObjectInputStream objInput = new ObjectInputStream(input)) {

			objOutput.writeObject(Solicitacao.PING);
			objOutput.flush();

			return objInput != null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel conexão com o Servidor" + "\n Verifique se o mesmo esta ativo!");
			throw new RuntimeException(e);
		}
	}

}
