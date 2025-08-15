package uz.pdp.spring_boot_demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "posts")
@NamedQueries({
        @NamedQuery(name = "Post.FindAll", query = "from Post"),
        @NamedQuery(name = "Post.ById", query = "select p from Post p where p.id=?1")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Post.FindAll.Native",
                query = "select * from posts;",
                resultClass = Post.class
        ),
        @NamedNativeQuery(
                name = "Post.ById.Native",
                query = "select * from posts where id=?1;",
                resultClass = Post.class
        )
})

public class Post {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String body;
}
