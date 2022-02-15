import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Connection {

    Station st1;
    Station st2;

    public Connection(Station st1, Station st2) {
        this.st1 = st1;
        this.st2 = st2;
    }

    public Station getSt1() {
        return st1;
    }

    public void setSt1(Station st1) {
        this.st1 = st1;
    }

    public Station getSt2() {
        return st2;
    }

    public void setSt2(Station st2) {
        this.st2 = st2;
    }

    public String toString()
    {
        String result = "\t\t[\n\t\t\t{" +
                "\n\t\t\t\t\"line\" : \"" + st1.numberLine +
                "\",\n\t\t\t\t\"station\" : \"" + st1.name + "\"\n\t\t\t},\n\t\t\t{" +
                "\n\t\t\t\t\"line\" : \"" + st2.numberLine +
                "\",\n\t\t\t\t\"station\" : \"" + st2.name + "\"\n\t\t\t}" +
                "\n\t\t]";

        return new String(result.getBytes(), StandardCharsets.UTF_8);
//        return result;
    }

    public static String connectionsToString(List<Connection> list)
    {
        String[] arr = new String[list.size()];
        int i = 0;
        for (Connection el : list)
        {
            arr[i] = el.toString();
            i++;
        }

        return new String(String.join(",\n", arr));
    }
}
