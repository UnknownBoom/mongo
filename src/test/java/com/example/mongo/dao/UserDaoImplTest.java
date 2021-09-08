package com.example.mongo.dao;

import com.example.mongo.domain.model.Comment;
import com.example.mongo.domain.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
class UserDaoImplTest {
    @Autowired
    private UserDaoImpl userDao;

    private List<User> users = new ArrayList<>() {{
        add(User.builder()
                .username("test1")
                .age(60)
                .group("testGroup")
                .largeText("Large text 1 to search bla bla bla bla ")
                .secondLargeText("Largest text 2 to search bla bla bla bla ")
                .comment(Comment.builder()
                        .message("Test message 1 in messages").build())
                .build());
        add(User.builder()
                .username("test2")
                .age(20)
                .group("testGroup")
                .largeText("Large text 1 to search bla bla bla bla ")
                .secondLargeText("Largest text 2.2 to search bla bla bla bla ")
                .comment(Comment.builder()
                        .message("Test message 2 in messages").build())
                .build());
        add(User.builder()
                .username("test3")
                .age(20)
                .group("testGroup2")
                .largeText("Large text 3 to search bla bla bla bla ")
                .secondLargeText("Largest text 3 to search bla bla bla bla ")
                .comment(Comment.builder()
                        .message("Test message 3.1 in messages").build())
                .build());
    }};

    @BeforeEach
    private void init() {
        userDao.removeAll();
        List<User> newUsers = users.stream().map(User::new).collect(Collectors.toList());
        userDao.insertAll(newUsers);
    }

    @Test
    void findByUsernameShouldNull() {
        User no_name = userDao.findByUsername("No name");
        assertNull(no_name);
    }

    @Test
    void findByUsernameShouldEquals() {
        String username = "test1";
        User user = userDao.findByUsername(username);
        List<User> collect = users.stream().filter(t -> username.equals(t.getUsername())).collect(Collectors.toList());

        Assertions.assertThat(collect.get(0))
                .isEqualToIgnoringGivenFields(user, "id");
    }

    @Test
    void findAll() {
        Collection<User> all = userDao.findAll();
        Assertions.assertThat(users)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(all);
    }

    @Test
    void save() {
        User userToSave = User.builder()
                .username("test123")
                .build();
        User save = userDao.save(new User(userToSave));
        Assertions.assertThat(save).isEqualToIgnoringGivenFields(save, "id");
    }

    @Test
    void deleteByUsername() {
        String username = "test2";
        userDao.deleteByUsername(username);
        Collection<User> filtered = users.stream().filter(t -> !username.equals(t.getUsername())).collect(Collectors.toList());
        Collection<User> all = userDao.findAll();
        Assertions.assertThat(filtered)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(all);
    }
}