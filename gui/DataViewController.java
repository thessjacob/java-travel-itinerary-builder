package gui;

import database.DatabaseController;
import destination.Country;
import destination.SuperRegion;
import plan.ItineraryController;

import javax.swing.*;
import java.util.ArrayList;

public enum DataViewController {

    INSTANCE;

    private Country country;
    private ArrayList<SuperRegion> superRegions;
    private int numOfSuperRegions;
    private boolean planning;
    private ItineraryPanel ip;
    private ItineraryController ic = ItineraryController.INSTANCE;

    public void initController(String countryName) {
        country = DatabaseController.getCountry(countryName);
        superRegions = country.getSuperRegions();
        numOfSuperRegions = superRegions.size();
    }

    public void clearController() {
        country = null;
        superRegions.clear();
        numOfSuperRegions = 0;
    }

    public Country getCountry() {
        return country;
    }

    public ArrayList<Country> getCountries() {
        return DatabaseController.getAllCountries();
    }

    public String getSiteName(String siteName) {
        return country.getSite(siteName).getName();
    }
    public String getSiteDescription(String siteName) {
        return country.getSite(siteName).getDescription();
    }

    public String getSiteImageURL(String siteName) {
        return country.getSite(siteName).getImageURL();
    }

    public String getSiteNearestCityName(String siteName) {
        return country.getSite(siteName).getNearestCityName();
    }

    public double getSiteDefaultTime(String siteName) {
        return country.getSite(siteName).getDefaultTime();
    }

    public String getCityName(String cityName) {
        return country.getCity(cityName).getName();
    }

    public String getCityDescription(String cityName) {
        return country.getCity(cityName).getDescription();
    }

    public String getCityImageURL(String cityName) {
        return country.getCity(cityName).getImageURL();
    }

    public int getNumOfSuperRegions() {
        return numOfSuperRegions;
    }

    public void setPlanning(boolean bool) {
        planning = bool;
    }

    public boolean isPlanning() {
        return planning;
    }

    public void setItineraryPanel(ItineraryPanel ip) {
        this.ip = ip;
    }

    public void addTimeOnPanel() {
        ip.addTime();
    }

    public void resetItineraryPanel() {
        ip.resetTime();
        ip.itemList = ic.getList();
        ic.list.revalidate();
    }

    public void throwTimeWarning() {
        String message = "You need to leave time for sleep! Please add overnight rest to continue planning.";
        JOptionPane.showMessageDialog(ip, message);
    }
}
