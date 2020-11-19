package com.venda;

import com.auxiliares.Auxiliares;
import com.bottom.BottomImg;
import com.carrinho.Carrinho;
import com.inserir.Inserir;
import com.litebase.LitebasePack;
import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class Venda extends totalcross.ui.Window{
	
	private Label 							lblProduto;
	private Label							lblCodigo;
	private Label							lblCategoria;
	private ArtButton 						btnVoltar;
	private ArtButton						btnBuscaProduto;
	private ArtButton						btnBuscaCodigo;
	private ArtButton						btnInserir;
	private ComboBox						cmbCategoria;
	private Edit							editBuscaProduto;
	private Edit							editBuscaCodigo;
	public Grid							    gridProdutos;
	
	public static Button					btnCarrinho;
	public static String					codigo = "";
	public static String					produto = "";
	public static String					quantidade = "";
	public static String				    valor = "";
	public static String					categoria = "";
	public static String					marca = "";
	public static String  					descricao;
	
	public Venda(){
		 setBackColor(0x003366);
		 initUI();
	}
	
	public void initUI() {
		
		try {
					
//			lblCategoria = new Label("CATEGORIA:");
//			add(lblCategoria);
//			lblCategoria.setRect(LEFT, TOP + 5, PREFERRED, PREFERRED);
//			lblCategoria.setBackColor(0x003366);
//			lblCategoria.setForeColor(Color.WHITE);
//			
//			cmbCategoria = new ComboBox();
//			add(cmbCategoria);
//			cmbCategoria.setRect(LEFT, AFTER + 5, FILL + 5, PREFERRED, lblCategoria);
			
			lblProduto = new Label("PRODUTO");
			add(lblProduto);
			lblProduto.setRect(LEFT, TOP + 5, PREFERRED, PREFERRED);
			lblProduto.setBackColor(0x003366);
			lblProduto.setForeColor(Color.WHITE);
			
			editBuscaProduto = new Edit();
			add(editBuscaProduto);
			editBuscaProduto.capitalise = (Edit.ALL_UPPER);
			editBuscaProduto.setRect(LEFT, AFTER + 5, width - 160, PREFERRED, lblProduto);
			editBuscaProduto.setBackColor(Color.WHITE);
			editBuscaProduto.setForeColor(0x003366);

			btnBuscaProduto = new ArtButton("BUSCAR");
			add(btnBuscaProduto);
			btnBuscaProduto.setRect(AFTER + 1, SAME, SCREENSIZE - 7, PREFERRED, editBuscaProduto);
			btnBuscaProduto.setBackColor(0x003366);
	        btnBuscaProduto.setForeColor(Color.WHITE);
	        
	        lblCodigo = new Label("CÓDIGO");
			add(lblCodigo);
			lblCodigo.setRect(LEFT, AFTER + 5, PREFERRED, PREFERRED, editBuscaProduto);
			lblCodigo.setBackColor(0x003366);
			lblCodigo.setForeColor(Color.WHITE);
			
			editBuscaCodigo = new Edit();
			add(editBuscaCodigo);
			editBuscaCodigo.capitalise = (Edit.ALL_UPPER);
			editBuscaCodigo.setRect(LEFT, AFTER + 5, width - 160, PREFERRED, lblCodigo);
			editBuscaCodigo.setBackColor(Color.WHITE);
			editBuscaCodigo.setForeColor(0x003366);
			editBuscaCodigo.setValidChars("0123456789");

			btnBuscaCodigo = new ArtButton("BUSCAR");
			add(btnBuscaCodigo);
			btnBuscaCodigo.setRect(AFTER + 1, SAME, SCREENSIZE - 7, PREFERRED, editBuscaCodigo);
			btnBuscaCodigo.setBackColor(0x003366);
			btnBuscaCodigo.setForeColor(Color.WHITE);
	            
            btnVoltar = new ArtButton("VOLTAR");
            add(btnVoltar);
            btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
            btnVoltar.setBackColor(0x003366);
            btnVoltar.setForeColor(Color.WHITE);
            
            btnInserir = new ArtButton("INSERIR");
            add(btnInserir);
            btnInserir.setRect(LEFT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
            btnInserir.setBackColor(0x009933);
            btnInserir.setForeColor(Color.WHITE);
            
            btnCarrinho = BottomImg.imageOnly(new Image("img/carrinho.png"));
			add(btnCarrinho, AFTER + 5, BOTTOM, SCREENSIZE + 16, PREFERRED, btnInserir);
			btnCarrinho.setEnabled(false);

            int gridWidths[] = new int[7];
				gridWidths[0] = 5;
				gridWidths[1] = 400;
				gridWidths[2] = 100;
				gridWidths[3] = 300;
				gridWidths[4] = 190;
				gridWidths[5] = 100;
				gridWidths[6] = 140;
	
			String[] caps = { "COD.", "PRODUTO", "DESC", "MARCA", "CATEGORIA","QNT", " VALOR"};
			int[] aligns = { Grid.LEFT, Grid.CENTER, Grid.LEFT, Grid.LEFT, Grid.LEFT, Grid.LEFT, Grid.LEFT};
			gridProdutos = new Grid(caps, gridWidths, aligns, false);
			add(gridProdutos);
			gridProdutos.setBackColor(Color.WHITE);
			gridProdutos.setForeColor(0x003366);
			gridProdutos.transparentBackground = false;
			gridProdutos.drawCheckBox = true;
			gridProdutos.disableSort = false;
			gridProdutos.canClickSelectAll = true;
			gridProdutos.boldCheck = false;
			gridProdutos.enableColumnResize = false;
//			gridProdutos.captionsBackColor = Color.BLACK;
			gridProdutos.setRect(Container.LEFT + 1, Container.AFTER + 10,
					Container.FILL - 1, Container.FIT, btnBuscaCodigo);
			
			reposition();
			editBuscaProduto.requestFocus();
			
			habilitaCarrinho();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO","Erro ao construir a tela venda\n" + e);
			
		}
		
	}
	
	
	public void onEvent(Event evt) {
		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnBuscaProduto) {
					gridProdutos.removeAllElements();
					buscaProdutoPorDescricao();

				} else if (evt.target == btnBuscaCodigo) {
					gridProdutos.removeAllElements();
					buscaProdutoPorCodigo();

				} else if (evt.target == cmbCategoria) {
					if (cmbCategoria.getItems() != null) {
						editBuscaProduto.setText("");
						gridProdutos.removeAllElements();
						carregaGridProdutos();

					} else {
						return;
					}

				} else if (evt.target == btnInserir) {
					if (gridProdutos.getSelectedItem() != null) {
						boolean prodAdicionado = false;
						prodAdicionado = validaProdutoCarrinho(prodAdicionado);

						if (prodAdicionado == false) {
							Inserir inserir = new Inserir();
							inserir.popup();
						} else {

							Auxiliares.artMsgbox("CONTROLE",
									"Esse produto encontra-se no carrinho. Por favor finalize a venda para inseri-lo novamente!");
						}

					} else {
						Auxiliares.artMsgbox("CONTROLE", "Deve-se selecionar um item!");
					}

				} else if (evt.target == btnCarrinho) {
					gridProdutos.removeAllElements();
					Carrinho carrinho = new Carrinho();
					carrinho.popup();
				}
				break;
			case ControlEvent.FOCUS_IN:
				if (evt.target == cmbCategoria) {
					cmbCategoria.removeAll();
					carregaCmbCategoria();
				}
			}
			switch (evt.type) {
			case GridEvent.SELECTED_EVENT:
				if (evt.target == gridProdutos) {

					try {

						codigo = gridProdutos.getSelectedItem()[0];
						produto = gridProdutos.getSelectedItem()[1];
						quantidade = gridProdutos.getSelectedItem()[5];
						marca = gridProdutos.getSelectedItem()[3];
						categoria = gridProdutos.getSelectedItem()[4];
						descricao = gridProdutos.getSelectedItem()[2];
						valor = gridProdutos.getSelectedItem()[6];

					} catch (Exception e) {
						Auxiliares.artMsgbox("CONTROLE", "Clique em um Item!");
					}

				}

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação da tela venda\n" + e);
		}

	}
	
	public void carregaGridProdutos() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				
				lb = new LitebasePack();
				sql = " SELECT * FROM ESTOQUE WHERE CATEGORIA = " + "'" + cmbCategoria.getSelectedItem() + "'";

				rs = lb.executeQuery(sql);
				rs.first();
				for (int i = 0; rs.getRowCount() > i; i++) {
					String[] b = new String[7];
					b[0] = Convert.toString(rs.getInt("CODIGO"));
					b[1] = rs.getString("PRODUTO");
					b[2] = rs.getString("DESCRICAO");
					b[3] = rs.getString("MARCA");
					b[4] = rs.getString("CATEGORIA");
					b[5] = Convert.toString(rs.getInt("QUANTIDADE"));
					b[6] = rs.getString("VALOR");
					gridProdutos.add(b);
					rs.next();
				}

			} finally {
				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao carregarGridProdutos\n" + e);

		}

	}
	
	public void buscaProdutoPorDescricao() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				editBuscaCodigo.setText("");
				lb = new LitebasePack();
				sql = " SELECT * FROM ESTOQUE WHERE PRODUTO LIKE " 
				    + "'%" + editBuscaProduto.getText() + "%'"
				    + "OR MARCA LIKE "  + "'%" + editBuscaProduto.getText() + "%'"
 					+ "OR CATEGORIA LIKE "  + "'%" + editBuscaProduto.getText() + "%'"
				    + "OR DESCRICAO LIKE "  + "'%" + editBuscaProduto.getText() + "%'";

				rs = lb.executeQuery(sql);
				rs.first();
				for (int i = 0; rs.getRowCount() > i; i++) {
					String[] b = new String[7];
					b[0] = Convert.toString(rs.getInt("CODIGO"));
					b[1] = rs.getString("PRODUTO");
					b[2] = rs.getString("DESCRICAO");
					b[3] = rs.getString("MARCA");
					b[4] = rs.getString("CATEGORIA");
					b[5] = Convert.toString(rs.getInt("QUANTIDADE"));
					b[6] = rs.getString("VALOR");
					gridProdutos.add(b);
					rs.next();
				}
			} finally {
 				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao carregaGridProdutosBusca\n" + e);

		}

	}
	
	public void buscaProdutoPorCodigo() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				
				editBuscaProduto.setText("");
				lb = new LitebasePack();

				if (editBuscaCodigo.getText().equals("")) {
					sql = " SELECT * FROM ESTOQUE WHERE PRODUTO LIKE " 
				        + "'%" + editBuscaCodigo.getText() + "%'"
					    + "OR MARCA LIKE " + "'%" + editBuscaProduto.getText() + "%'" + "OR CATEGORIA LIKE " + "'%"
						+ editBuscaCodigo.getText() + "%'" + "OR DESCRICAO LIKE " + "'%"
						+ editBuscaCodigo.getText() + "%'";
				} else {

					sql = " SELECT * FROM ESTOQUE WHERE CODIGO = " + editBuscaCodigo.getText();

				}

				rs = lb.executeQuery(sql);
				rs.first();
				for (int i = 0; rs.getRowCount() > i; i++) {
					String[] b = new String[7];
					b[0] = Convert.toString(rs.getInt("CODIGO"));
					b[1] = rs.getString("PRODUTO");
					b[2] = rs.getString("DESCRICAO");
					b[3] = rs.getString("MARCA");
					b[4] = rs.getString("CATEGORIA");
					b[5] = Convert.toString(rs.getInt("QUANTIDADE"));
					b[6] = rs.getString("VALOR");
					gridProdutos.add(b);
					rs.next();
				}
			} finally {
				if (lb != null)
					lb.closeAll();

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao carregaGridProdutosBusca\n" + e +"\nPor favor digite um código válido!");
			editBuscaCodigo.setText("");
		}

	}
	
	public void carregaCmbCategoria() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
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
	
	public boolean validaProdutoCarrinho(boolean prodAdicionado) {
 		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {

				lb = new LitebasePack();

				sql = "SELECT * FROM VENDAPRODUTOTEMP WHERE CODIGOPROD = " + codigo;

				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() == 0) {
					return prodAdicionado = false;

				} else {
					
					return prodAdicionado = true;
				}

			}
			finally {
				if (lb != null) {
					lb.closeAll();
				}
			}

		} 
		catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro validaProdutoCarrinho\n" + e);
		}
		return prodAdicionado;

	}
	
	public void habilitaCarrinho() {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {

				lb = new LitebasePack();

				sql = "SELECT * FROM VENDAPRODUTOTEMP";

				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() == 0) {
					return;

				} else {
					btnCarrinho.setEnabled(true);
				}

			} finally {
				if (lb != null) {
					lb.closeAll();
				}
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao habilitaCarrinho\n" + e);
		}

	}
	
}
