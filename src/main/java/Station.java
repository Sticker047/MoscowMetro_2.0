import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Station {
    String name;
    String numberLine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public Station(String name, String numberLine) {
        this.name = name;
        this.numberLine = numberLine;
    }

    @Override
    public String toString() {
        return "\"" + name + "\" " + numberLine;
    }

    public static String stationsToString(List<Station> list)
    {
        Set<String> lineNames = new HashSet<>();
        String result = "";

        for (Station el : list)
        {
            lineNames.add(el.numberLine);
        }
        boolean firstEl = true;
        result = result.concat("\t\"stations\" : {");
        for (String name : lineNames) {
            if (firstEl) firstEl = false;
            else result = result.concat(",");
            result = result.concat("\n\t\t\"" + name + "\" : [\n");
            String res;
            List<String> names = new ArrayList<>();
            for (Station st : list)
            {
                if (name.equals(st.getNumberLine()))
                {
                    names.add("\t\t\t\"" + st.name + "\"");
                }
            }
            names.toArray();
            res = String.join(",\n", names);
            result = result.concat(res);
            result = result.concat("\n\t\t]");
        }
        result = result.concat("\n}");
        return result;
    }
}
