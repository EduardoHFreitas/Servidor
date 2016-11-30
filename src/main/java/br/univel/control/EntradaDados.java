package br.univel.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import br.univel.model.ProcessaSolicitacaoFactory;

public class EntradaDados implements Runnable {

	private final Socket connection;

	public EntradaDados(final Socket connection) {
		this.connection = connection;
	}

	@Override
	public void run() {
		InputStream input;
		ObjectInputStream objInput = null;
		OutputStream output;
		ObjectOutputStream objOutput = null;
		Object objetoRetorno = null;

		try {
			input = this.connection.getInputStream();
			objInput = new ObjectInputStream(input);
			output = this.connection.getOutputStream();
			objOutput = new ObjectOutputStream(output);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
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
