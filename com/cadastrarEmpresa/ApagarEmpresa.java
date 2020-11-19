package com.cadastrarEmpresa;

import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;
import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.ui.ComboBox;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PenEvent;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class ApagarEmpresa extends totalcross.ui.Window {

	private Label				lblAviso;
	private ComboBox			cmbEmpresa;
	private ArtButton			btnApagar;
	private ArtButton 			btnVoltar;  
	private ImageControl		imgApagarEmpresa;
	
	private int					codigoEmpresa = 0;

	public ApagarEmpresa() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			lblAviso = new Label("POR FAVOR SELECIONE A EMPRESA QUE DESEJA\n APAGAR DO SISTEMA:");
			add(lblAviso);
			lblAviso.setRect(CENTER, TOP + 2, PREFERRED, PREFERRED);
			lblAviso.setBackColor(0x003366);
			lblAviso.setForeColor(Color.WHITE);
			
			imgApagarEmpresa = new ImageControl(new Image("img/apagarEmpresa.png"));
			imgApagarEmpresa.scaleToFit = true;
			imgApagarEmpresa.centerImage = true;
			add(imgApagarEmpresa, CENTER, AFTER - 5, SCREENSIZE + 20, SCREENSIZE + 40,lblAviso);
			
			cmbEmpresa = new ComboBox();
			add(cmbEmpresa);
			cmbEmpresa.setRect(LEFT + 90, AFTER + 30, FILL - 90, PREFERRED, imgApagarEmpresa);		
			
			btnApagar = new ArtButton("APAGAR");
			add(btnApagar);
			btnApagar.setRect(LEFT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnApagar.setBackColor(0xDF0101);
			btnApagar.setForeColor(Color.WHITE);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir a tela ApagarEmpresa\n" + e);
		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnApagar) {

					if (cmbEmpresa.getSelectedIndex() == -1) {

						Auxiliares.artMsgbox("CONTROLE", "Selecione uma empresa!");
						return;
					}

					String[] ArtButtonArray = { "Sim", "Não" };

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja apagar essa empresa?", ArtButtonArray);

					if (i == 1) {
						return;

					} else {

						apagarEmpresaDoSistema();
						Auxiliares.artMsgbox("CONTROLE", "Empresa apagada do sistema!");

					}

				} else if (evt.target == cmbEmpresa) {
					buscaCodigoEmpresa();
				}
				break;

			case PenEvent.PEN_DOWN:

				if (evt.target == cmbEmpresa) {

					cmbEmpresa.removeAll();
					carregaCmbEmpresa();

				}
			}
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação evento apagarEmpresa\n " + e);
		}

	}
	
	public void carregaCmbEmpresa() {
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;
 
			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT NOME, CODIGO FROM EMPRESA";

					rs = lb.executeQuery(sql);
					rs.first();			
				for (int i = 0; rs.getRowCount() > i; i++) {
					String[] b = new String[1];
					b[0] = rs.getString("NOME");
					cmbEmpresa.add(b);
					rs.next();
				}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao carregar cmbEmpresa\n" + e);

			}

		}
	
	public void apagarEmpresaDoSistema() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = " DELETE FROM EMPRESA " + " WHERE CODIGO = " + getCodigoEmpresa();
				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao apagar Empresa do sistema\n"+ e);			
			unpop();
		}
	}
	
	public void buscaCodigoEmpresa() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				lb = new LitebasePack();
				sql = " SELECT CODIGO, NOME FROM EMPRESA "
					+ " WHERE NOME = "	+ "'" + cmbEmpresa.getSelectedItem() + "'";

				rs = lb.executeQuery(sql);
				rs.first();			
				setCodigoEmpresa(rs.getInt("CODIGO"));
			} finally {
				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar codigoEmpresa\n" + e);

		}

	}

	public int getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(int codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

}
