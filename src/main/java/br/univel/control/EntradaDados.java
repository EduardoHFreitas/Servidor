package br.univel.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import br.univel.model.ProcessaSolicitacaoFactory;

/**
 * Realiza a leitura e escrita
 *
 * @author Eduardo
 *
 */
public class EntradaDados implements Runnable {

	private final Socket connection;

	public EntradaDados(final Socket connection) {
		this.connection = connection;
	}

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
			e.printStackTrace();
		} finally {
			if (!this.connection.isClosed()) {
				try {
					this.connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
