// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;


import java.util.Random;
import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class CadastrarCategoriaSistema extends totalcross.ui.Window {

	public Label 				lblCategoria;
	public Label 				lblUsuario;
	public Label				lblCodigo;
	
	public Edit					editCodigo;
	public Edit					editCategoria;
	
	public ArtButton		    btnCadastrar;
	public ArtButton			btnGerarCodigo;
	public ArtButton 			btnVoltar;
	
	public ImageControl		    imgCadastrarEmpresa;

	public CadastrarCategoriaSistema() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			imgCadastrarEmpresa = new ImageControl(new Image("img/cadastrarCategoria.png"));
			imgCadastrarEmpresa.scaleToFit = true;
			imgCadastrarEmpresa.centerImage = true;
			add(imgCadastrarEmpresa, CENTER, TOP + 10, SCREENSIZE + 20, SCREENSIZE + 20);
			
			lblCategoria = new Label("CATEGORIA: ");
			add(lblCategoria);
			lblCategoria.setBackColor(0x003366);
			lblCategoria.setForeColor(Color.WHITE);
			lblCategoria.setRect(LEFT, AFTER, PREFERRED, PREFERRED, imgCadastrarEmpresa);

			add(editCategoria = new Edit(), LEFT, AFTER, PREFERRED, PREFERRED, lblCategoria);
			editCategoria.setBackColor(Color.WHITE);
			editCategoria.capitalise = (Edit.ALL_UPPER);
			editCategoria.setForeColor(0x003366);
			
			btnGerarCodigo = new ArtButton("C�DIGO");
			add(btnGerarCodigo);
			btnGerarCodigo.setRect(LEFT, AFTER + 40, SCREENSIZE - 5, PREFERRED, editCategoria);
			btnGerarCodigo.setBackColor(0x003366);
			btnGerarCodigo.setForeColor(Color.WHITE);	
			
			add(editCodigo = new Edit(), LEFT, AFTER + 10, PREFERRED, PREFERRED,btnGerarCodigo);
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
			editCategoria.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir o menu CadastrarCategoriaSistema\n" + e);

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

					if (editCategoria.getText().equals("")) {
						Auxiliares.artMsgbox("CONTROLE", "Preencha todos os campos!");
						return;
					}

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja cadastrar essa categoria no sistema?",
							ArtButtonArray);

					if (i == 1) {
						return;

					} else {
						cadastrarCategoria();
						Auxiliares.artMsgbox("CONTROLE", "Categoria cadastrada com sucesso!");
						unpop();

					}
				} else if (evt.target == btnGerarCodigo) {
					Random random = new Random();
					int codigo = random.nextInt(900);
					editCodigo.setText(Convert.toString(codigo));

				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("CONTROLE", "Erro na valida��o do menu CadastrarCategoriaSistema\n " + e);
		}

	}
	
	public void cadastrarCategoria() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();

				sql = " INSERT INTO categoria (codigo, descricao)" + " VALUES " + "(" + "'" + editCodigo.getText()
						+ "'," + "'" + editCategoria.getText() + "'" + ")";

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

}
