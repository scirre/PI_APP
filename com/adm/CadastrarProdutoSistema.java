// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;


import java.util.Random;

import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import principal.Home;
import totalcross.sys.Convert;
import totalcross.ui.ComboBox;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class CadastrarProdutoSistema extends totalcross.ui.Window {

	public Label                lblAviso;
	public Label 				lblProduto;
	public Label 				lblCnpj;
	public Label 				lblUsuario;
	public Label				lblCodigo;
	public Edit					editCodigo;
	public Edit 				editUsuario;
	public Edit 				editSenha;
	public Edit					editProduto;
	public Edit					editCnpj;
	private Label 				lblCategoria;
	private ComboBox			cmbCategoria;
	public ArtButton		    btnCadastrar;
	public ArtButton			btnGerarCodigo;
	public ArtButton 			btnVoltar;
	public ImageControl		    imgCadastrarEmpresa;

	public CadastrarProdutoSistema() {
		setBackColor(0x003366);
		initUI();
		carregaCmbCategoria();
	}

	public void initUI() {

		try {
			
			imgCadastrarEmpresa = new ImageControl(new Image("img/cadastrarProduto.png"));
			imgCadastrarEmpresa.scaleToFit = true;
			imgCadastrarEmpresa.centerImage = true;
			add(imgCadastrarEmpresa, CENTER, TOP + 10, SCREENSIZE + 20, SCREENSIZE + 20,lblAviso);
			
			lblProduto = new Label("PRODUTO: ");
			add(lblProduto);
			lblProduto.setBackColor(0x003366);
			lblProduto.setForeColor(Color.WHITE);
			lblProduto.setRect(LEFT, AFTER + 30, PREFERRED, PREFERRED, imgCadastrarEmpresa);

			add(editProduto = new Edit(), LEFT, AFTER, PREFERRED, PREFERRED, lblProduto);
			editProduto.setBackColor(Color.WHITE);
			editProduto.capitalise = (Edit.ALL_UPPER);
			editProduto.setForeColor(0x003366);

			lblCategoria = new Label("CATEGORIA:");
			add(lblCategoria);
			lblCategoria.setRect(LEFT, AFTER + 40, PREFERRED, PREFERRED, editProduto);
			lblCategoria.setBackColor(0x003366);
			lblCategoria.setForeColor(Color.WHITE);
			
			cmbCategoria = new ComboBox();
			add(cmbCategoria);
			cmbCategoria.setRect(LEFT, AFTER, FILL + 5, PREFERRED, lblCategoria);
			
			btnGerarCodigo = new ArtButton("C�DIGO");
			add(btnGerarCodigo);
			btnGerarCodigo.setRect(LEFT, AFTER + 40, SCREENSIZE - 5, PREFERRED, cmbCategoria);
			btnGerarCodigo.setBackColor(0x003366);
			btnGerarCodigo.setForeColor(Color.WHITE);	
			
			add(editCodigo = new Edit(), LEFT, AFTER + 10, PREFERRED, PREFERRED);
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

			reposition();
			editProduto.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir o menu CadastrarProdutoSistema\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnCadastrar) {
					String[] ArtButtonArray = { "Sim", "N�o" };

					if (editProduto.getText().equals("") || editCodigo.getText().equals("")
							|| cmbCategoria.getSelectedItem() == "") {
						Auxiliares.artMsgbox("CONTROLE", "Preencha todos os campos!");
						return;
					}

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja cadastrar esse produto no sistema?", ArtButtonArray);

					if (i == 1) {
						return;

					} else {
						cadastrarProdutoSistema();
						Auxiliares.artMsgbox("CONTROLE", "Produto cadastrado com sucesso!");
						unpop();

					}
				} else if (evt.target == btnGerarCodigo) {
					Random random = new Random();
					int codigo = random.nextInt(900);
					editCodigo.setText(Convert.toString(codigo));

				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("CONTROLE", "Erro na valida��o do menu CadastrarProdutoSistema\n " + e);
		}

	}
	
	public void cadastrarProdutoSistema() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				sql = " INSERT INTO produto (codigo, produto, categoria)" + " VALUES " + "(" + "'"
						+ editCodigo.getText() + "'," + "'" + editProduto.getText() + "'," + "'"
						+ cmbCategoria.getSelectedItem() + "'" + ")";

				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar cadastrarProdutoSistema\n" + e);

			return;
		}
	}
	
	public void carregaCmbCategoria() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					cmbCategoria.removeAll();
					lb = new LitebasePack();
					sql = " SELECT DESCRICAO FROM CATEGORIA";

					rs = lb.executeQuery(sql);
					rs.first();
					for (int i = 0; rs.getRowCount() > i; i++) {
						String[] b = new String[1];
						b[0] = rs.getString("DESCRICAO");
						cmbCategoria.add(b);
						rs.next();
					}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao carregaCmbCategoria\n" + e);

			}

		}
	}

}
