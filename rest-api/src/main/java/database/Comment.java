package database;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="commentId",nullable=false)
    private String id;
    @Column(name="username",nullable = false)
    private String username;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name="commentAt",nullable=false)
    private Timestamp commentAt;
    @Column(name = "content",nullable = false,length = -1)
    private String content;
    @Column(name = "articleId",nullable = false)
    private Long articleId;

    public Comment(String username, String email,
                   Timestamp commentAt, String content,
                   Long articleId) {
        this.username = username;
        this.email = email;
        this.commentAt = commentAt;
        this.content = content;
        this.articleId = articleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCommentAt() {
        return commentAt;
    }

    public void setCommentAt(Timestamp commentAt) {
        this.commentAt = commentAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
