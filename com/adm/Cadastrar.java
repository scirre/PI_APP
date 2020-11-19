// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;

import com.auxiliares.Auxiliares;
import com.email.Email;
import com.informacao.Informacao;
import com.litebase.LitebasePack;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class Cadastrar extends totalcross.ui.Window {

	private ArtButton		    btnCadastrarProduto;
	private ArtButton 			btnCadastrarDescricao;
	private ArtButton			btnCadastrarMarca;
	private ArtButton			btnCadastrarCategoria;
	private ArtButton 			btnVoltar;

	public Cadastrar() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {

			btnCadastrarProduto = new ArtButton("CADASTRAR PRODUTO");
			add(btnCadastrarProduto);
			btnCadastrarProduto.setRect(CENTER, TOP + 80, width - 200, PREFERRED + 80);
			btnCadastrarProduto.setBackColor(0x003366);
			btnCadastrarProduto.setForeColor(Color.WHITE);
			
			btnCadastrarMarca = new ArtButton("CADASTRAR MARCA");
			add(btnCadastrarMarca);
			btnCadastrarMarca.setRect(CENTER, AFTER + 15, width - 200, PREFERRED + 80);
			btnCadastrarMarca.setBackColor(0x003366);
			btnCadastrarMarca.setForeColor(Color.WHITE);

			btnCadastrarDescricao = new ArtButton("CADASTRAR DESCRI��O");
			add(btnCadastrarDescricao);
			btnCadastrarDescricao.setRect(CENTER, AFTER + 15, width - 200, PREFERRED + 80);
			btnCadastrarDescricao.setBackColor(0x003366);
			btnCadastrarDescricao.setForeColor(Color.WHITE);
			
			btnCadastrarCategoria = new ArtButton("CADASTRAR CATEGORIA");
			add(btnCadastrarCategoria);
			btnCadastrarCategoria.setRect(CENTER, AFTER + 15, width - 200, PREFERRED + 80);
			btnCadastrarCategoria.setBackColor(0x003366);
			btnCadastrarCategoria.setForeColor(Color.WHITE);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO","Erro ao construir a tela configuracoes\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
//					boolean senhaSalva = false;
//					senhaSalva = validaSenhaSalva(senhaSalva);
//
//					if (senhaSalva == false) {
//						ValidaAdministrador.editSenha.setText("");
//						unpop();
//					} else {
//
//						String[] ArtButtonArray = { "Sim", "N�o" };
//
//						int i = Auxiliares.artMsgbox("CONTROLE", "Deseja continuar deixando a senha salva?",
//								ArtButtonArray);
//
//						if (i == 1) {
//							apagaDadosLogin();
//							ValidaAdministrador.editSenha.setText("");
//							Auxiliares.artMsgbox("CONTROLE", "Senha apagada do sistema!");
//							unpop();
//
//						} else {
//							unpop();
//						}
//					}
					unpop();

				} else if (evt.target == btnCadastrarProduto) {
					CadastrarProdutoSistema cadastrarProdutoSistema = new CadastrarProdutoSistema();
					cadastrarProdutoSistema.popup();
					
				}else if (evt.target == btnCadastrarMarca) {
					CadastrarMarcaSistema cadastrarMarcaSistema = new CadastrarMarcaSistema();
					cadastrarMarcaSistema.popup();
					
				}else if (evt.target == btnCadastrarDescricao) {
					CadastrarDescricaoSistema cadastrarDescricaoSistema = new CadastrarDescricaoSistema();
					cadastrarDescricaoSistema.popup();
					
				}else if (evt.target == btnCadastrarCategoria) {
					CadastrarCategoriaSistema cadastrarCategoriaSistema = new CadastrarCategoriaSistema();
					cadastrarCategoriaSistema.popup();
				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na valida��o da tela configuracoes\n" + e);
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
