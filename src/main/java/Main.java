import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Я уже имел практику работы с ДжейсонФорматом, это не единственный проект. Могу я сдать его?
public class Main {
    //Местоположение страницы, которую парсим
    public static final String webSiteURL = "https://www.moscowmap.ru/metro.html#lines";

    //файл для хранения результата
    public static final String fileName = "src\\info.json";

    public static void main(String[] args) {

        //        List<Station> stations = readStations();
                printFile(); // Вывод станций, переходов и линий в файл

        //Создаёт и записывает на диск JSON-файл со списком станций по линиям и списком линий по формату JSON-файла из проекта SPBMetro (файл map.json).
        //Читает файл и выводит в консоль количество станций на каждой линии.

                PrintCountsStationsByLine(); //Вывод на какой линии сколько станций

        //        List<Connection> list = readConnections();
        //        for (Connection con : list) System.out.println(con.Print());
    }

    public static List<Line> readingLines()
    {
        List<Line> lines = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(webSiteURL).maxBodySize(0).get();
            Elements content = doc.getElementsByClass("js-metro-line");
            for (Element el : content) {
                lines.add(new Line(el.attr("data-line"), el.text()));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return lines;
    }

    public static List<Station> readStations()
    {
        List<Station> stations = new ArrayList<>();
        try
        {
            Document doc = Jsoup.connect(webSiteURL).maxBodySize(0).get();
            Elements content = doc.getElementsByClass("name");
            for (Element el : content) {
                stations.add(new Station(el.text(), el.parent().parent().parent().attr("data-line")));
            }
        }
        catch (Exception ex)
        {ex.printStackTrace();}
        return stations;
    }

    public static List<Connection> readConnections()
    {
        List<Connection> connections = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(webSiteURL).maxBodySize(0).get();
            Elements content = doc.getElementsByClass("t-icon-metroln");
            for (Element el : content) {
                if (el.attr("title").length() != 0) {
                    String line1, st1, line2, st2;
                    line1 = el.parent().parent().parent().attr("data-line");
                    st1 = el.parent().child(1).text();
                    line2 = el.attr("class").substring(el.attr("class").indexOf("ln-")+3);
                    String temp = el.attr("title");
                    temp = temp.substring(20);
                    temp = temp.substring(0, temp.indexOf(" ") - 1);
                    st2 = temp;
                    connections.add(new Connection(new Station(st1, line1), new Station(st2, line2)));
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return connections;
    }

    public static void printFile()
    {
        String result = "{\n";
        result = result.concat(Station.stationsToString(readStations()));
        result = result.concat(",\n\t\"connections\": [\n");
        result = result.concat(Connection.connectionsToString(readConnections()));
        result = result.concat("\n\t]");
        result = result.concat(",\n\t\"lines\" : ");
        result = result.concat(readingLines().toString());
        result = result.concat("\n}");

        try {
            File fileSource = new File(fileName);
            if (!new File(fileName).exists())
            {
                fileSource.createNewFile();
            }
            FileWriter fw = new FileWriter(fileSource);
            fw.write(result);
            fw.flush();
            fw.close();
        }
        catch (Exception ex)
        { ex.printStackTrace(); }
    }

    public static void PrintCountsStationsByLine()
    {

        try {
            Object obj = new JSONParser().parse(new FileReader(fileName));
            JSONObject jo = (JSONObject) obj;
            JSONObject stations = (JSONObject) jo.get("stations");

            Set keys = stations.keySet();
            for (Object key : keys)
            {
                JSONArray arr = (JSONArray) stations.get(key);
                System.out.println("Line " + key + " has " + arr.size() + " stations");
            }
        }
        catch (Exception ex) {ex.printStackTrace();}
    }
}
