package com.inserir;

import java.util.Random;

import com.auxiliares.Auxiliares;
import com.litebase.LitebasePack;
import com.teclado.Teclado;
import com.venda.Venda;
import totalcross.ui.ComboBox;
import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.util.Date;

public class Inserir extends totalcross.ui.Window {

	private Label				 lblProduto;
	private Label 				 lblEstoque;
	private Label 			     lblValor;
	private Label                lblAviso;
	private Label 				 lblQuantidade;
	private Label				 lblTotal;
	private Label   			 lblCodigo;
	private Label				 lblCategoria;
	private Label 				 lblMarca;
	private Label 				 lblDescricao;
	private Edit				 editCategoria;
	private Edit				 editMarca;
	private Edit				 editDescricao;
	private Edit 				 editCodigo;
	private Edit 				 editProduto;
	private Edit 				 editEstoque;
	private Edit 				 editValor;
	private Edit				 editQuantidade;
	private Edit 				 editTotal;
	private ArtButton 			 btnInserir;
	private ArtButton 			 btnVoltar;
	
	private ComboBox			 cmbTipoPagamento;
	private ImageControl		 imgInserir;
	
	public double 				 valorProduto = 0.0;
	public double				 total = 0.0;
	public int				     qntEstoqueFinal = 0;
	public int 				     quantidade = 0;
	public int 					 quantidadeEstoque = 0;
	public int 					 qntEstoqueCalculo = 0;
	public String 				 quantidadeVendida = "";
	public String 				 dataEntrada = "";
	public String				 estoque = "";
	public String 				 quantidadeInserida = "";

	public Teclado teclado;

	public Inserir() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			imgInserir = new ImageControl(new Image("img/inserir.png"));
			imgInserir.scaleToFit = true;
			imgInserir.centerImage = true;
			add(imgInserir, CENTER, TOP - 30, SCREENSIZE + 10, SCREENSIZE + 30);
			
			lblCategoria = new Label("CATEGORIA:   ");
			add(lblCategoria);
			lblCategoria.setRect(LEFT + 90, AFTER - 10, PREFERRED, PREFERRED, imgInserir);
			lblCategoria.setBackColor(0x003366);
			lblCategoria.setForeColor(Color.WHITE);

			editCategoria = new Edit();
			add(editCategoria);
			editCategoria.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblCategoria);
			editCategoria.setBackColor(Color.WHITE);
			editCategoria.setForeColor(0x003366);
			editCategoria.setText(Venda.categoria);
			editCategoria.setEditable(false);
			
			lblMarca = new Label("MARCA:          ");
			add(lblMarca);
			lblMarca.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editCategoria);
			lblMarca.setBackColor(0x003366);
			lblMarca.setForeColor(Color.WHITE);

			editMarca = new Edit();
			add(editMarca);
			editMarca.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblMarca);
			editMarca.setBackColor(Color.WHITE);
			editMarca.setForeColor(0x003366);
			editMarca.setText(Venda.marca);
			editMarca.setEditable(false);
			
			lblDescricao = new Label("DESCRIÇÃO:   ");
			add(lblDescricao);
			lblDescricao.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editMarca);
			lblDescricao.setBackColor(0x003366);
			lblDescricao.setForeColor(Color.WHITE);

			editDescricao = new Edit();
			add(editDescricao);
			editDescricao.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblDescricao);
			editDescricao.setBackColor(Color.WHITE);
			editDescricao.setForeColor(0x003366);
			editDescricao.setText(Venda.descricao);
			editDescricao.setEditable(false);		

			lblProduto = new Label("PRODUTO:      ");
			add(lblProduto);
			lblProduto.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editDescricao);
			lblProduto.setBackColor(0x003366);
			lblProduto.setForeColor(Color.WHITE);

			editProduto = new Edit();
			add(editProduto);
			editProduto.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblProduto);
			editProduto.setBackColor(Color.WHITE);
			editProduto.setForeColor(0x003366);
			editProduto.setText(Venda.produto);
			editProduto.setEditable(false);

			lblCodigo = new Label("CÓDIGO:          ");
			add(lblCodigo);
			lblCodigo.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editProduto);
			lblCodigo.setBackColor(0x003366);
			lblCodigo.setForeColor(Color.WHITE);

			editCodigo = new Edit();
			add(editCodigo);
			editCodigo.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblCodigo);
			editCodigo.setBackColor(Color.WHITE);
			editCodigo.setForeColor(0x003366);
			editCodigo.setText(Venda.codigo);
			editCodigo.setEditable(false);

			lblEstoque = new Label("ESTOQUE:       ");
			add(lblEstoque);
			lblEstoque.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editCodigo);
			lblEstoque.setBackColor(0x003366);
			lblEstoque.setForeColor(Color.WHITE);

			editEstoque = new Edit();
			add(editEstoque);
			editEstoque.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblEstoque);
			editEstoque.setBackColor(Color.WHITE);
			editEstoque.setForeColor(0x003366);
			editEstoque.setText(Venda.quantidade);
			editEstoque.setEditable(false);

			lblQuantidade = new Label("QUANTIDADE: ");
			add(lblQuantidade);
			lblQuantidade.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, lblEstoque);
			lblQuantidade.setBackColor(0x003366);
			lblQuantidade.setForeColor(Color.WHITE);

			editQuantidade = new Edit();
			add(editQuantidade);
			editQuantidade.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblQuantidade);
			editQuantidade.setBackColor(Color.WHITE);
			editQuantidade.setForeColor(0x003366);
			editQuantidade.setValidChars("0 1 2 3 4 5 6 7 8 9");

			lblValor = new Label("VALOR:            ");
			add(lblValor);
			lblValor.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, lblQuantidade);
			lblValor.setBackColor(0x003366);
			lblValor.setForeColor(Color.WHITE);

			editValor = new Edit();
			add(editValor);
			editValor.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblValor);
			editValor.setBackColor(Color.WHITE);
			editValor.setForeColor(0x003366);
			editValor.setText(Venda.valor);
			editValor.setEditable(false);

			lblTotal = new Label("TOTAL:            ");
			add(lblTotal);
			lblTotal.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editValor);
			lblTotal.setBackColor(0x003366);
			lblTotal.setForeColor(Color.WHITE);

			editTotal = new Edit();
			add(editTotal);
			editTotal.setRect(AFTER, SAME, FILL - 80, PREFERRED, lblTotal);
			editTotal.setBackColor(Color.WHITE);
			editTotal.setForeColor(0x003366);
			editTotal.setText("0.00");
			editTotal.setEditable(false);
			
			cmbTipoPagamento = new ComboBox();
			add(cmbTipoPagamento);
			cmbTipoPagamento.setRect(LEFT + 150, AFTER + 15, FILL - 140, PREFERRED, editTotal);
			
			btnInserir = new ArtButton("INSERIR");
			add(btnInserir);
			btnInserir.setRect(LEFT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
			btnInserir.setBackColor(0x009933);
			btnInserir.setForeColor(Color.WHITE);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 5, PREFERRED + 13);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();
			
			carregaCmbTipoPagamento();
			cmbTipoPagamento.setSelectedIndex(0);
			
			editQuantidade.requestFocus();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO","Erro ao construir a tela inserir\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				}
				if (evt.target == btnInserir) {

					if (editQuantidade.getText().length() == 0) {
						Auxiliares.artMsgbox("CONTROLE", "Por favor insira uma quantidade!");
						return;
					}

					if (editQuantidade.getText().length() > 0) {

						estoque = editEstoque.getText();
						quantidadeInserida = editQuantidade.getText();

						if (Convert.toInt(quantidadeInserida) > Convert.toInt(Venda.quantidade)) {
							Auxiliares.artMsgbox("CONTROLE", "Quantidade inserida maior que a quantidade em estoque!");
							
							editQuantidade.setText("");
							editEstoque.setText(Venda.quantidade);
							editTotal.setText("0.00");
							
							editQuantidade.requestFocus();
							return;

						}

						String[] ArtButtonArray = { "Sim", "Não" };

						int i = Auxiliares.artMsgbox("CONTROLE", "Deseja inserir " + editQuantidade.getText()
								+ " unidade(s) deste produto\nno carrinho?", ArtButtonArray);

						if (i == 1) {
							return;

						} else {

							insereProdutoNoCarrinho();

							Auxiliares.artMsgbox("CONTROLE", "Produto inserido no carrinho!");

							Venda.btnCarrinho.setEnabled(true);
							unpop();
						}
					}

				}
				if (editQuantidade.getText().length() > 0) {
					calculaTotalProduto();

				} else if (editQuantidade.getText().length() == 0) {
					editEstoque.setText(Venda.quantidade);
					editTotal.setText("");
				}
			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação da tela inserir\n" + e);
		}

	}

	public void calculaTotalProduto() {

		try {

			estoque = Venda.quantidade;
			quantidadeInserida = editQuantidade.getText();
			qntEstoqueFinal = Convert.toInt(estoque) - Convert.toInt(quantidadeInserida);

			String quantidadeString = "";
			quantidadeString = editQuantidade.getText();
			valorProduto = Convert.toDouble(Venda.valor.replace(".", "."));
			quantidade = Convert.toInt(quantidadeString);

			total = valorProduto * quantidade;
			editTotal.setText(Convert.toString(total, 2));
			editEstoque.setText(Convert.toString(qntEstoqueFinal));

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro no calculo do produto\n" + e);
		}

	}
	
	public void insereProdutoNoCarrinho() {

		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {
			try {
				
				lb = new LitebasePack();
				
				sql = "SELECT CODIGO FROM VENDAPRODUTOTEMP";
				rs = lb.executeQuery(sql);
				rs.first();
				
				if (rs.getRowCount() == 0) {

					Random random = new Random();
					int codigo = random.nextInt(900);
					String dataVenda;
					Date year = new Date();
					Date month = new Date();
					Date day = new Date();

					dataVenda = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

					sql = "INSERT INTO VENDAPRODUTOTEMP " + "(" + " CODIGO, PRODUTO, VALOR, QUANTIDADE, CODIGOPROD, "
							+ " CATEGORIA, MARCA, DESCRICAO, TIPOPAGAMENTO, DATASAIDA " + ")" + " VALUES " 
							+ "( '" + codigo + "' , '" + editProduto.getText()
	                        + "', '" + editTotal.getText() + "', '"
							+ editQuantidade.getText() + "', '" + editCodigo.getText() + "','" + editCategoria.getText() + "','" 
							+ editMarca.getText() + "', '"+ editDescricao.getText() 
							+ "', '" + cmbTipoPagamento.getSelectedItem() + "', '" + dataVenda + "'" + ")";

					lb.executeUpdate(sql);

				}else {
					
					int codigo = rs.getInt("CODIGO");
					String dataVenda;
					Date year = new Date();
					Date month = new Date();
					Date day = new Date();

					dataVenda = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

					sql = "INSERT INTO VENDAPRODUTOTEMP " + "(" + " CODIGO, PRODUTO, VALOR, QUANTIDADE, CODIGOPROD, "
							+ " CATEGORIA, MARCA, DESCRICAO, TIPOPAGAMENTO, DATASAIDA " + ")" + " VALUES " 
							+ "( '" + codigo + "' , '" + editProduto.getText()
	                        + "', '" + editTotal.getText() + "', '"
							+ editQuantidade.getText() + "', '" + editCodigo.getText() + "','" + editCategoria.getText() + "','" 
							+ editMarca.getText() + "', '"+ editDescricao.getText() 
							+ "', '" + cmbTipoPagamento.getSelectedItem() + "', '" + dataVenda + "'" + ")";

					lb.executeUpdate(sql);
					
				}

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao inserir produto estoque\n" + e);
		}

	}
	
	public void carregaCmbTipoPagamento() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT DESCRICAO FROM TIPOPAGAMENTO";

					rs = lb.executeQuery(sql);
					rs.first();
					for (int i = 0; rs.getRowCount() > i; i++) {
						String[] b = new String[1];
						b[0] = rs.getString("DESCRICAO");
						cmbTipoPagamento.add(b);
						rs.next();
					}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro carregaCmbTipoPagamento\n" + e);

			}

		}
	}

}
