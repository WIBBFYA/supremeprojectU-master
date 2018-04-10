package edu.duke.wibbfya.projectU;

/**
 * Created by XL on 4/8/18.
 */

class Projects {
    // Fields
    public String projectName;
    public String startDate;
    public String endDate;
    public String description;

    // Constructor
    public Projects(){

    }

    // Methods
    public void setName(String name) {
        projectName = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {return projectName;}

    public String getDescription() {return description;}

    public String getStartDate() {return startDate;}

    public String getEndDate() {return endDate;}
}
