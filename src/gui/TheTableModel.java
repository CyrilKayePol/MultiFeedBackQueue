package gui;

import javax.swing.table.DefaultTableModel;

public class TheTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	public TheTableModel(Object rowData[][], Object columnNames[]) {
	    super(rowData, columnNames);
    }

    public Class<?> getColumnClass(int col) {
    	if (col == 1 || col == 2 || col == 3)      
	         return Integer.class;
        else return String.class; 
	}
    public boolean isCellEditable(int row, int col) {
        if (col == 0)      
        	return false;
	    else return true;
    }
}
