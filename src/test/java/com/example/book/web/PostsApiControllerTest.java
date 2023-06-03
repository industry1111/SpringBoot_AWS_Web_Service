package com.example.book.web;

import com.example.book.domain.posts.Posts;
import com.example.book.domain.posts.PostsRepository;
import com.example.book.web.dto.PostsSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

//    @AfterAll
//    public void tearDown() throws Exception {
//        postsRepository.deleteAll();
//    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> posts = postsRepository.findAll();
        Assertions.assertThat(posts.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

}
