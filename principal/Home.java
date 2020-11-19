package principal;

import com.auxiliares.Auxiliares;
import com.cadastrarEmpresa.Cadastrar;
import com.litebase.LitebasePack;
import com.menu.Menu;
import com.tabelas.Tabelas;

import litebase.ResultSet;
import nx.componentes.ArtButton;
import totalcross.sys.Convert;
import totalcross.sys.Settings;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class Home extends MainWindow{
	
		public static Edit				editEmpresa;
		public static Edit				editCnpj;
		public static Edit              editUsuario;
		public static Edit				editCodigo;
		
		public Label					lblCodigo; 
		public Label					lblEmpresa;
		public Label            		lblCnpj;
		public Label					lblUsuario;
		public ArtButton        		btnCadastrar;
		public ArtButton        		btnEntrar;
		public ArtButton				btnBuscar;
		public ArtButton				btnAtualizar;
		public ArtButton       		    btnSair;
//		public ArtButton       		    btnAdm;
		public ImageControl				imgHome;
		
		
	public Home() {
		
		super("CONTROLE", TAB_ONLY_BORDER);
		
		setBackColor(0x003366);
		Tabelas tabelas = new Tabelas();
		tabelas.criaTabelas();
		
		Settings.applicationId   = "CTRL";
		Settings.appDescription  = "CONTROLE";
		Settings.appVersion 	 = Auxiliares.VERSAO;
		Settings.useNewFont 	 = Settings.fingerTouch = true;		
		Settings.dateFormat 	 = Settings.DATE_DMY;
		
//		Settings.resizableWindow = true;
		
	}
		
		public void initUI(){
			
		try {
			
			imgHome = new ImageControl(new Image("img/home.png"));
			imgHome.scaleToFit = true;
			imgHome.centerImage = true;
			add(imgHome, CENTER, TOP + 20, SCREENSIZE + 20, PREFERRED, btnAtualizar);
			
			lblEmpresa = new Label("EMPRESA: ");
			add(lblEmpresa);
			lblEmpresa.setBackColor(0x003366);
			lblEmpresa.setForeColor(Color.WHITE);
			lblEmpresa.setRect(CENTER, AFTER + 20, PREFERRED, PREFERRED, imgHome);

			add(editEmpresa = new Edit(), LEFT, AFTER + 1, PREFERRED, PREFERRED);
			editEmpresa.setBackColor(Color.WHITE);
			editEmpresa.setForeColor(0x003366);
			editEmpresa.setEditable(false);

			lblCnpj = new Label("CNPJ: ");
			add(lblCnpj);
			lblCnpj.setBackColor(0x003366);
			lblCnpj.setForeColor(Color.WHITE);
			lblCnpj.setRect(CENTER, AFTER + 10, PREFERRED, PREFERRED, editEmpresa);

			add(editCnpj = new Edit(), LEFT, AFTER + 1, PREFERRED, PREFERRED);
			editCnpj.setBackColor(Color.WHITE);
			editCnpj.setForeColor(0x003366);
			editCnpj.setEditable(false);
			
			lblUsuario = new Label("USUÁRIO: ");
			add(lblUsuario);
			lblUsuario.setBackColor(0x003366);
			lblUsuario.setForeColor(Color.WHITE);
			lblUsuario.setRect(CENTER, AFTER + 10, PREFERRED, PREFERRED, editCnpj);
			
			add(editUsuario = new Edit(), LEFT, AFTER + 1, PREFERRED, PREFERRED);
			editUsuario.setBackColor(Color.WHITE);
			editUsuario.setForeColor(0x003366);
			editUsuario.setEditable(false);
			
			lblCodigo = new Label("CÓDIGO: ");
			add(lblCodigo);
			lblCodigo.setBackColor(0x003366);
			lblCodigo.setForeColor(Color.WHITE);
			lblCodigo.setRect(CENTER, AFTER + 10, PREFERRED, PREFERRED, editUsuario);
			
			add(editCodigo = new Edit(), LEFT, AFTER + 1, PREFERRED, PREFERRED);
			editCodigo.setMode(TAB_ONLY_BORDER);
			editCodigo.setBackColor(Color.WHITE);
			editCodigo.setForeColor(0x003366);
			editCodigo.setEditable(false);
			
			btnSair = new ArtButton("SAIR");
			add(btnSair);
			btnSair.setBackColor(0xDF7401);
			btnSair.setForeColor(Color.WHITE);
			btnSair.setRect(CENTER,BOTTOM, SCREENSIZE + 100, PREFERRED + 15, btnCadastrar);
			
			btnCadastrar = new ArtButton("CADASTRAR");
			add(btnCadastrar);
			btnCadastrar.setBackColor(0x003366);
			btnCadastrar.setForeColor(Color.WHITE);
			btnCadastrar.setRect(CENTER, BEFORE, SCREENSIZE + 100, PREFERRED + 15, btnSair);

			btnEntrar = new ArtButton("ENTRAR");
			add(btnEntrar);
			btnEntrar.setBackColor(0x003366);
			btnEntrar.setForeColor(Color.WHITE);
			btnEntrar.setRect(CENTER, BEFORE, SCREENSIZE + 100, PREFERRED + 15, btnCadastrar);
			
			reposition();
			
			buscaEmpresaCadastrada();
			
		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao construir a tela principal\n" + e);
		}	
		
	}
		
	public void onEvent(Event evt) {
		try {
			switch (evt.type) {
			case ControlEvent.PRESSED:

				if (evt.target == btnEntrar) {

					if (editEmpresa.getText().equals("") || editCnpj.getText().equals("")
							|| editUsuario.getText().equals("")) {
						Auxiliares.artMsgbox("CONTROLE", "Deve-se cadastrar uma empresa!");

					} else {
						boolean empresaCadastrada = false;
						empresaCadastrada = validaEmpresa(empresaCadastrada);
						if (empresaCadastrada == true) {
							Menu menu = new Menu();
							menu.popup();
						}
					}

				} else if (evt.target == btnCadastrar) {
					Cadastrar cadastrar = new Cadastrar();
					cadastrar.popup();

				} else if (evt.target == btnSair) {

					String[] ArtButtonArray = { "Sim", "Não" };

					int i = Auxiliares.artMsgbox("CONTROLE", "Deseja sair do sistema?", ArtButtonArray);

					if (i == 1) {
						return;

					} else {
						Home.exit(0);

					}
				}
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro na validação da tela principal\n" + e);
		}

	}

	public void buscaEmpresaCadastrada(){
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {
				lb = new LitebasePack();
				sql = "SELECT * FROM EMPRESA ";

				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() == 0) {
					Auxiliares.artMsgbox("CONTROLE", "Sistema não possui empresa cadastrada!");
					return;
				}

				editEmpresa.setText(rs.getString("NOME"));
				editCnpj.setText(rs.getString("CNPJ"));
				editUsuario.setText(rs.getString("USUARIO"));
				editCodigo.setText(Convert.toString(rs.getInt("CODIGO")));

			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar empresa\n" + e);
			return;
		}
	}
	
	public boolean validaEmpresa(boolean empresaCadastrada) {
		String sql = "";
		LitebasePack lb = null;
		ResultSet rs = null;

		try {

			try {
				lb = new LitebasePack();
				
				if (!lb.exists("EMPRESA")) {

					sql = " create table empresa ( " + "codigo int," + " nome char(40), " + " cnpj char(30), "
						+ " usuario char(40) " + ")";

					lb.execute(sql);
					lb.execute("create index empresa01 ON empresa(codigo,nome)");
				}
				
				sql = "SELECT * FROM EMPRESA "
				    + " WHERE CODIGO = " + editCodigo.getText();

				rs = lb.executeQuery(sql);
				rs.first();

				if (rs.getRowCount() == 0) {
					Auxiliares.artMsgbox("CONTROLE", "Sistema não possui empresa cadastrada!");
					editEmpresa.setText("");
					editCnpj.setText("");
					editUsuario.setText("");
					editCodigo.setText("");
					return empresaCadastrada = false;
				}
				
				return empresaCadastrada = true;
				
			} finally {
				if (lb != null)
					lb.closeAll();
			}

		} catch (Exception e) {
			Auxiliares.artMsgbox("ERRO", "Erro ao buscar empresa\n" + e);
			return empresaCadastrada;
		}
		
	}
	
//------------> TITLE APP <-------------	
//	super("ARTVENDAS_IN", TAB_ONLY_BORDER);
//	 //super("ARTVENDAS_IN_II", TAB_ONLY_BORDER);
//	LitebasePack lb = null;
//	try {
//		lb = new LitebasePack();
//		lb.CheckAllTables();
//	} catch (Exception e) {
//		Auxiliares.artMsgbox("ERRO" + e.getMessage());
//	} finally {
//		if (lb != null)
//			lb.closeAll();
//	}
//
//	setBackColor(Color.WHITE);
//	UIColors.controlsBack = Color.WHITE;
//
//	// Instantiate the screens
//
//	UIColors.controlsBack = Color.WHITE;
//	Settings.uiAdjustmentsBasedOnFontHeight = true;
			
}
