// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;

import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;
import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class Apagar extends totalcross.ui.Window {

	private ArtButton		    btnApagarProduto;
	private ArtButton 			btnApagarDescricao;
	private ArtButton			btnApagarMarca;
	private ArtButton			btnApagarCategoria;
	private ArtButton 			btnVoltar;

	public Apagar() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {

			btnApagarProduto = new ArtButton("APAGAR PRODUTO");
			add(btnApagarProduto);
			btnApagarProduto.setRect(CENTER, TOP + 80, width - 200, PREFERRED + 80);
			btnApagarProduto.setBackColor(0x003366);
			btnApagarProduto.setForeColor(Color.WHITE);
			
			btnApagarMarca = new ArtButton("APAGAR MARCA");
			add(btnApagarMarca);
			btnApagarMarca.setRect(CENTER, AFTER + 15, width - 200, PREFERRED + 80);
			btnApagarMarca.setBackColor(0x003366);
			btnApagarMarca.setForeColor(Color.WHITE);

			btnApagarDescricao = new ArtButton("APAGAR DESCRI��O");
			add(btnApagarDescricao);
			btnApagarDescricao.setRect(CENTER, AFTER + 15, width - 200, PREFERRED + 80);
			btnApagarDescricao.setBackColor(0x003366);
			btnApagarDescricao.setForeColor(Color.WHITE);
			
			btnApagarCategoria = new ArtButton("APAGAR CATEGORIA");
			add(btnApagarCategoria);
			btnApagarCategoria.setRect(CENTER, AFTER + 15, width - 200, PREFERRED + 80);
			btnApagarCategoria.setBackColor(0x003366);
			btnApagarCategoria.setForeColor(Color.WHITE);

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

				} else if (evt.target == btnApagarProduto) {
					ApagarProdutoSistema apagarProduto = new ApagarProdutoSistema();
					apagarProduto.popup();
					
				}else if (evt.target == btnApagarMarca) {
					ApagarMarcaSistema apagarMarcaSistema = new ApagarMarcaSistema();
					apagarMarcaSistema.popup();
					
				}else if (evt.target == btnApagarDescricao) {
					CadastrarDescricaoSistema cadastrarDescricaoSistema = new CadastrarDescricaoSistema();
					cadastrarDescricaoSistema.popup();
					
				}else if (evt.target == btnApagarCategoria) {
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
