package uz.pdp.spring_boot_demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.spring_boot_demo.dto.PostCreator;
import uz.pdp.spring_boot_demo.dto.PostUpdator;
import uz.pdp.spring_boot_demo.entity.Post;
import uz.pdp.spring_boot_demo.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        //postRepository.deleteById(id);
        postRepository.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody PostUpdator updator){
        boolean exists = postRepository.existsById(id);
        if (exists){
            Optional<Post> optional = postRepository.findById(id);
            Post oldPost = optional.get();
            oldPost.setTitle(updator.title());
            oldPost.setBody(updator.body());
            oldPost.setUserId(updator.userId());
//            postRepository.save(oldPost);
            return ResponseEntity.ok("Updated✅✅✅✅✅✅");
        } else {
            return ResponseEntity.status(400).body("Invalid Data");
        }

    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<Void> create(@RequestBody PostCreator creator){
        postRepository.save(
                Post.builder()
                        .id(creator.id())
                        .title(creator.title())
                        .body(creator.body())
                        .userId(creator.userId())
                        .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/createV2")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createV2(@RequestBody PostCreator creator){
        postRepository.createPost(creator.id(), creator.title(), creator.body(), creator.userId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Post>> findAll(){
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/findAllPageV1")
    public ResponseEntity<List<Post>> findAllPageV1(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
    ){
        List<Post> posts = postRepository.findAll()
                .stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/findAllPageV2")
    public ResponseEntity<Page<Post>> findAllPageV2(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/findAllV2")
    public ResponseEntity<List<Post>> findAllV2(){
        List<Post> posts = postRepository.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/findAllV3")
    public ResponseEntity<List<Post>> findAllV3(){
        List<Post> posts = postRepository.getAllPostsByNativeQuery();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Post> findById(@PathVariable Integer id){
        Optional<Post> optional = postRepository.findById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @GetMapping("/findByIdV2/{id}")
    public ResponseEntity<Post> findByIdV2(@PathVariable Integer id){
        Post post = postRepository.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/findByIdV3/{id}")
    public ResponseEntity<Post> findByIdV3(@PathVariable Integer id){
        Post post = postRepository.getPostByIdNativeQuery(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/findPostsByUserIds")
    public ResponseEntity<List<Post>> findPostsByUserIds(@RequestParam(name = "ids") List<Integer> ids){
        List<Post> posts = postRepository.findPostsByUserIds(ids);
        return ResponseEntity.ok(posts);
    }

}
