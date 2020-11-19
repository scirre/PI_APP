// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO
package com.adm;


import java.util.Random;
import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.ui.ComboBox;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.Radio;
import totalcross.ui.RadioGroupController;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class CadastrarDescricaoSistema extends totalcross.ui.Window {

	public Label 				lblDescricao;
	public Label 				lblUsuario;
	public Label				lblCodigo;
	
	public Edit					editCodigo;
	public Edit					editDescricao;

	private ComboBox			cmbCategoria;
	
	public Radio 				radioDescricao;
	public Radio				radioDescricaoPeso;	
	public RadioGroupController radioGrupo;
	
	public ArtButton		    btnCadastrar;
	public ArtButton			btnGerarCodigo;
	public ArtButton 			btnVoltar;
	
	public ImageControl		    imgCadastrarEmpresa;

	public CadastrarDescricaoSistema() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			radioGrupo = new RadioGroupController();
			
			imgCadastrarEmpresa = new ImageControl(new Image("img/cadastrarDescricao.png"));
			imgCadastrarEmpresa.scaleToFit = true;
			imgCadastrarEmpresa.centerImage = true;
			add(imgCadastrarEmpresa, CENTER, TOP, SCREENSIZE + 20, SCREENSIZE + 20);
			
			lblDescricao = new Label("DESCRICAO: ");
			add(lblDescricao);
			lblDescricao.setBackColor(0x003366);
			lblDescricao.setForeColor(Color.WHITE);
			lblDescricao.setRect(LEFT, AFTER, PREFERRED, PREFERRED, imgCadastrarEmpresa);

			add(editDescricao = new Edit(), LEFT, AFTER, PREFERRED, PREFERRED, lblDescricao);
			editDescricao.setBackColor(Color.WHITE);
			editDescricao.capitalise = (Edit.ALL_UPPER);
			editDescricao.setForeColor(0x003366);
			
			btnGerarCodigo = new ArtButton("C�DIGO");
			add(btnGerarCodigo);
			btnGerarCodigo.setRect(LEFT, AFTER + 40, SCREENSIZE - 5, PREFERRED, editDescricao);
			btnGerarCodigo.setBackColor(0x003366);
			btnGerarCodigo.setForeColor(Color.WHITE);	
			
			add(editCodigo = new Edit(), LEFT, AFTER + 10, PREFERRED, PREFERRED,btnGerarCodigo);
			editCodigo.setBackColor(Color.WHITE);
			editCodigo.setForeColor(0x003366);
			editCodigo.setEditable(false);

			radioDescricaoPeso = new Radio("GM-KL",radioGrupo);
			add(radioDescricaoPeso);
			radioDescricaoPeso.setRect(RIGHT - 5, AFTER + 20, PREFERRED, PREFERRED, editCodigo);
			radioDescricaoPeso.setForeColor(Color.WHITE);
			
			radioDescricao = new Radio("ML-LT",radioGrupo);
			add(radioDescricao);
			radioDescricao.setRect(BEFORE - 15, SAME, PREFERRED, PREFERRED, radioDescricaoPeso);
			radioDescricao.setForeColor(Color.WHITE);
			
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
			editDescricao.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir o menu CadastrarDescricaoSistema\n" + e);

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

					if (editDescricao.getText().equals("")) {
						Auxiliares.artMsgbox("CONTROLE", "Preencha todos os campos!");
						return;
					}

					if (!radioDescricao.isChecked() && !radioDescricaoPeso.isChecked()) {
						Auxiliares.artMsgbox("CONTROLE", "� preciso marcar alguma op��o\n'ML-LT' ou 'GM-KL'!");
						return;
					}

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja cadastrar essa descri��o no sistema?",
							ArtButtonArray);

					if (i == 1) {
						return;

					} else {
						cadastraDescricao();
						Auxiliares.artMsgbox("CONTROLE", "Descri��o cadastrada com sucesso!");
						unpop();

					}
				} else if (evt.target == btnGerarCodigo) {
					Random random = new Random();
					int codigo = random.nextInt(900);
					editCodigo.setText(Convert.toString(codigo));

				}

			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("CONTROLE", "Erro na valida��o do menu CadastrarDescricaoSistema\n " + e);
		}

	}
	
	public void cadastraDescricao() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();

				if (radioDescricao.isChecked()) {
					sql = " INSERT INTO descricao (codigo, descricao)" + " VALUES " + "(" + "'" + editCodigo.getText()
							+ "'," + "'" + editDescricao.getText() + "'" + ")";

					lb.executeUpdate(sql);
				} else {

					sql = " INSERT INTO descricaopeso (codigo, descricao)" + " VALUES " + "(" + "'"
							+ editCodigo.getText() + "'," + "'" + editDescricao.getText() + "'" + ")";

					lb.executeUpdate(sql);
				}

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
