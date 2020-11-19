package com.cadastrarEmpresa;

import java.util.Random;
import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.util.Date;

public class CadastrarEmpresa extends totalcross.ui.Window {

	public Label                lblAviso;
	public Label 				lblEmpresa;
	public Label 				lblCnpj;
	public Label 				lblUsuario;
	public Label				lblCodigo;
	public Edit					editCodigo;
	public Edit 				editUsuario;
	public Edit 				editSenha;
	public Edit					editEmpresa;
	public Edit					editCnpj;
	public ArtButton		    btnCadastrar;
	public ArtButton			btnGerarCodigo;
	public ArtButton 			btnVoltar;
	public ImageControl		    imgCadastrarEmpresa;

	public CadastrarEmpresa() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			lblAviso = new Label("POR FAVOR PREENCHA TODOS OS CAMPOS PARA\n EFETUAR O CADASTRO:");
			add(lblAviso);
			lblAviso.setRect(CENTER, TOP, PREFERRED, PREFERRED);
			lblAviso.setBackColor(0x003366);
			lblAviso.setForeColor(Color.WHITE);
			
			imgCadastrarEmpresa = new ImageControl(new Image("img/cadastrar.png"));
			imgCadastrarEmpresa.scaleToFit = true;
			imgCadastrarEmpresa.centerImage = true;
			add(imgCadastrarEmpresa, CENTER, AFTER - 5, SCREENSIZE + 20, SCREENSIZE + 40,lblAviso);
			
			lblEmpresa = new Label("EMPRESA: ");
			add(lblEmpresa);
			lblEmpresa.setBackColor(0x003366);
			lblEmpresa.setForeColor(Color.WHITE);
			lblEmpresa.setRect(LEFT, AFTER - 50, PREFERRED, PREFERRED, imgCadastrarEmpresa);

			add(editEmpresa = new Edit(), LEFT, AFTER + 20, PREFERRED, PREFERRED, lblEmpresa);
			editEmpresa.setBackColor(Color.WHITE);
			editEmpresa.setForeColor(0x003366);

			lblCnpj = new Label("CNPJ: ");
			add(lblCnpj);
			lblCnpj.setBackColor(0x003366);
			lblCnpj.setForeColor(Color.WHITE);
			lblCnpj.setRect(LEFT, AFTER + 20, PREFERRED, PREFERRED, editEmpresa);

			add(editCnpj = new Edit(), LEFT, AFTER + 20, PREFERRED, PREFERRED, lblCnpj);
			editCnpj.setBackColor(Color.WHITE);
			editCnpj.setForeColor(0x003366);
			editCnpj.setValidChars("0 1 2 3 4 5 6 7 8 9 /");
			
			lblUsuario = new Label("USUÁRIO: ");
			add(lblUsuario);
			lblUsuario.setBackColor(0x003366);
			lblUsuario.setForeColor(Color.WHITE);
			lblUsuario.setRect(LEFT, AFTER + 20, PREFERRED, PREFERRED, editCnpj);
			
			add(editUsuario = new Edit(), LEFT, AFTER + 20, PREFERRED, PREFERRED);
			editUsuario.setBackColor(Color.WHITE);
			editUsuario.setForeColor(0x003366);
			
			btnGerarCodigo = new ArtButton("CÓDIGO");
			add(btnGerarCodigo);
			btnGerarCodigo.setRect(LEFT, AFTER + 20, SCREENSIZE - 5, PREFERRED, editUsuario);
			btnGerarCodigo.setBackColor(0x003366);
			btnGerarCodigo.setForeColor(Color.WHITE);	
			
			add(editCodigo = new Edit(), LEFT, AFTER + 20, PREFERRED, PREFERRED);
			editCodigo.setBackColor(Color.WHITE);
			editCodigo.setForeColor(0x003366);
			editCodigo.setEditable(false);
			
			btnCadastrar = new ArtButton("CADASTRAR");
			add(btnCadastrar);
			btnCadastrar.setRect(LEFT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnCadastrar.setBackColor(0x009933);
			btnCadastrar.setForeColor(Color.WHITE);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			editEmpresa.requestFocus();
			reposition();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir tela cadastrarEmpresa\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnCadastrar) {
					if (editEmpresa.getText().equals("") || editCnpj.getText().equals("")
							|| editUsuario.getText().equals("")) {
						Auxiliares.artMsgbox("CONTROLE", "Preencha todos os campos!");
						
					} else {
						
						String[] ArtButtonArray = { "Sim", "Não" };

						int i = Auxiliares.artMsgbox("CONTROLE", "Deseja cadastrar essa empresa?", ArtButtonArray);

						if (i == 1) {
							return;

						} else {
							boolean apagarEmpresaCadastrada = false;
							apagarEmpresaCadastrada = cadastrarNovaEmpresa(apagarEmpresaCadastrada);

							if (apagarEmpresaCadastrada == true) {
								Auxiliares.artMsgbox("CONTROLE", "Empresa cadastrada com sucesso!");
								unpop();
							}

						}
					}
					
				} else if (evt.target == btnGerarCodigo) {
					Random random = new Random();
					int codigo = random.nextInt(900);
					editCodigo.setText(Convert.toString(codigo));

				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação de cadastroEmpresa\n" + e);
		}

	}
	
	public boolean cadastrarNovaEmpresa(boolean apagarEmpresaCadastrada) {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {

				lb = new LitebasePack();

				sql = "SELECT * FROM EMPRESA";
				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() > 0) {
					String[] ArtButtonArray = { "Sim", "Não" };

					int i = Auxiliares.artMsgbox("CONTROLE",
							"Atualmente existe uma empresa cadastrada\nDeseja continuar?\nOBS: esse processo irá apagar a empresa cadastrada existente!",
							ArtButtonArray);

					if (i == 1) {
						return apagarEmpresaCadastrada;

					} else {

						sql = "DELETE EMPRESA";

						lb.executeUpdate(sql);

						String dataCadastro;
						Date year = new Date();
						Date month = new Date();
						Date day = new Date();

						dataCadastro = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

						sql = "INSERT INTO EMPRESA " + "(" + " CODIGO, NOME, CNPJ, USUARIO, DATACADASTRO " + ")"
								+ " VALUES " + "( '" + editCodigo.getText() + "' , '" + editEmpresa.getText() + "', '"
								+ editCnpj.getText() + "', '" + editUsuario.getText() + "', '" + dataCadastro + "'"
								+ ")";

						lb.executeUpdate(sql);

						return apagarEmpresaCadastrada = true;

					}
				}

				sql = "DELETE EMPRESA";

				lb.executeUpdate(sql);

				String dataCadastro;
				Date year = new Date();
				Date month = new Date();
				Date day = new Date();

				dataCadastro = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

				sql = "INSERT INTO EMPRESA " + "(" + " CODIGO, NOME, CNPJ, USUARIO, DATACADASTRO " + ")" + " VALUES "
						+ "( '" + editCodigo.getText() + "' , '" + editEmpresa.getText() + "', '" + editCnpj.getText()
						+ "', '" + editUsuario.getText() + "', '" + dataCadastro + "'" + ")";

				lb.executeUpdate(sql);

				return apagarEmpresaCadastrada = true;

			} finally {
				if (lb != null) {
					lb.closeAll();
				}
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao efetuar o cadastro\n" + e);
			return apagarEmpresaCadastrada;
		}
	}

}
