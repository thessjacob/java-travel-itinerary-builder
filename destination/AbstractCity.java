/**
 * AbstractCity represents a settlement of some kind where visitors can spend free time and "rest", meaning spend the
 * night or otherwise spend time not doing active tourism.
 */
package destination;

import java.net.URL;
import java.util.Objects;

public abstract class AbstractCity implements CanStay {
    private final String name;
    private final String title;
    private final int size;
    private String imageURL;
    private String description;


    public AbstractCity () {
        this("", 0);
    }

    public AbstractCity(String name) {
        this(name, 0);
    }

    public AbstractCity(String name, int size) {
        this.name = name;
        this.title = name + " Free Time";
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public URL getImageURL() {
        return AbstractCity.class.getClassLoader().getResource(imageURL);
    }

    /**
     * Intellij generated equals method for comparison of AbstractCity.
     * @param o Object to compare current object against.
     * @return true if objects are equal based on name and description variables.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCity that)) return false;
        return size == that.size && Objects.equals(name, that.name);
    }

    /**
     * Intellij generated Hashcode for comparison of AbstractCity objects.
     * @return int hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }
}
