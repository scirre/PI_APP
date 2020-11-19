package com.teclado;

import totalcross.sys.Settings;
import totalcross.sys.SpecialKeys;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.PushButtonGroup;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.Event;
import totalcross.ui.event.KeyEvent;
import totalcross.ui.gfx.Color;

public class Teclado extends Control {

	public PushButtonGroup KeyboardBox;
	public int isPrice = -1;

	public Teclado(Container W_, int Quadrante, boolean posicionado) throws Exception {

		if (posicionado == true) {

			int w = W_.getWidth();
			int h = W_.getHeight();

			KeyboardBox = new PushButtonGroup(
					new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "<" }, 1, 4);
			W_.add(KeyboardBox);

			switch (Quadrante) {
			case 1:
				KeyboardBox.setRect(Container.LEFT + 1, Container.TOP + 1, w * 55 / 100, h * 45 / 100);
				break;
			case 2:
				KeyboardBox.setRect(Container.RIGHT - 1, Container.TOP + 1, w * 55 / 100, h * 45 / 100);
				break;
			case 3:
				KeyboardBox.setRect(Container.LEFT + 1, Container.BOTTOM - 1, w * 55 / 100, h * 45 / 100);
				break;
			case 4:
				KeyboardBox.setRect(Container.RIGHT - 1, Container.BOTTOM - 1, w * 55 / 100, h * 45 / 100);
				break;
			default:
				KeyboardBox.setRect(Container.LEFT + 1, Container.TOP + 1, w * 55 / 100, h * 45 / 100);
				break;
			}

		} else {

			KeyboardBox = new PushButtonGroup(
					new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "<-" }, 1, 4);

			W_.add(KeyboardBox);

			switch (Quadrante) {
			case 1:
				KeyboardBox.setRect(Container.LEFT + 1, Container.TOP + 1, Container.FILL - 1,
						Container.SCREENSIZE + 20);
				break;
			case 2:
				KeyboardBox.setRect(Container.LEFT + 1, Container.TOP + 1, Container.FILL - 1,
						Container.SCREENSIZE + 20);
				break;
			case 3:
				KeyboardBox.setRect(Container.LEFT + 1, Container.BOTTOM - 1, Container.FILL - 1,
						Container.SCREENSIZE + 20);
				break;
			case 4:
				KeyboardBox.setRect(Container.LEFT + 1, Container.BOTTOM - 1, Container.FILL - 1,
						Container.SCREENSIZE + 20);
				break;
			default:
				KeyboardBox.setRect(Container.LEFT + 1, Container.TOP + 1, Container.FILL - 1,
						Container.SCREENSIZE + 20);
				break;

			}
		}

		KeyboardBox.setFocusLess(true);
		KeyboardBox.setBackColor(0x363636);
		KeyboardBox.setForeColor(Color.WHITE);

	}

	public void setPosition(Container W_, Edit edt) throws Exception {
		int Cwidth = W_.getWidth();
		int meio_width = Cwidth / 2;
		int Cheight = W_.getHeight();
		int meio_height = Cheight / 2;
		// cfg.teclado_em_linha_= true;

		boolean editissupper = false;
		boolean editisleft = false;

		if (edt.getY() < meio_height) {
			editissupper = true;
			// "SUPERIOR"
		} else {
			editissupper = false;
			// "INFERIOR"
		}
		if (edt.getX() < meio_width) {
			editisleft = true;
			// "ESQUERDO"
		} else {
			editisleft = false;
			// "DIREITO"
		}

		if (editissupper && editisleft) {
			KeyboardBox.setRect(Container.RIGHT - 1, Container.BOTTOM - 1, Cwidth * 45 / 100, Cheight * 35 / 100);
		} else if (editissupper && !editisleft) {
			KeyboardBox.setRect(Container.LEFT + 1, Container.BOTTOM - 1, Cwidth * 45 / 100, Cheight * 35 / 100);
		}

		if (!editissupper && editisleft) {
			KeyboardBox.setRect(Container.RIGHT - 1, Container.TOP + 1, Cwidth * 45 / 100, Cheight * 35 / 100);
		} else if (!editissupper && !editisleft) {
			KeyboardBox.setRect(Container.LEFT + 1, Container.TOP + 1, Cwidth * 45 / 100, Cheight * 35 / 100);
		}

	}
	
	public void VisibleAll(boolean opc) {
		try {

			if (Settings.platform.equalsIgnoreCase("Win32"))
				opc = false;

			KeyboardBox.setVisible(opc);
		} catch (Exception e) {
			MessageBox msg = new MessageBox("Aviso!", "Erro ao carregar teclado\n " + e);
			msg.setBackColor(Color.WHITE);
			msg.setForeColor(0x424242);
			msg.popup();
		}
	}

	public void KeyboardEvent(Edit edt) {
		try {
			String x = KeyboardBox.getSelectedItem();
			char c = x.charAt(0);

			KeyEvent k = new KeyEvent();
			k.type = KeyEvent.KEY_PRESS;

			if (c == '<')
				k.key = SpecialKeys.BACKSPACE;// BACKSPACE
			else
				k.key = (int) c;

			k.target = edt;
			edt.postEvent(k);
			edt.repaintNow();

			return;
		} catch (Exception e) {

			return;
		}
	}

	public void onEvent(Event evt) {
		try {
			switch (evt.type) {

			}
		} catch (Exception e) {
			String erro = e.getMessage();
			MessageBox msg = new MessageBox("Aviso!", "Erro ao carregar teclado\n " + erro);
			msg.setBackColor(Color.WHITE);
			msg.setForeColor(0x424242);
			msg.popup();
		}

	}

}
