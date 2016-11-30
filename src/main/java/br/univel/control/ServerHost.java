package br.univel.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import br.univel.model.TempoVerificacaoSingleton;

public class ServerHost extends Observable{
	private static ServerHost instancia;
	private final ExecutorService pool = Executors.newFixedThreadPool(10);
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ServerSocket servidor;

	private ServerHost () {}

	public void criarSocket() {
		try {
			setServidor(new ServerSocket(5000));
			while (!getServidor().isClosed()) {
				Socket conexao = null;
				conexao = getServidor().accept();
				pool.submit(new EntradaDados(conexao));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			pool.shutdown();
			try {
				if (getServidor() != null) {
					getServidor().close();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro fechar conexao com o servidor!");
				throw new RuntimeException(e);
			}
		}
	}

	public void start() throws IOException {

		this.executor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				criarSocket();
			}
		}, 0, TempoVerificacaoSingleton.getInstancia().getTempoVerificacao(), TimeUnit.SECONDS);
	}

	public void shutdown(){
		if (getServidor() != null && !getServidor().isClosed()) {
			try {
				getServidor().close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public ServerSocket getServidor() {
		return servidor;
	}

	public void setServidor(ServerSocket servidor) {
		this.servidor = servidor;
	}

	/**
	 * @return the instancia
	 */
	public static synchronized ServerHost getInstancia() {
		if (instancia == null){
			instancia = new ServerHost();
		}
		return instancia;
	}


}
