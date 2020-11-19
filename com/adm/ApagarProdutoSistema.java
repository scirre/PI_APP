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

public class ApagarProdutoSistema extends totalcross.ui.Window {

	private Label				lblAviso;
	private ComboBox			cmbProduto;
	private ArtButton			btnApagar;
	private ArtButton 			btnVoltar;  
	private ImageControl		imgApagarEmpresa;
	
	private int					codigoProduto = 0;

	public ApagarProdutoSistema() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			lblAviso = new Label("POR FAVOR SELECIONE O PRODUTO QUE DESEJA\n APAGAR DO SISTEMA:");
			add(lblAviso);
			lblAviso.setRect(CENTER, TOP + 2, PREFERRED, PREFERRED);
			lblAviso.setBackColor(0x003366);
			lblAviso.setForeColor(Color.WHITE);
			
			imgApagarEmpresa = new ImageControl(new Image("img/apagarProduto.png"));
			imgApagarEmpresa.scaleToFit = true;
			imgApagarEmpresa.centerImage = true;
			add(imgApagarEmpresa, CENTER, AFTER - 5, SCREENSIZE + 20, SCREENSIZE + 40,lblAviso);
			
			cmbProduto = new ComboBox();
			add(cmbProduto);
			cmbProduto.setRect(LEFT + 90, AFTER + 30, FILL - 90, PREFERRED, imgApagarEmpresa);		
			
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

					if (cmbProduto.getSelectedIndex() == -1) {

						Auxiliares.artMsgbox("CONTROLE", "Selecione um produto!");
						return;
					}

					String[] ArtButtonArray = { "Sim", "N�o" };

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja apagar esse produto?", ArtButtonArray);

					if (i == 1) {
						return;

					} else {

						apagarProduto();
						Auxiliares.artMsgbox("CONTROLE", "Produto apagado do sistema!");

					}

				} else if (evt.target == cmbProduto) {
					buscaCodigoDoProduto();
				}
				break;

			case PenEvent.PEN_DOWN:

				if (evt.target == cmbProduto) {

					cmbProduto.removeAll();
					carregaCmbProduto();

				}
			}
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na valida��o evento ApagarProdutoSistema\n " + e);
		}

	}
	
	public void carregaCmbProduto() {
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
					cmbProduto.add(b);
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
	
	public void apagarProduto() {
		String sql = "";
		LitebasePack lb = null;

		try {

			try {
				lb = new LitebasePack();
				
				sql = " DELETE FROM PRODUTO " 
				    + " WHERE PRODUTO = "	+ "'" + cmbProduto.getSelectedItem() + "'";
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
	
	public void buscaCodigoDoProduto() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				lb = new LitebasePack();
				sql = " SELECT CODIGO, PRODUTO FROM PRODUTO "
					+ " WHERE PRODUTO = "	+ "'" + cmbProduto.getSelectedItem() + "'";

				rs = lb.executeQuery(sql);
				rs.first();			
				setCodigoProduto(rs.getInt("CODIGO"));
			} finally {
				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro buscaCodigoDoProduto\n" + e);

		}

	}

	public int getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(int codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

}
