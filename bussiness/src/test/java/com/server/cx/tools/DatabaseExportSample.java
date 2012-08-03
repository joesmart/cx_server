package com.server.cx.tools;

import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.h2.H2Connection;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseExportSample {

    public static void main(String[] args) throws Exception {
        // database connection

//    Class driverClass = Class.forName("com.mysql.jdbc.Driver");
//    Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://10.90.3.122/cxserver", "root", "root");
        Class driverClass = Class.forName("org.h2.Driver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:h2:file:~/bussiness4;AUTO_SERVER=TRUE", "sa", "");
//        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        H2Connection connection = new H2Connection(jdbcConnection,null);
        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);

        partialDataSet.addTable("graphic_infos");
        partialDataSet.addTable("mgraphic");
        partialDataSet.addTable("userinfo");
        partialDataSet.addTable("category");
        partialDataSet.addTable("signature");
        partialDataSet.addTable("sms_message");
        partialDataSet.addTable("versioninfo");

        // FlatXmlDataSet.write(partialDataSet, new FileOutputStream("partial.xml"));

        // full database export
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));


        ITableFilter filter = new DatabaseSequenceFilter(connection);
        IDataSet dataset = new FilteredDataSet(filter, connection.createDataSet());
        FlatXmlDataSet.write(dataset, new FileOutputStream("full2.xml"));

        // dependent tables database export: export table X and all tables that
        // have a PK which is a FK on X, in the right order for insertion
        // String[] depTableNames = TablesDependencyHelper.getAllDependentTables( connection, "X" );
        // IDataSet depDataset = connection.createDataSet( depTableNames );
        // FlatXmlDataSet.write(depDataset, new FileOutputStream("dependents.xml"));

    }
}
