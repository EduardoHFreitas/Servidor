package br.univel.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import br.univel.model.ProcessaSolicitacaoFactory;

/**
 * Realiza a leitura e escrita
 *
 * @author Eduardo
 *
 */
public class EntradaDados implements Runnable {

	private final Socket connection;

	/**
	 *
	 * Construtor
	 *
	 * @param connection
	 */
	public EntradaDados(final Socket connection) {
		this.connection = connection;
	}

	/**
	 * Metodo executor da thread
	 */
	@Override
	public void run() {
		Object objetoRetorno = null;

		try (InputStream input = this.connection.getInputStream();
				ObjectInputStream objInput = new ObjectInputStream(input);
				OutputStream output = this.connection.getOutputStream();
				ObjectOutputStream objOutput = new ObjectOutputStream(output)) {

			Object object = (Object) objInput.readObject();
			ProcessaSolicitacaoFactory processador = new ProcessaSolicitacaoFactory();

			objetoRetorno = (Object) processador.processar(object);

			objOutput.writeObject(objetoRetorno);
			objOutput.flush();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {

			closeConnection();
		}
	}

	private void closeConnection() {
		if (!this.connection.isClosed()) {
			try {
				this.connection.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
