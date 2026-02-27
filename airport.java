public class airport {

    private String name;
    private String code;
    private String city;
    private String state;
    private String country;

    public airport(String name, String code, String city, String state, String country) {
        this.name = name;
        this.code = code;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    @Override
public String toString() {
    return code + " - " + name + " (" + city + ", " + state + ", " + country + ")";
}

}  
