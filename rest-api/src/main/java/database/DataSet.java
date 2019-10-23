package database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class DataSet {

    @Autowired
    private UserRepo userRepo;

    private  HashMap<String,User> userMap = new HashMap<>();
    private ArrayList<User> userList = null;

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public HashMap<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(HashMap<String, User> userMap) {
        this.userMap = userMap;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public void init(){
        for(User user : userList = userRepo.findAll()){
            userMap.put(user.getId(),user);
        }
    }
    //每隔1h将缓存数据写入数据库
    @Scheduled(initialDelay = 1000 * 60 * 60,fixedDelay = 1000 * 60 * 60)
    public void cacheUpdate(){
        userList.clear();
        for(User user : userMap.values()){
            userList.add(user);
        }
        userRepo.saveAll(userList);
    }

}
