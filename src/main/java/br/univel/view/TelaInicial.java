package br.univel.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.management.RuntimeErrorException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.univel.control.HibernateUtil;
import br.univel.control.ServerHost;
import br.univel.model.TempoVerificacaoSingleton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaInicial extends JFrame {
	private JTextField tfTempo;
	private JTextField textField;

	public TelaInicial() {
		HibernateUtil.getSession();

		getContentPane().setFont(getContentPane().getFont().deriveFont(11f));
		setBounds(100, 100, 300, 150);
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 13));
		setType(Type.POPUP);
		setResizable(false);
		setTitle("Monitoramento do SERVIDOR");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblServidorOnline = new JLabel("Servidor On-line?");
		GridBagConstraints gbc_lblServidorOnline = new GridBagConstraints();
		gbc_lblServidorOnline.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblServidorOnline.insets = new Insets(0, 0, 5, 5);
		gbc_lblServidorOnline.gridx = 1;
		gbc_lblServidorOnline.gridy = 1;
		getContentPane().add(lblServidorOnline, gbc_lblServidorOnline);

		textField = new JTextField();
		textField.setBackground(Color.GREEN);
		textField.setEditable(false);
		textField.setEnabled(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblTempoParaVerifica = new JLabel("Verificar a cada (segundos)?");
		GridBagConstraints gbc_lblTempoParaVerifica = new GridBagConstraints();
		gbc_lblTempoParaVerifica.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblTempoParaVerifica.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempoParaVerifica.gridx = 1;
		gbc_lblTempoParaVerifica.gridy = 2;
		getContentPane().add(lblTempoParaVerifica, gbc_lblTempoParaVerifica);

		tfTempo = new JTextField();
		tfTempo.setText("1");
		GridBagConstraints gbc_tfTempo = new GridBagConstraints();
		gbc_tfTempo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTempo.insets = new Insets(0, 0, 5, 5);
		gbc_tfTempo.anchor = GridBagConstraints.NORTH;
		gbc_tfTempo.gridx = 2;
		gbc_tfTempo.gridy = 2;
		getContentPane().add(tfTempo, gbc_tfTempo);
		tfTempo.setColumns(10);

		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ServerHost.getInstancia().shutdown();
					ServerHost.getInstancia().start();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erro ao reiniciar servidor!");
					throw new RuntimeException(e);
				}
			}
		});
		GridBagConstraints gbc_btnReiniciar = new GridBagConstraints();
		gbc_btnReiniciar.insets = new Insets(0, 0, 5, 5);
		gbc_btnReiniciar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnReiniciar.gridx = 2;
		gbc_btnReiniciar.gridy = 3;
		getContentPane().add(btnReiniciar, gbc_btnReiniciar);

		TempoVerificacaoSingleton.getInstancia().setTempoVerificacao(tempoVerificacao());
	}

	private Long tempoVerificacao() {
		if (tfTempo.getText().matches("^[0-9]*$")) {
			return Long.parseLong(tfTempo.getText());
		} else {
			tfTempo.setText("1");
			return 1L;
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TelaInicial telaInicial = new TelaInicial();
				telaInicial.setLocationRelativeTo(null);
				telaInicial.setVisible(true);
			}
		});
		try {
			ServerHost.getInstancia().start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
