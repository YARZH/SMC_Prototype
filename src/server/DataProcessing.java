package server;

import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DataProcessing {
	
	public static DefaultTableModel showTable(Connection conn, String qury) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(qury);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		ResultSetMetaData metaData = rs.getMetaData();
		
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    
		return new DefaultTableModel(data, columnNames);
	}
	
}
