package analyza;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class AplikaceFrame extends JFrame {

	private JTable table;
	private Soubor seznam = new Soubor();
	private SouborModel model = new SouborModel(seznam);
	private Action actOpen, actSave, actEnd;
	private JFileChooser chooser;

	public AplikaceFrame() {
		super("Frekven�n� anal�za textu");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createActions();

		setJMenuBar(createMenu());

		getContentPane().add(createToolBar(), "North");

		getContentPane().add(createScrollPane(), "Center");

		chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.addChoosableFileFilter(new FileFilter() {

			public boolean accept(File f) {
				if (f.isDirectory() || f.getName().endsWith(".txt")) {
					return true;
				} else {
					return false;
				}
			}

			public String getDescription() {
				return null;
			}

		});

		pack();
		setVisible(true);
	}

	private void createActions() {

		actOpen = new AbstractAction("Na��st") {

			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		};

		actSave = new AbstractAction("Ulo�it") {

			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		};

		actEnd = new AbstractAction("Konec") {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

	}

	public JMenuBar createMenu() {
		JMenuBar mb = new JMenuBar();

		JMenu m = new JMenu("Soubor");
		m.add(actOpen);
		m.add(actSave);
		m.add(actEnd);

		mb.add(m);

		return mb;

	}

	public JToolBar createToolBar() {
		JToolBar tb = new JToolBar("N�stroje", JToolBar.HORIZONTAL);

		tb.add(actOpen);
		tb.add(actSave);

		return tb;
	}

	private JScrollPane createScrollPane() {
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBorder(BorderFactory.createTitledBorder("Soubory"));

		return sp;
	}

	private void openFile() {
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			Slovo soub = null;
			String slova = "";
			String radek;
			try {
				BufferedReader br = new BufferedReader(new FileReader(chooser
						.getSelectedFile().getAbsolutePath()));

				while ((radek = br.readLine()) != null) { // na�te v�echny ��dky
															// do slova
					slova += radek.trim(); // vyma�e v�echny b�l� znaky na
											// za��tku a konci v�ty
				}
				slova = slova.replace(",", ""); // odstrani v�echen v�skyt
												// dan�ho znaku
				slova = slova.replace(".", "");
				slova = slova.replace("?", "");
				slova = slova.replace("!", "");
				slova = slova.replace("\t", "");
				String[] casti = slova.split(" "); // rozd�l� po meze�e do pole
													// casti

				Set<String> s1 = new LinkedHashSet<String>(); // vyvo�� hashmapu
																// ta
																// automaticky
																// odebere
																// duplicity
				for (int i = 0; i < casti.length; i++) { // do hashmapy s1 vlo��
															// obsah pole casti
					s1.add(casti[i]);
				}

				for (String string : s1) { // spo��ta po�et duplicit podle
											// pole(zde jsou) a hashmapy(v n�
											// ji� nejsou)
					int pocet = 0;

					for (int i = 0; i < casti.length; i++) {
						if (string.equals(casti[i])) {
							pocet++;
						}
					}
					double p = ((double) (pocet) / (double) (casti.length)) * 100; // spo��t�n�
																					// procentu�ln�ho
																					// obsahu
																					// slov
					p = Math.round(p * 100);
					p = p / 100;

					soub = new Slovo(string, pocet, p);
					seznam.add(soub); // p�id� do seznamu nov� slovo
				}

				br.close(); // uzav�e bufferdreader
			} catch (FileNotFoundException e) {
				vypisZpravu(this, "Soubor "
						+ chooser.getSelectedFile().getName() + " neexistuje!");
			} catch (IOException e) {
				vypisZpravu(this, "Chyba p�i na��t�n� dat!");
			}

			model = new SouborModel(seznam);
			table.setModel(model);
		}
	}

	private void saveFile() {
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			Slovo s;
			String j, k, l;
			int i = 0;
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(chooser
						.getSelectedFile().getAbsolutePath()));
				while (i < seznam.size()) {
					s = (Slovo) seznam.get(i);
					j = s.getSlovo();
					k = String.valueOf(s.getPocet_vyskytu());
					l = String.valueOf(s.getProcento_vyskytu());
					pw.println(j + ";" + k + ";" + l);
					i++;
				}
				pw.close();
			} catch (IOException e) {
				vypisZpravu(this, "Chyba p�i ukl�d�n� dat!");
			}
		}
	}

	private void vypisZpravu(JFrame f, String text) {
		JOptionPane.showMessageDialog(f, text, "Chybov� zpr�va",
				JOptionPane.ERROR_MESSAGE);
	}

	MouseListener ml = new MouseListener() {
		public void mouseClicked(MouseEvent e) {
			JTableHeader h = (JTableHeader) e.getSource();
			TableColumnModel columnModel = h.getColumnModel();
			int viewColumn = columnModel.getColumnIndexAtX(e.getX());
			int column = columnModel.getColumn(viewColumn).getModelIndex();
			seznam.seradit(column);
			model.aktualizuj();
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	};
}
