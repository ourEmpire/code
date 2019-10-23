package database;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobAndCompany {
    @Id
    private String id;
    private String category;
    private String jobName;
    private String jobNameHref;
    private String companyName;
    private String companyNameHref;
    private String workCity;
    private String salary;
    private String datetime;

    public JobAndCompany() {}

    public JobAndCompany(String id,String category,String jobName, String jobNameHref, String companyName, String companyNameHref, String workCity, String salary, String datetime) {
        this.category = category;
        this.id = id;
        this.jobName = jobName;
        this.jobNameHref = jobNameHref;
        this.companyName = companyName;
        this.companyNameHref = companyNameHref;
        this.workCity = workCity;
        this.salary = salary;
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getJobNameHref() {
        return jobNameHref;
    }

    public void setJobNameHref(String jobNameHref) {
        this.jobNameHref = jobNameHref;
    }

    public String getCompanyNameHref() {
        return companyNameHref;
    }

    public void setCompanyNameHref(String companyNameHref) {
        this.companyNameHref = companyNameHref;
    }
}
