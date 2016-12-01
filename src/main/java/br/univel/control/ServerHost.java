package br.univel.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import br.univel.model.TempoVerificacaoSingleton;

/**
 * Criar um Servidor socket
 *
 * @author Eduardo
 *
 */
public final class ServerHost extends Observable {
	private static ServerHost instancia;

	private final ExecutorService pool = Executors.newFixedThreadPool(10);
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	private ServerSocket servidor;
	public ScheduledFuture<?> schedule;

	private ServerHost() {
	}

	/**
	 * Cria o socket
	 */
	public void criarSocket() {
		try {
			setServidor(new ServerSocket(5000));
			while (!getServidor().isClosed()) {
				Socket conexao = null;
				setChanged();
				notifyObservers();
				conexao = getServidor().accept();
				pool.submit(new EntradaDados(conexao));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			desligarServidor();
		}
	}

	/**
	 * fechar conexao servidor
	 */
	private void desligarServidor() {
		executor.shutdown();
		try {
			if (getServidor() != null) {
				getServidor().close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro fechar conexao com o servidor!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Startar thread
	 *
	 * @throws IOException
	 */
	public void start() throws IOException {
		new Thread(() -> {
			criarSocket();
		}).start();
		schedule = this.executor.scheduleAtFixedRate(new Runnable() {

			/**
			 * Executor da classe
			 */
			@Override
			public void run() {
				PingServer.testarConexao();
			}
		}, 0, TempoVerificacaoSingleton.getInstancia().getTempoVerificacao(), TimeUnit.SECONDS);
	}

	/**
	 * Reiniciar servidor
	 */
	public void reiniciar() {
		if (getServidor() != null && !getServidor().isClosed()) {
			try {
				schedule.cancel(true);
				getServidor().close();
				setChanged();
				notifyObservers();
				start();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Retornar atributo da classe
	 *
	 * @return
	 */
	public ServerSocket getServidor() {
		return servidor;
	}

	/**
	 * Setar servidor ao atributo
	 *
	 * @param servidor
	 */
	public void setServidor(final ServerSocket servidor) {
		this.servidor = servidor;
	}

	/**
	 * @return the instancia
	 */
	public static synchronized ServerHost getInstancia() {
		if (instancia == null) {
			instancia = new ServerHost();
		}
		return instancia;
	}

}
