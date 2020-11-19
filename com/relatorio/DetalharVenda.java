package com.relatorio;

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

public class DetalharVenda extends totalcross.ui.Window {

	private Label				 lblProduto;
	private Label 				 lblDataVenda;
	private Label                lblAviso;
	private Label				 lblTotal;
	private Label   			 lblTipoPagamento;
	private Label				 lblCategoria;
	private Label 				 lblMarca;
	private Label 				 lblDescricao;
	private Edit				 editCategoria;
	private Edit				 editMarca;
	private Edit				 editDescricao;
	private Edit 				 editTipoPagamento;
	private Edit 				 editProduto;
	private Edit 				 editDataVenda;
	private Edit				 editQuantidade;
	private Edit 				 editTotal;
	private ArtButton 			 btnInserir;
	private ArtButton 			 btnVoltar;
	private ComboBox			 cmbTipoPagamento;
	private ImageControl		 imgVenda;
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

	public DetalharVenda() {
		setBackColor(0x003366);
		initUI();
	}

	public void initUI() {

		try {
			
			imgVenda = new ImageControl(new Image("img/venda.png"));
			imgVenda.scaleToFit = true;
			imgVenda.centerImage = true;
			add(imgVenda, CENTER, TOP - 50, SCREENSIZE + 30, SCREENSIZE + 50);
			
			lblProduto = new Label("PRODUTO:    ");
			add(lblProduto);
			lblProduto.setRect(LEFT + 90, AFTER + 20, PREFERRED, PREFERRED, imgVenda);
			lblProduto.setBackColor(0x003366);
			lblProduto.setForeColor(Color.WHITE);

			editProduto = new Edit();
			add(editProduto);
			editProduto.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblCategoria);
			editProduto.setBackColor(Color.WHITE);
			editProduto.setForeColor(0x003366);
			editProduto.setText(Relatorio.produto);
			editProduto.setEditable(false);
			
			lblMarca = new Label("MARCA:         ");
			add(lblMarca);
			lblMarca.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editProduto);
			lblMarca.setBackColor(0x003366);
			lblMarca.setForeColor(Color.WHITE);

			editMarca = new Edit();
			add(editMarca);
			editMarca.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblMarca);
			editMarca.setBackColor(Color.WHITE);
			editMarca.setForeColor(0x003366);
			editMarca.setText(Relatorio.marca);
			editMarca.setEditable(false);
			
			lblDescricao = new Label("DESCRICAO: ");
			add(lblDescricao);
			lblDescricao.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editMarca);
			lblDescricao.setBackColor(0x003366);
			lblDescricao.setForeColor(Color.WHITE);

			editDescricao = new Edit();
			add(editDescricao);
			editDescricao.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblDescricao);
			editDescricao.setBackColor(Color.WHITE);
			editDescricao.setForeColor(0x003366);
			editDescricao.setText(Relatorio.descricao);
			editDescricao.setEditable(false);		

			lblCategoria = new Label("CATEGORIA: ");
			add(lblCategoria);
			lblCategoria.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editDescricao);
			lblCategoria.setBackColor(0x003366);
			lblCategoria.setForeColor(Color.WHITE);

			editCategoria = new Edit();
			add(editCategoria);
			editCategoria.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblCategoria);
			editCategoria.setBackColor(Color.WHITE);
			editCategoria.setForeColor(0x003366);
			editCategoria.setText(Relatorio.categoria);
			editCategoria.setEditable(false);

			lblTipoPagamento = new Label("PAGAMENTO");
			add(lblTipoPagamento);
			lblTipoPagamento.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editCategoria);
			lblTipoPagamento.setBackColor(0x003366);
			lblTipoPagamento.setForeColor(Color.WHITE);

			editTipoPagamento = new Edit();
			add(editTipoPagamento);
			editTipoPagamento.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblTipoPagamento);
			editTipoPagamento.setBackColor(Color.WHITE);
			editTipoPagamento.setForeColor(0x003366);
			editTipoPagamento.setText(Relatorio.tipoPagamento);
			editTipoPagamento.setEditable(false);

			lblDataVenda = new Label("DATA:            ");
			add(lblDataVenda);
			lblDataVenda.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, editTipoPagamento);
			lblDataVenda.setBackColor(0x003366);
			lblDataVenda.setForeColor(Color.WHITE);

			editDataVenda = new Edit();
			add(editDataVenda);
			editDataVenda.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblDataVenda);
			editDataVenda.setBackColor(Color.WHITE);
			editDataVenda.setForeColor(0x003366);
			editDataVenda.setText(Relatorio.dataVenda);
			editDataVenda.setEditable(false);

			lblTotal = new Label("TOTAL:          ");
			add(lblTotal);
			lblTotal.setRect(LEFT + 90, AFTER + 15, PREFERRED, PREFERRED, lblDataVenda);
			lblTotal.setBackColor(0x003366);
			lblTotal.setForeColor(Color.WHITE);

			editTotal = new Edit();
			add(editTotal);
			editTotal.setRect(AFTER, SAME, FILL - 100, PREFERRED, lblTotal);
			editTotal.setBackColor(Color.WHITE);
			editTotal.setForeColor(0x003366);
			editTotal.setText(Relatorio.valor);
			editTotal.setEditable(false);

			btnVoltar = new ArtButton("VOLTAR");
			add(btnVoltar);
			btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 5, PREFERRED + 13);
			btnVoltar.setBackColor(0x003366);
			btnVoltar.setForeColor(Color.WHITE);
			
			reposition();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO","Erro ao construir a tela DetalharVenda\n" + e);

		}

	}

	public void onEvent(Event evt) {

		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				}

			}
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação da tela DetalharVenda\n" + e);
		}

	}

}
