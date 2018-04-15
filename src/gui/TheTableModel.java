package gui;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TheTableModel extends DefaultTableModel implements TableModelListener{
	private String myTable;
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
        else if (col == 2){
        	if(myTable == "queueTable" && getValueAt(row, 1) == "Round Robin"){
        		return true;
        	}
        	else{
        		if(myTable == "jobTable"){
        			return true;
        		}
        		return false;
        	}
        }
        else if(col == 3){
        	if(myTable == "queueTable"){
        		return false;
        	}
        }
        return true;
    }
    
    public void tableUsing(String tableName){
    	myTable = tableName;
    }
	public void tableChanged(TableModelEvent arg0) {
		fireTableDataChanged();
	}
}
