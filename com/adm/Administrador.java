// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;


import com.auxiliares.Auxiliares;
import nx.componentes.ArtButton;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class Administrador extends totalcross.ui.Window {

	private ArtButton		    btnCadastrarEmpresa;
	private ArtButton 			btnApagarEmpresa;
	private ArtButton 			btnVoltar;

	public Administrador() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {

			btnCadastrarEmpresa = new ArtButton("CADASTRAR");
			add(btnCadastrarEmpresa);
			btnCadastrarEmpresa.setRect(CENTER, TOP + 150, width - 200, SCREENSIZE + 20);
			btnCadastrarEmpresa.setBackColor(0x003366);
			btnCadastrarEmpresa.setForeColor(Color.WHITE);
			
			btnApagarEmpresa = new ArtButton("APAGAR");
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
					unpop();

				}else if (evt.target == btnCadastrarEmpresa) {
					Cadastrar administrador = new Cadastrar();
					administrador.popup();

				} else if(evt.target == btnApagarEmpresa) {
					Apagar apagar = new Apagar();
					apagar.popup();
				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("CONTROLE", "Erro na valida��o do menu cadastro\n " + e);
		}

	}

}
