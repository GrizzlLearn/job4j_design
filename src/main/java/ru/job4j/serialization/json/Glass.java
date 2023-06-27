package ru.job4j.serialization.json;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "glass")
public class Glass {
    @XmlAttribute
    private float refractiveIndex;

    public Glass() {

    }

    public Glass(float refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    @Override
    public String toString() {
        return "Glass{"
                + "refractiveIndex=" + refractiveIndex
                + '}';
    }
}
