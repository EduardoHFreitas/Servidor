package br.univel.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import br.univel.model.ProcessaSolicitacao;
import br.univel.model.dto.ObjetoRetorno;

public class EntradaDados <T> implements Runnable {

	private final Socket connection;

	public EntradaDados(final Socket connection) {
		this.connection = connection;
	}

	@Override
	public void run() {

		ObjetoRetorno objetoRetorno = null;
		try (InputStream input = this.connection.getInputStream();
				ObjectInputStream objInput = new ObjectInputStream(input);
				OutputStream output = this.connection.getOutputStream();
				ObjectOutputStream objOutput = new ObjectOutputStream(output)) {
			JOptionPane.showMessageDialog(null, objetoRetorno.getClass());
			T object = (T) objInput.readObject();

			ProcessaSolicitacao processador = new ProcessaSolicitacao();
			objetoRetorno = (ObjetoRetorno) processador.processar(object);

			JOptionPane.showMessageDialog(null, objetoRetorno.getClass());
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
