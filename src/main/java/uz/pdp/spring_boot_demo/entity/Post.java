package uz.pdp.spring_boot_demo.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.spring_boot_demo.dto.PostDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "posts")
@SqlResultSetMapping(
        name = "POST_DTO_MAPPER",
        classes = @ConstructorResult(targetClass = PostDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "title", type = String.class)
                })
)
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
        ),
        @NamedNativeQuery(
                name = "Post.Native.ClassDTO.Projection",
                query = "select id, title from posts;",
                resultSetMapping = "POST_DTO_MAPPER"
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
// 3 < id 8 OR body start with "b"
