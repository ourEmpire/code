package database;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
@Entity
public class Job {
    @Id
    private String id;
    private String jobName;
    private String companyName;//公司名
    private String workCity;//工作地点
    private String[] tags;
    private String jobMsg;
    private String companyMsg;
    private HashMap<String,String> companyInfo;

    public Job() {
    }

    public Job(String id,String jobName, String companyName, String workCity, String[] tags, String jobMsg, String companyMsg, HashMap<String, String> companyInfo) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.workCity = workCity;
        this.tags = tags;
        this.jobMsg = jobMsg;
        this.companyMsg = companyMsg;
        this.companyInfo = companyInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getJobMsg() {
        return jobMsg;
    }

    public void setJobMsg(String jobMsg) {
        this.jobMsg = jobMsg;
    }

    public String getCompanyMsg() {
        return companyMsg;
    }

    public void setCompanyMsg(String companyMsg) {
        this.companyMsg = companyMsg;
    }

    public HashMap<String, String> getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(HashMap<String, String> companyInfo) {
        this.companyInfo = companyInfo;
    }
}
