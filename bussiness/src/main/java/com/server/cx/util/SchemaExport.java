package com.server.cx.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yanjianzou
 * Date: 9/21/12
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchemaExport {

    public static void main(String[] args) {

        boolean drop = true;
        boolean create = true;
        String outFile = null;
        String delimiter = ";";
        String unitName = "defaultPU";
        String propertiesName = "application.properties";
        Map map = new HashMap();


        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                if (args[i].equals("--drop")) {
                    drop = true;
                } else if (args[i].equals("--create")) {
                    create = true;
                } else if (args[i].startsWith("--output=")) {
                    outFile = args[i].substring(9);
                } else if (args[i].startsWith("--delimiter=")) {
                    delimiter = args[i].substring(12);
                } else if(args[i].startsWith("--properties=")){
                    propertiesName = args[i].substring(13);
                }
            } else {
                unitName = args[i];
            }
        }
        try {
            Properties properties = new Properties();
            properties.load(SchemaExport.class.getResourceAsStream("/"+propertiesName));
            if(properties.entrySet().size()>0){
                for(Map.Entry<Object,Object> temp:properties.entrySet()){
                    map.put(temp.getKey(),temp.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Formatter formatter = FormatStyle.DDL.getFormatter();

        Ejb3Configuration jpaConfiguration = new Ejb3Configuration().configure(unitName, map);
        Configuration hibernateConfiguration = jpaConfiguration.getHibernateConfiguration();

        String[] createSQL = hibernateConfiguration.generateSchemaCreationScript(
                Dialect.getDialect(hibernateConfiguration.getProperties()));
        String[] dropSQL = hibernateConfiguration.generateDropSchemaScript(
                Dialect.getDialect(hibernateConfiguration.getProperties()));
        File file = new File(outFile);
        if(file.exists()){
            file.delete();
        }
        System.out.println(dropSQL.length);
        System.out.println("drop:"+drop);
        List<String> sqlList = new ArrayList<String>();
        Collections.addAll(sqlList, dropSQL);
        Collections.addAll(sqlList, createSQL);
        export(file, delimiter, formatter, sqlList);
    }

    private static void export(File outFile, String delimiter, Formatter formatter, List<String> sqlList) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outFile);
            for (String string : sqlList) {
                writer.append(formatter.format(string)  + delimiter + "\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
