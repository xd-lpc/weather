package com.xd.lpc_.weather.model;

/**
 * Created by LPC- on 2015/8/10.
 */
public class CityInfo {
    private int id;
    private String nationName;
    private String provinceName;
    private String cityName;
    private String countryName;
    private String countryPingyin;
    private String cityId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryPingyin() {
        return countryPingyin;
    }

    public void setCountryPingyin(String countryPingyin) {
        this.countryPingyin = countryPingyin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getProviceName() {
        return provinceName;
    }

    public void setProviceName(String proviceName) {
        this.provinceName = proviceName;
    }
}
