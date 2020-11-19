package com.cadastrarEmpresa;


import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import principal.Home;
import totalcross.sys.Convert;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class Cadastrar extends totalcross.ui.Window {

	private ArtButton		    btnCadastrarEmpresa;
	private ArtButton 			btnApagarEmpresa;
	private ArtButton 			btnVoltar;

	public Cadastrar() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {

			btnCadastrarEmpresa = new ArtButton("CADASTRAR EMPRESA");
			add(btnCadastrarEmpresa);
			btnCadastrarEmpresa.setRect(CENTER, TOP + 150, width - 200, SCREENSIZE + 20);
			btnCadastrarEmpresa.setBackColor(0x003366);
			btnCadastrarEmpresa.setForeColor(Color.WHITE);
			
			btnApagarEmpresa = new ArtButton("APAGAR EMPRESA");
			add(btnApagarEmpresa);
			btnApagarEmpresa.setRect(CENTER, AFTER, width - 200, SCREENSIZE + 20);
			btnApagarEmpresa.setBackColor(0x003366);
			btnApagarEmpresa.setForeColor(Color.WHITE);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir o menu cadastro\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					buscaEmpresaCadastrada();
					unpop();

				}else if (evt.target == btnCadastrarEmpresa) {
					CadastrarEmpresa cadastrarEmpresa = new CadastrarEmpresa();
					cadastrarEmpresa.popup();

				} else if(evt.target == btnApagarEmpresa) {
					ApagarEmpresa apagarEmpresa = new ApagarEmpresa();
					apagarEmpresa.popup();
				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("CONTROLE", "Erro na validação do menu cadastro\n " + e);
		}

	}
	
	public void buscaEmpresaCadastrada(){
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {
				lb = new LitebasePack();
				sql = "SELECT * FROM EMPRESA ";

				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() == 0) {
					Auxiliares.artMsgbox("CONTROLE", "Sistema não possui empresa cadastrada!");

					return;
				}

				Home.editEmpresa.setText(rs.getString("NOME"));
				Home.editCnpj.setText(rs.getString("CNPJ"));
				Home.editUsuario.setText(rs.getString("USUARIO"));
				Home.editCodigo.setText(Convert.toString(rs.getInt("CODIGO")));

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar empresa\n" + e);
			
			return;
		}
	}

}
