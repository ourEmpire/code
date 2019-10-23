package tool;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import database.Job;
import database.JobAndCompany;
import database.JobAndCompanyRepo;
import org.jsoup.nodes.*;
import org.jsoup.*;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/users/test")
public class HtmlReader {
    private static final int jLength = 169;
    @Autowired
    private JobAndCompanyRepo jcRepo;


    public void saveJobAndCompany() throws IOException{
        ArrayList<String> returnStr = new ArrayList<>();
        for(int j = 1;j < jLength;j++) {
            JobAndCompany[] jobAndCompanies = getJobAndCompanies(j);
//            System.out.println(JSON.toJSONString(jobAndCompanies));
//            returnStr.add(JSON.toJSONString(jobAndCompanies));
            jcRepo.saveAll(Arrays.asList(jobAndCompanies));
        }

        for(JobAndCompany jc : jcRepo.findAll()){
            System.out.println("category: "+jc.getCategory());
            System.out.println("CompanyName:" + jc.getCompanyName());
        }
    }

    public ArrayList<String> getJobJSON() throws IOException {
        ArrayList<String> returnStr = new ArrayList<>();
        for(int j = 1;j < jLength;j++) {
            JobAndCompany[] jobAndCompanies = getJobAndCompanies(j);
            System.out.println(JSON.toJSONString(getJobs(jobAndCompanies)));
            returnStr.add(JSON.toJSONString(getJobs(jobAndCompanies)));
        }
        return returnStr;
    }

    private JobAndCompany[] getJobAndCompanies(int j)throws IOException{
        JobAndCompany[] jobAndCompanies = null;
        String url = "https://search.51job.com/list/000000,000000,0000,32,9,99,%25E5%2589%258D%25E7%25AB%25AF%25E5%25BC%2580%25E5%258F%2591,2,"+j+".html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
        Document doc = Jsoup.connect(url).get();

        Elements jobNames = doc.select(".el p>span>a");
        Elements companyNames = doc.select(".el .t2 a");
        Elements workCities = doc.select(".dw_wp .dw_table div.el span.t3");
        workCities.remove(0);
        Elements salaries = doc.select("div.el span.t4");
        salaries.remove(0);
        Elements datetimes = doc.select("div.el span.t5");
        datetimes.remove(0);
        jobAndCompanies= new JobAndCompany[jobNames.size()];
        for (int i = 0; i < jobNames.size(); i++) {
            jobAndCompanies[i] = new JobAndCompany();
        }

        int i = 0;
        for (Element jobNameEl : jobNames) {
            jobAndCompanies[i].setCategory("前端开发");
            jobAndCompanies[i].setJobName(jobNameEl.text());
            String href = jobNameEl.attr("href");
            String id = href.substring(href.lastIndexOf("/")+1,href.lastIndexOf(".html"));
            System.out.println("id: " + id);
            jobAndCompanies[i].setJobNameHref(jobNameEl.attr("href"));
            jobAndCompanies[i++].setId(id);
            System.out.println("href:" + jobAndCompanies[i - 1].getJobNameHref());
        }
        i = 0;
        for (Element company : companyNames) {
            jobAndCompanies[i].setCompanyName(company.text());
            jobAndCompanies[i++].setCompanyNameHref(company.attr("href"));
        }
        i = 0;
        for (Element cities : workCities) {
            jobAndCompanies[i++].setWorkCity(cities.text());
            String temp = new String(cities.text().getBytes(),"UTF-8");
            System.out.println("workCities:"+temp);
        }
        i = 0;
        for (Element salaryEl : salaries) {
            jobAndCompanies[i++].setSalary(salaryEl.text());
        }
        i = 0;
        for (Element time : datetimes) {
            jobAndCompanies[i++].setDatetime(time.text());
        }
        return jobAndCompanies;
    }


    private ArrayList<Job> getJobs(JobAndCompany[] jobAndCompanies)  {
        ArrayList<Job> jobs = new ArrayList<>();
        for(int i = 0;i < jobAndCompanies.length;i++) {
            JobAndCompany jobAndCompany = jobAndCompanies[i];
            String href = jobAndCompany.getJobNameHref();
            String id = href.substring(href.lastIndexOf("/"),href.lastIndexOf(".html"));
            String jobName = jobAndCompany.getJobName();
            String companyName = jobAndCompany.getCompanyName();
            String workCity = jobAndCompany.getWorkCity();
            try {
                Document doc = Jsoup.connect(href).get();
                Element tagsEl = doc.selectFirst(".in .cn p.msg");
                String[] tags = null;
                if (tagsEl != null) {
                    System.out.println("tagsEl:" + tagsEl.text());
                    tags = tagsEl.text().split("|");
                }
                Element jobMsgEl = doc.selectFirst(".tCompany_main .tBorderTop_box .job_msg");
                String jobMsg = null;
                if (jobMsgEl != null) {
                    System.out.println("jobMsgEl:" + jobMsgEl.text());
                    jobMsg = jobMsgEl.text();
                }
                Element companyMsgEl = doc.selectFirst(".tCompany_center .tCompany_main .tBorderTop_box .tmsg");
                String companyMsg = null;
                if (companyMsgEl != null) {
                    companyMsg = companyMsgEl.text();
                    System.out.println("companyMsg:" + companyMsg);
                }
                HashMap<String, String> companyInfo = new HashMap<>();
                Element companyLogoEl = doc.selectFirst(".tBorderTop_box .com_msg .himg img");
                if (companyLogoEl != null) {//有些企业没有logo
                    String companyLogo = companyLogoEl.attr("src");
                    companyInfo.put("companyLogo", companyLogo);
                    System.out.println("companyLogo src:" + companyLogo);
                }

                Element companyFullNameEl = doc.selectFirst(".tBorderTop_box .com_msg a>p");
                Elements companyTagsEl = doc.select(".com_tag .at");

                if (companyFullNameEl != null || companyLogoEl != null) {
                    String companyFullName = companyFullNameEl.text();
                    companyInfo.put("companyFullName", companyFullName);
                    System.out.println("companyFullName:" + companyFullName);

                    String companyFlag = companyTagsEl.get(0).text();
                    companyInfo.put("companyFlag", companyFlag);
                    System.out.println("companyFlag:" + companyFlag);

                    String companyPeople = companyTagsEl.get(1).text();
                    companyInfo.put("companyPeople", companyPeople);

                    String companyTrade = companyTagsEl.get(2).text();
                    companyInfo.put("companyTrade", companyTrade);
                }
                jobs.add(new Job(id,jobName, companyName, workCity, tags, jobMsg, companyMsg, companyInfo));

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return jobs;
    }


}
