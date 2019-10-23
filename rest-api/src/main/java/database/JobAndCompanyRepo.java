package database;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface JobAndCompanyRepo extends MongoRepository<JobAndCompany,String> {
    ArrayList<JobAndCompany> findAll();
}
