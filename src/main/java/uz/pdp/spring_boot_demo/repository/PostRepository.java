package uz.pdp.spring_boot_demo.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.spring_boot_demo.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(nativeQuery = true, value = "select * from posts where user_id > 6")
    List<Post> getPostsWhereUserIdBig6();


    Boolean existsByUserId(Integer userId);


    List<Post> findAllByUserId(Integer userId);

    List<Post> findAllByUserIdGreaterThan(Integer userId);
    List<Post> findAllByUserIdGreaterThanEqual(Integer userId);
    List<Post> findAllByUserIdBetween(Integer userId, Integer userId2);

    List<Post> findAllByUserIdLessThanAndTitleStartingWith(Integer userId, String title);
    List<Post> findAllByUserIdLessThanOrTitleStartingWith(Integer userId, String title);

    @Query(name = "Post.FindAll")
    List<Post> getAllPosts();

    @Query(name = "Post.FindAll.Native",nativeQuery = true)
    List<Post> getAllPostsByNativeQuery();

    @Query(name = "Post.ById")
    Post getPostById(Integer id);

    @Query(name = "Post.ById.Native",nativeQuery = true)
    Post getPostByIdNativeQuery(Integer id);

    @Modifying
    @Transactional
    @Query(value = "insert into posts (id, title, body, user_id) values (?1,?2,?3,?4) returning id;",nativeQuery = true)
    Integer createPost(Integer id, String title, String body, Integer userId);

    @Modifying
    @Transactional
    @Query(value = "delete from posts where id=?1;",nativeQuery = true)
    void deletePost(Integer id);

    @Query("from Post where userId in ?1 order by title")
    List<Post> findPostsByUserIds(List<Integer> ids);

    @Query("select p from Post p")
    List<Post> getPostsByIds(Sort sort);
}
