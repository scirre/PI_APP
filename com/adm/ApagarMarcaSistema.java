// APP PROJETO INTEGRADOR UNIVESP - POLO HELIOPOLIS - ENG. COMPUTAÇÃO

package com.adm;

import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;
import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.ui.ComboBox;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PenEvent;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class ApagarMarcaSistema extends totalcross.ui.Window {

	private Label				lblAviso;
	private ComboBox			cmbMarca;
	private ArtButton			btnApagar;
	private ArtButton 			btnVoltar;  
	private ImageControl		imgApagarEmpresa;
	
	private int					codigoMarca = 0;

	public ApagarMarcaSistema() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			lblAviso = new Label("POR FAVOR SELECIONE A MARCA QUE DESEJA\n APAGAR DO SISTEMA:");
			add(lblAviso);
			lblAviso.setRect(CENTER, TOP + 2, PREFERRED, PREFERRED);
			lblAviso.setBackColor(0x003366);
			lblAviso.setForeColor(Color.WHITE);
			
			imgApagarEmpresa = new ImageControl(new Image("img/apagarProduto.png"));
			imgApagarEmpresa.scaleToFit = true;
			imgApagarEmpresa.centerImage = true;
			add(imgApagarEmpresa, CENTER, AFTER - 5, SCREENSIZE + 20, SCREENSIZE + 40,lblAviso);
			
			cmbMarca = new ComboBox();
			add(cmbMarca);
			cmbMarca.setRect(LEFT + 90, AFTER + 30, FILL - 90, PREFERRED, imgApagarEmpresa);		
			
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
			Auxiliares.artMsgbox("ERRO", "Erro ao construir a tela ApagarProdutoSistema\n" + e);
		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnApagar) {

					if (cmbMarca.getSelectedIndex() == -1) {

						Auxiliares.artMsgbox("CONTROLE", "Selecione uma marca!");
						return;
					}

					String[] ArtButtonArray = { "Sim", "N�o" };

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja apagar essa marca?", ArtButtonArray);

					if (i == 1) {
						return;

					} else {

						apagarMarca();
						Auxiliares.artMsgbox("CONTROLE", "Marca apagado do sistema!");

					}

				} else if (evt.target == cmbMarca) {
					buscaCodigoMarca();
				}
				break;

			case PenEvent.PEN_DOWN:

				if (evt.target == cmbMarca) {

					cmbMarca.removeAll();
					carregaCmbMarca();

				}
			}
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na valida��o evento ApagarProdutoSistema\n " + e);
		}

	}
	
	public void carregaCmbMarca() {
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;
 
			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT CODIGO, PRODUTO FROM PRODUTO";

					rs = lb.executeQuery(sql);
					rs.first();			
				for (int i = 0; rs.getRowCount() > i; i++) {
					String[] b = new String[1];
					b[0] = rs.getString("PRODUTO");
					cmbMarca.add(b);
					rs.next();
				}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao carregar carregaCmbProduto\n" + e);

			}

		}
	
	public void apagarMarca() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = " DELETE FROM PRODUTO " 
				    + " WHERE PRODUTO = "	+ "'" + cmbMarca.getSelectedItem() + "'";
				lb.executeUpdate(sql);

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao apagarProduto\n"+ e);			
			unpop();
		}
	}
	
	public void buscaCodigoMarca() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				lb = new LitebasePack();
				sql = " SELECT CODIGO, PRODUTO FROM PRODUTO "
					+ " WHERE PRODUTO = "	+ "'" + cmbMarca.getSelectedItem() + "'";

				rs = lb.executeQuery(sql);
				rs.first();			
				setCodigoMarca(rs.getInt("CODIGO"));
			} finally {
				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro buscaCodigoDoProduto\n" + e);

		}

	}

	public int getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(int codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

}
