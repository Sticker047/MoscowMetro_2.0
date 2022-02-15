public class Line {
    private String number;
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "\n\t\t{" +
                "\n\t\t\t\"number\" : \"" + number + "\"," +
                "\n\t\t\t\"name\" : \"" + name + '\"' +
                "\n\t\t}";
    }
}
