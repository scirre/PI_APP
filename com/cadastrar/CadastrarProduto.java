package com.cadastrar;


import java.util.Random;

import com.auxiliares.Auxiliares;
import com.bottom.BottomImg;
import com.litebase.LitebasePack;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PenEvent;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.util.Date;

public class CadastrarProduto extends totalcross.ui.Window{
	
	private Label 							lblCategoria;
	private Label							lblProduto;
	private Label							lblMarca;
	private Label							lblDescricao;
	private Label							lblQuantidade;
	private Label							lblValor;
	private ComboBox						cmbCategoria;
	private ComboBox						cmbProduto;
	private ComboBox						cmbMarca;
	private ComboBox						cmbDescricao;
	private Edit                            editQuantidade;
	private Edit 							editValor;
	private ArtButton						btnCadastrar;
	private ArtButton 						btnVoltar;
	public  Button					        btnInformacao;
	private ImageControl					imgAdicionar;
	
	public int								codigoProduto;

	public CadastrarProduto() {
		setBackColor(0x003366);
		initUI();
		carregaCmbCategoria();
	}
	
	public void initUI() {
		
		try {
			
		    btnInformacao = BottomImg.imageOnly(new Image("img/informacao.png"));
		    add(btnInformacao, RIGHT + 10, TOP + 10, SCREENSIZE + 14, PREFERRED);
				
			imgAdicionar = new ImageControl(new Image("img/adicionar.png"));
			imgAdicionar.scaleToFit = true;
			imgAdicionar.centerImage = true;
			add(imgAdicionar,  CENTER, AFTER - 50, SCREENSIZE + 30, SCREENSIZE + 50, btnInformacao);
			
			lblCategoria = new Label("CATEGORIA:  ");
			add(lblCategoria);
			lblCategoria.setRect(LEFT + 90, AFTER - 10, PREFERRED, PREFERRED, imgAdicionar);
			lblCategoria.setBackColor(0x003366);
			lblCategoria.setForeColor(Color.WHITE);
			
			cmbCategoria = new ComboBox();
			add(cmbCategoria);
			cmbCategoria.setRect(AFTER + 5, SAME, FILL - 120, PREFERRED, lblCategoria);
			
			lblProduto = new Label("PRODUTO:     ");
			add(lblProduto);
			lblProduto.setRect(LEFT + 90, AFTER + 20, PREFERRED, PREFERRED, cmbCategoria);
			lblProduto.setBackColor(0x003366);
			lblProduto.setForeColor(Color.WHITE);
			
			cmbProduto = new ComboBox();
			add(cmbProduto);
			cmbProduto.setRect(AFTER + 5, SAME, FILL - 120, PREFERRED, lblProduto);
			
			lblMarca = new Label("MARCA:         ");
			add(lblMarca);
			lblMarca.setRect(LEFT + 90, AFTER + 20, PREFERRED, PREFERRED, cmbProduto);
			lblMarca.setBackColor(0x003366);
			lblMarca.setForeColor(Color.WHITE);
						
			cmbMarca = new ComboBox();			
			add(cmbMarca);
			cmbMarca.setRect(AFTER + 5, SAME, FILL - 120, PREFERRED, lblMarca);	
			
			lblDescricao = new Label("DESCRIÇÃO:  ");
			add(lblDescricao);
			lblDescricao.setRect(LEFT + 90, AFTER + 20, PREFERRED, PREFERRED, cmbMarca);
			lblDescricao.setForeColor(Color.WHITE);
			lblDescricao.setBackColor(0x003366);
			
			cmbDescricao = new ComboBox();			
			add(cmbDescricao);
			cmbDescricao.setRect(AFTER + 5, SAME, FILL - 120, PREFERRED, lblDescricao);
			
			lblQuantidade = new Label("QUANTIDADE:");
			add(lblQuantidade);
			lblQuantidade.setRect(LEFT + 90, AFTER + 20, PREFERRED, PREFERRED, cmbDescricao);
			lblQuantidade.setForeColor(Color.WHITE);
			lblQuantidade.setBackColor(0x003366);
			
			editQuantidade = new Edit();			
			add(editQuantidade);
			editQuantidade.setRect(AFTER, SAME, FILL - 120, PREFERRED, lblQuantidade);
			editQuantidade.setBackColor(Color.WHITE);
			editQuantidade.setForeColor(0x003366);
			editQuantidade.setValidChars("0123456789");
			
			lblValor = new Label("VALOR:           ");
			add(lblValor);
			lblValor.setRect(LEFT + 90, AFTER + 20, PREFERRED, PREFERRED, editQuantidade);
			lblValor.setForeColor(Color.WHITE);
			lblValor.setBackColor(0x003366);
			
			editValor = new Edit();			
			add(editValor);
			editValor.setRect(AFTER, SAME, FILL - 120, PREFERRED, lblValor);
			editValor.setBackColor(Color.WHITE);
			editValor.setForeColor(0x003366);
			editValor.setValidChars("0123456789.");

			btnCadastrar = new ArtButton("CADASTRAR");
	        add(btnCadastrar);
	        btnCadastrar.setRect(LEFT - 2, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
	        btnCadastrar.setBackColor(0x009933);
	        btnCadastrar.setForeColor(Color.WHITE);
					    
			btnVoltar = new ArtButton("VOLTAR");
	        add(btnVoltar);
	        btnVoltar.setRect(RIGHT, BOTTOM, SCREENSIZE - 4, PREFERRED + 15);
	        btnVoltar.setBackColor(0x003366);
	        btnVoltar.setForeColor(Color.WHITE);
	        
	        reposition();
	        
	        editQuantidade.requestFocus();
	        
//	        buscaPrimeiroLogin();
	        
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir a tela cadastrarProduto\n" + e);

		}
		
	}
	public void onEvent(Event evt) {
		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnVoltar) {
					unpop();

				} else if (evt.target == btnCadastrar) {

					if (!editValor.getText().equals("") && !editQuantidade.getText().equals("")
							&& !cmbCategoria.getSelectedItem().toString().equals("")
							&& !cmbMarca.getSelectedItem().toString().equals("")
							&& !cmbProduto.getSelectedItem().toString().equals("")
							&& !cmbDescricao.getSelectedItem().toString().equals("")) {

						if (editValor.getText().equals(".0") || editValor.getText().equals(".1")
								|| editValor.getText().equals(".2") || editValor.getText().equals(".3")
								|| editValor.getText().equals(".4") || editValor.getText().equals(".5")
								|| editValor.getText().equals(".6") || editValor.getText().equals(".7")
								|| editValor.getText().equals(".8") || editValor.getText().equals(".9")) {

							Auxiliares.artMsgbox("CONTROLE",
									"Valor inserido incorreto!\n tente '2.23', por exemplo... ");
							return;
						}
						
						boolean produtoEstoque = false;
						produtoEstoque = validaProdutoEstoque(produtoEstoque);
						
						if (produtoEstoque) {
							Auxiliares.artMsgbox("CONTROLE",
									"Produto já cadastrado no estoque!\nObs: Tente altera-lo na aba estoque");

							cmbProduto.removeAll();
							cmbMarca.removeAll();
							cmbCategoria.removeAll();
							cmbDescricao.removeAll();
							editQuantidade.clear();
							editValor.clear();

							return;
						}
						
						
						String[] ArtButtonArray = { "Sim", "Não" };

						int i = Auxiliares.artMsgbox("CONTROLE", "Deseja cadastrar o produto no estoque?",
								ArtButtonArray);

						if (i == 1) {
							return;

						} else {

							cadastrarProdutoNoEstoque();

							Auxiliares.artMsgbox("CONTROLE", "Produto cadastrado no estoque!");
						}

					} else {
						Auxiliares.artMsgbox("CONTROLE", "Preencha todos os campos!");
					}

				} else if (evt.target == cmbCategoria) {
					cmbProduto.removeAll();
					cmbMarca.removeAll();
					cmbDescricao.removeAll();
					carregaCmbProduto();			
					carregaCmbDescricao();

				}else if (evt.target == cmbProduto) {
					cmbMarca.removeAll();
					carregaCmbMarca();
					
				} else if (evt.target == btnInformacao) {
					Auxiliares.artMsgbox("CONTROLE",
							"Essa é a tela de cadastro de produto. Preencha todos os campos corretamente.\nNo campo do valor só será aceito número e ponto.\nNão use valores como '00.15.50', pois o calculo pode não ficar correto. E lembre-se de não colocar espaço entre os números.");
				}

			}

		}catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação da tela cadastrarProduto\n" + e);
			}
			
			
		}

	public void cadastrarProdutoNoEstoque() {
		String sql = "";
		LitebasePack lb = null;

		try {
			Random random = new Random();
			int codigo = random.nextInt(900);
			String dataEntrada;
			Date year = new Date();
			Date month = new Date();
			Date day = new Date();

			dataEntrada = Date.formatDate(year.getYear(), month.getMonth(), day.getDay());

			lb = new LitebasePack();

			sql = "INSERT INTO ESTOQUE " + "(" + " CODIGO, PRODUTO, VALOR, QUANTIDADE, CATEGORIA, MARCA, DESCRICAO,"
					+ " DATAENTRADA " + ")" + " VALUES " + "( '" + codigo + "' , '" + cmbProduto.getSelectedItem()
					+ "', '" + editValor.getText() + "', '" + editQuantidade.getText() + "', '"
					+ cmbCategoria.getSelectedItem() + "', '" + cmbMarca.getSelectedItem() + "', '"
					+ cmbDescricao.getSelectedItem() + "', '" + dataEntrada + "'" + ")";

			lb.executeUpdate(sql);

			cmbProduto.removeAll();
			cmbMarca.removeAll();
			cmbCategoria.removeAll();
			cmbDescricao.removeAll();
			editQuantidade.clear();
			editValor.clear();

			carregaCmbCategoria();

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao cadastrarProdutoNoEstoque\n" + e);
			return;
		}

	}
	
	public void carregaCmbProduto() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT PRODUTO, CATEGORIA FROM PRODUTO "
						+ " WHERE CATEGORIA = " + "'" + cmbCategoria.getSelectedItem() + "'";;

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
				Auxiliares.artMsgbox("ERRO", "Erro ao carregaCmbProduto\n" + e);

			}

		}
	}
	
	public void carregaCmbCategoria() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					cmbCategoria.removeAll();
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
	
	public void carregaCmbMarca() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;
			ResultSet rs2 = null;

			try {
				try {
					lb = new LitebasePack();
					
					sql ="SELECT CODIGO FROM PRODUTO "
						+" WHERE PRODUTO = " + "'" + cmbProduto.getSelectedItem() + "'";	
					
					rs2 = lb.executeQuery(sql);
					rs2.first();
					codigoProduto = rs2.getInt("CODIGO");
					
					
					sql = " SELECT DESCRICAO, CODIGOPROD"
						+ " FROM MARCA"
						+ " WHERE CODIGOPROD = " + codigoProduto;

					rs = lb.executeQuery(sql);
					rs.first();
					for (int i = 0; rs.getRowCount() > i; i++) {
						String[] b = new String[1];
						b[0] = rs.getString("DESCRICAO");
						cmbMarca.add(b);
						rs.next();
					} 
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao carregaCmbMarca\n" + e);

			}

		}
	}
	
	public void carregaCmbDescricao() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();

					if (cmbCategoria.getSelectedItem().equals("ALIMENTACAO")) {
						sql = " SELECT DESCRICAO FROM DESCRICAOPESO ";

						rs = lb.executeQuery(sql);
						rs.first();
						for (int i = 0; rs.getRowCount() > i; i++) {
							String[] b = new String[1];
							b[0] = rs.getString("DESCRICAO");
							cmbDescricao.add(b);
							rs.next();
						}
					} else if (cmbCategoria.getSelectedItem().equals("DIVERSOS")
							|| cmbCategoria.getSelectedItem().equals("SUCO")) {

						sql = " SELECT DESCRICAO FROM DESCRICAO ";

						rs = lb.executeQuery(sql);
						rs.first();
						for (int i = 0; rs.getRowCount() > i; i++) {
							String[] b = new String[1];
							b[0] = rs.getString("DESCRICAO");
							cmbDescricao.add(b);
							rs.next();
						}

						sql = " SELECT DESCRICAO FROM DESCRICAOPESO ";

						rs = lb.executeQuery(sql);
						rs.first();
						for (int i = 0; rs.getRowCount() > i; i++) {
							String[] b = new String[1];
							b[0] = rs.getString("DESCRICAO");
							cmbDescricao.add(b);
							rs.next();
						}

					}

					else {

						sql = " SELECT DESCRICAO FROM DESCRICAO ";

						rs = lb.executeQuery(sql);
						rs.first();
						for (int i = 0; rs.getRowCount() > i; i++) {
							String[] b = new String[1];
							b[0] = rs.getString("DESCRICAO");
							cmbDescricao.add(b);
							rs.next();
						}
					}
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao carregaCmbDescricao\n" + e);

			}

		}
	}
	
	public boolean validaProdutoEstoque( boolean produtoEstoque) {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					
					sql =" SELECT MARCA, DESCRICAO, PRODUTO FROM ESTOQUE "
						+" WHERE MARCA =   " + "'" + cmbMarca.getSelectedItem() + "'"
					    +" AND DESCRICAO = " + "'" + cmbDescricao.getSelectedItem() + "'"
					    +" AND PRODUTO =   " + "'" + cmbProduto.getSelectedItem() + "'";;
					
					rs = lb.executeQuery(sql);
					rs.first();
					
					if (rs.getRowCount() == 0) {
						return produtoEstoque = false;

					} else {
						
						return produtoEstoque = true;
					}
 
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao carregaCmbMarca\n" + e);
				
				return produtoEstoque;
			}

		}
	}
	
	public static String completNumber(int arg, int len) throws Exception {
		return completNumber(Convert.toString(arg), len);
	}

	public static String completNumber(String arg, int len) throws Exception {
		String result = "";
		if (arg == null)
			arg = "";

		if (arg.length() > len)
			arg = arg.substring(0, len);

		for (int x = 0; x < len - arg.length(); x++)
			result += "0";

		result = result + arg;
		return result;
	}
	
	public void buscaPrimeiroLogin() {
		{
			String sql = "";
			LitebasePack lb = null;
			ResultSet rs = null;

			try {
				try {
					lb = new LitebasePack();
					sql = " SELECT DATACADASTRO FROM EMPRESA";

					rs = lb.executeQuery(sql);
					rs.first();
					
					String dataAtual;
					Date year = new Date();
					Date month = new Date();
					Date day = new Date();

					dataAtual = Date.formatDate(day.getDay(), month.getMonth(), year.getYear() );
					
					if(dataAtual.equals(rs.getDate("DATACADASTRO").toString())){
						Auxiliares.artMsgbox("CONTROLE", "Bem Vindo a Tela de Cadastro de Produtos!\n-> Por favor prencha todos os campos.\n"
								+ "-> No valor do produto só será aceito números inteiros\n-> Não será permitido o uso de ',' ou outros sinais.\n"
								+ "-> Lembre-se de inserir o valor corretamente. Ex: '25.22'\n"
								+ "-> Caso insira um valor como: '.25.22', o sistema não irá calcular\n"
								+ "o valor da venda corretamente!");
					}
					
				} finally {
					if (lb != null)
						lb.closeAll();

				}
			} catch (Exception e) {
				Auxiliares.artMsgbox("ERRO", "Erro ao buscaPrimeiroLogin\n" + e);

			}

		}
	}

	public static int getDataInt(String formato) throws Exception {
		Date data = new Date();
		String dataFormatada = "";

		if (formato.equalsIgnoreCase("ddmmyyyy"))
			dataFormatada = completNumber(data.getDay(), 2) + completNumber(data.getMonth(), 2)
					+ Convert.toString(data.getYear());
		
		else if (formato.equalsIgnoreCase("yyyymmdd"))
			dataFormatada = Convert.toString(data.getYear()) +completNumber(data.getMonth(), 2)
					+ completNumber(data.getDay(), 2);

		return Convert.toInt(dataFormatada);
	}
	
}
