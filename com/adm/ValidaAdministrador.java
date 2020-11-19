// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;

import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class ValidaAdministrador extends totalcross.ui.Window {

	public Label                lblAviso;
	public Label 				lblSenha;
	public ArtButton		    btnEntrar;
	public ArtButton 			btnVoltar;	
	public ImageControl		    imgApagarDados;
	
	public static Edit 				editSenha;

	public ValidaAdministrador() {
		setBackColor(0x003366);
		initUI();
		buscaDadosLogin();
	}

	public void initUI() {

		try {
			
			lblAviso = new Label("POR FAVOR INSIRA A SENHA DO ADMINISTRADOR\n PARA EFETUAR O LOGIN:");
			add(lblAviso);
			lblAviso.setRect(CENTER, TOP, PREFERRED, PREFERRED);
			lblAviso.setBackColor(0x003366);
			lblAviso.setForeColor(Color.WHITE);
			
			imgApagarDados = new ImageControl(new Image("img/validaAdm.png"));
			imgApagarDados.scaleToFit = true;
			imgApagarDados.centerImage = true;
			add(imgApagarDados, CENTER, AFTER + 10, SCREENSIZE + 40, SCREENSIZE + 40, lblAviso);

			lblSenha = new Label("SENHA:       ");
			add(lblSenha);
			lblSenha.setRect(LEFT + 90, AFTER + 30, PREFERRED, PREFERRED, imgApagarDados);
			lblSenha.setBackColor(0x003366);
			lblSenha.setForeColor(Color.WHITE);
			
			editSenha = new Edit();
			editSenha.setMode(TAB_ONLY_BORDER);
			editSenha.capitalise = (Edit.ALL_UPPER);
			add(editSenha);
			editSenha.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblSenha);
			editSenha.setBackColor(Color.WHITE);
			editSenha.setForeColor(0x003366);

			btnEntrar = new ArtButton("ENTRAR");
			add(btnEntrar);
			btnEntrar.setRect(LEFT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnEntrar.setBackColor(0x009933);
			btnEntrar.setForeColor(Color.WHITE);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();		
			editSenha.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO","Erro ao construir a tela ValidaAdministrador\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				}
				if (evt.target == btnEntrar) {
					if (editSenha.getText().equals(Auxiliares.SENHAADM)) {

						boolean senhaSalva = false;
						senhaSalva = validaSenhaSalva(senhaSalva);

						if (senhaSalva == true) {
							Cadastrar administrador = new Cadastrar();
							administrador.popup();
							return;
						}

						Auxiliares.artMsgbox("CONTROLE", "Bem vindo!");

						String[] ArtButtonArray = { "Sim", "N�o" };

						int i = Auxiliares.artMsgbox("CONTROLE", "Deseja salvar sua senha?", ArtButtonArray);

						if (i == 1) {
							apagaDadosLogin();
							Cadastrar administrador = new Cadastrar();
							administrador.popup();
							return;

						} else {

							salvaDadosLogin();
							Cadastrar administrador = new Cadastrar();
							administrador.popup();
						}

					} else if (editSenha.getText().equals("")) {
						Auxiliares.artMsgbox("CONTROLE", "Por favor digite uma senha!");

					} else {
						Auxiliares.artMsgbox("CONTROLE", "Senha incorreta!");
						editSenha.setText("");
					}
				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na valida��o da tela ValidaAdministrador\n" + e);
		}

	}
	
	public void salvaDadosLogin() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = "DELETE ADMINISTRADOR";
				lb.executeUpdate(sql);

				sql = " INSERT INTO ADMINISTRADOR (CODIGO, SENHA)" + " VALUES " + " ('01'," + "'"
						+ editSenha.getText() + "'" + ")";
				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar salvaDadosLogin\n" + e);

			return;
		}
	}
	
	public void apagaDadosLogin() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = "DELETE ADMINISTRADOR";
				lb.executeUpdate(sql);

				sql = " INSERT INTO ADMINISTRADOR (CODIGO, SENHA)" + " VALUES " + " ('01', 'N')";
				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar apagaDadosLogin\n" + e);

			return;
		}
	}
	
	public void buscaDadosLogin() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {
				lb = new LitebasePack();

				sql = "SELECT SENHA FROM ADMINISTRADOR";

				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() == 0) {
					return;
				}
				
				if (rs.getString("SENHA").equals("N")) {
					return;
				} else {
					editSenha.setText(rs.getString("SENHA"));
				}

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar buscaDadosLogin\n" + e);

			return;
		}
	}
	
	public boolean validaSenhaSalva(boolean senhaSalva) {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {
				lb = new LitebasePack();

				sql = "SELECT SENHA FROM ADMINISTRADOR";

				rs = lb.executeQuery(sql);
				rs.first();
				
				if (rs.getRowCount() == 0) {
					return senhaSalva;
				}
				
				if (rs.getString("SENHA").equals("N")) {
					return senhaSalva;
				} else {
					return senhaSalva = true;
				}

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar validaSenhaSalva\n" + e);

			return senhaSalva;
		}
	}

}
