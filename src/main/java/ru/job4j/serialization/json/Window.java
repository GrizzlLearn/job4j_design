package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.annotation.*;
import java.util.Arrays;


@XmlRootElement(name = "window")
@XmlAccessorType(XmlAccessType.FIELD)
public class Window {
    @XmlAttribute
    private float height;
    @XmlAttribute
    private float weight;
    @XmlAttribute
    private String manufacturer;
    @XmlAttribute
    private boolean isHasSpecialGlass;

    @XmlElementWrapper(name = "fittings")
    @XmlElement(name = "fitting")
    private String[] fittings;
    private Glass glass;

    public Window() {

    }

    public Window(float height, float weight, String manufacturer, boolean isHasSpecialGlass, String[] fittings, Glass glass) {
        this.height = height;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.isHasSpecialGlass = isHasSpecialGlass;
        this.fittings = fittings;
        this.glass = glass;
    }

    @Override
    public String toString() {
        return "Window{"
                + "height=" + height
                + ", weight=" + weight
                + ", manufacturer='" + manufacturer + '\''
                + ", isHasSpecialGlass=" + isHasSpecialGlass
                + ", fittings= " + Arrays.toString(fittings)
                + ", glass=" + glass
                + '}';
    }

    public static void main(String[] args) {
        final Gson gson = new GsonBuilder().create();
        Window window = new Window(
                2.0f,
                2.5f,
                "REHAU",
                true,
                new String[]{"handle", "hinge", "fasteners"},
                new Glass(1.4f)
        );

        System.out.println(gson.toJson(window));
        final String windowJson =
                "{"
                        + "\"height\":2.0,"
                        + "\"weight\":2.5,"
                        + "\"manufacturer\":\"REHAU\","
                        + "\"isHasSpecialGlass\":\"true\","
                        + "\"fittings\":"
                        + "[\"handle\",\"hinge\",\"fasteners\"],"
                        + "\"glass\":"
                        + "{"
                        + "\"refractiveIndex\":1.4"
                        + "}"
                        + "}";
        System.out.println(System.lineSeparator());
        System.out.println((gson.fromJson(windowJson, Window.class)));
    }
}
