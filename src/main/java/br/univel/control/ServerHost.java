package br.univel.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHost {

	private final ExecutorService pool = Executors.newFixedThreadPool(10);
	private ServerSocket servidor;

	public void IniciarServidor() throws IOException {
		setServidor(new ServerSocket(5000));
		while (!getServidor().isClosed()) {
			Socket conexao = getServidor().accept();
			pool.submit(new EntradaDados(conexao));
		}
		pool.shutdown();
	}

	public ServerSocket getServidor() {
		return servidor;
	}

	public void setServidor(ServerSocket servidor) {
		this.servidor = servidor;
	}
}
