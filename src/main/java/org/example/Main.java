package org.example;

import org.apache.tools.ant.DirectoryScanner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, SQLException {

        //String path = "D:\\Telegram Desktop\\Telegram Desktop\\data\\data\\plants*.xml";

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь к файлу/файлам (с помощью маски):");
        String path = sc.nextLine();
        File file = new File(path);

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{file.getName()});
        scanner.setBasedir(file.getParentFile());
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        if (files.length == 0) {
            System.err.println("Путь указан неверно");
        } else {
            for (String s : files) {
                new ParseXml().parseXmlFile(file.getParentFile() + "\\" + s);
                new DatabaseEntry().databaseEntry();
            }
        }
    }
}