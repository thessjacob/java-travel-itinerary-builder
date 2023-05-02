package destination;

public abstract class AbstractSite implements Visitable {
    private final String name;
    private final double defaultTime;
    private double plannedTime;
    private String nearestCityName;
    private String description;
    private String imageURL;
    private final double additionalFreeTimeIncrement = 1.5;



    public AbstractSite() {
        this("", 0);
    }

    public AbstractSite(String name, double defaultTime) {
        this.name = name;
        this.description = name;
        this.defaultTime = defaultTime;
    }

    public String getName() {
        return name;
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

    public String getImageURL() {
        return imageURL;
    }

    public double getDefaultTime() {
        return defaultTime;
    }

    public void setNearestCityName(String nearestCityName) {
        this.nearestCityName = nearestCityName;
    }

    public String getNearestCityName() {
        return nearestCityName;
    }
}
