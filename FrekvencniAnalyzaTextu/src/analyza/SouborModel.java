package analyza;

import javax.swing.table.AbstractTableModel;

public class SouborModel extends AbstractTableModel {
	
	private Soubor seznam = null;
	
	public SouborModel(Soubor seznam){
		this.seznam = seznam;
	}
	

	public int getRowCount() {
		// vrátí velikost seznamu
		return seznam.size();
	}

	public int getColumnCount() {
		return 3;
	}
	
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0: return "Slovo";
		case 1: return "Poèet výskytù";
		case 2: return "Procento výskytù";
		}
		return "";
	}
	
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0: return String.class;
		case 1: return Integer.class;
		case 2: return Integer.class;
		}
		return null;
		
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Slovo s = (Slovo)seznam.get(rowIndex);
		switch (columnIndex){
		case 0: return s.getSlovo();
		case 1: return new Integer (s.getPocet_vyskytu());
		case 2: return new Double(s.getProcento_vyskytu());
		}
		
		return null;
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 2;
	}
	
	public void setValueAt(Object hodnota, int rowIndex, int columnIndex){
		Slovo s = (Slovo)seznam.get(rowIndex);
		switch (columnIndex){
		case 1: s.setPocet_vyskytu(((Integer) hodnota).intValue());
		case 2: s.setProcento_vyskytu(((Double) hodnota).intValue());
		break;
		}
	}
	
	public void aktualizuj() {
		fireTableDataChanged();
	}

}

