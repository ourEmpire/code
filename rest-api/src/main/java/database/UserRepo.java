package database;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
@Transactional
@RepositoryRestResource
@CacheConfig(cacheNames = {"users"})
public interface UserRepo extends CrudRepository<User, Long>{
    @Cacheable
    ArrayList<User> findAll();

}
