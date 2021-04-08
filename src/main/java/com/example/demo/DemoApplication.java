package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class DemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Bean
    ReactiveAuditorAware<String> auditorAware() {
        return () -> Mono.just("hantsy");
    }
    
}


@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializer implements CommandLineRunner {
    
    private final PostRepository posts;
    
    @Override
    public void run(String[] args) {
        log.info("start data initialization ...");
        this.posts
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> this.posts.save(Post.builder().title(title).content("content of " + title).build())
                                )
                )
                .thenMany(
                        this.posts.findAll()
                )
                .subscribe(
                        data -> log.info("saved post: {}", data),
                        error -> log.error("err:" + error),
                        () -> log.info("done initialization...")
                );
        
    }
    
}

@RestController()
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
class PostController {
    
    private final PostRepository posts;
    
    @GetMapping("")
    public Flux<Post> all(@RequestParam(name = "q", required = false) String keyword) {
        if (StringUtils.hasText(keyword)) {
            return this.posts.findByTitleContains(keyword);
        }
        return this.posts.findAll();
    }
    
    @PostMapping("")
    public Mono<Post> create(@RequestBody Post post) {
        return this.posts.save(post);
    }
    
    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable("id") String id) {
        return this.posts.findById(id);
    }
    
    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") String id, @RequestBody Post post) {
        return this.posts.findById(id)
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    
                    return p;
                })
                .flatMap(this.posts::save);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.posts.deleteById(id);
    }
    
}

interface PostRepository extends ReactiveMongoRepository<Post, String> {
    
    Flux<Post> findByTitleContains(String title);
}

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Post {
    
    @Id
    private String id;
    private String title;
    private String content;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @CreatedBy
    private String createdBy;
    
    @LastModifiedBy
    private String updatedBy;
    
    @Version
    Long version;
}


