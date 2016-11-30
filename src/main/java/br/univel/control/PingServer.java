package br.univel.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

import javax.swing.JOptionPane;

import br.univel.model.enums.Solicitacao;

public class PingServer {

	public static Boolean testarConexao() {
		Socket socket = null;
		OutputStream output = null;
		InputStream input = null;

		try {
			socket = new Socket("localhost", 5000);

			output = socket.getOutputStream();

			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(Solicitacao.PING );
			objOutput.flush();

			input = socket.getInputStream();
			ObjectInputStream objInput = new ObjectInputStream(input);

			return (objInput != null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel conexão com o Servidor" + "\n Verifique se o mesmo esta ativo!");
			throw new RuntimeException(e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
