package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ParseXml {

    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    //Список обьектов из xml фалйа на основе модели Plant
    private static final List<Plant> plantList = new ArrayList<>();

    //Массив атрибутов поля CATALOG
    private static String[] catalogAttributes;

    public void parseXmlFile(String path) throws ParserConfigurationException, IOException, SAXException {

        plantList.clear();

        File xmlFile = new File(path);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        //Получение атрибутов поля CATALOG из xml
        catalogAttributes = new String[]{
                document.getElementsByTagName("CATALOG").item(0).getAttributes().getNamedItem("date").getTextContent(),
                document.getElementsByTagName("CATALOG").item(0).getAttributes().getNamedItem("company").getTextContent(),
                document.getElementsByTagName("CATALOG").item(0).getAttributes().getNamedItem("uuid").getTextContent()};


        //Изменение записи даты
        String[] date = catalogAttributes[0].split("\\.");
        catalogAttributes[0] = date[2] + "-" + date[1] + "-" + date[0];


        NodeList nodeList = document.getElementsByTagName("PLANT");


        for (int i = 0; i < nodeList.getLength(); i++) {
            plantList.add(getPlant(nodeList.item(i)));
        }

        //Вывод обьектов в консоль
        /*System.out.println(Arrays.toString(catalogAttributes));

        for (Plant plant : plantList) {
            System.out.println(plant.plantToString());
        }*/
    }

    //Создания нового обьекта
    private static Plant getPlant(Node node) {

        Plant plant = new Plant();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            plant.setCommon(getTagValue("COMMON", element));
            plant.setBotanical(getTagValue("BOTANICAL", element));
            plant.setZone(Integer.parseInt(getTagValue("ZONE", element)));
            plant.setLight(getTagValue("LIGHT", element));
            plant.setPrice(Float.parseFloat(getTagValue("PRICE", element).substring(1)));
            plant.setAvailability(Integer.parseInt(getTagValue("AVAILABILITY", element)));
        }
        return plant;
    }

    //Получения значения поля из xml файла
    private static String getTagValue(String tag, Element element) {

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);

        if (Objects.equals(tag, "ZONE")) {
            return zoneParse(node.getNodeValue());
        }

        return node.getNodeValue();
    }

    public static List<Plant> getPlantList() {
        return plantList;
    }

    public static String[] getCatalogAttributes() {
        return catalogAttributes;
    }

    //Обработка нестандартных значений поля ZONE в xml файле
    private static String zoneParse(String value) {

        if (Pattern.matches("\\d" + " - " + "\\d", value)) {
            return value.split(" - ")[0];
        }

        return switch (value) {
            case ("Годичный") -> "1";
            default -> value;
        };
    }
}
