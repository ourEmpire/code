package database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.security.Timestamp;
import java.util.ArrayList;

@RepositoryRestResource(collectionResourceRel = "comments",path = "comments")
public interface CommentRepo extends CrudRepository<Comment,Integer> {
    Iterable<Comment> findCommentsByArticleId(String id);
    Iterable<Comment> findCommentsByArticleIdAndCommentAtBefore(String id, Timestamp timestamp);
    ArrayList<Comment> findAllByOrderByCommentAtDesc();
}
