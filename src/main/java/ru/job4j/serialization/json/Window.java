package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
    private List<String> fittings;
    private Glass glass;

    public Window() {

    }

    public Window(float height, float weight, String manufacturer, boolean isHasSpecialGlass, List<String> fittings, Glass glass) {
        this.height = height;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.isHasSpecialGlass = isHasSpecialGlass;
        this.fittings = fittings;
        this.glass = glass;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public boolean isHasSpecialGlass() {
        return isHasSpecialGlass;
    }

    public Glass getGlass() {
        return glass;
    }

    public List<String> getFittings() {
        return fittings;
    }

    @Override
    public String toString() {
        return "Window{"
                + "height=" + height
                + ", weight=" + weight
                + ", manufacturer='" + manufacturer + '\''
                + ", isHasSpecialGlass=" + isHasSpecialGlass
                + ", fittings= " + fittings.toString()
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
                new ArrayList<>(Arrays.asList("handle", "hinge", "fasteners")),
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
