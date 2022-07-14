package com.griddynamics.internship.userservice;

import com.griddynamics.internship.userservice.model.User;
import com.griddynamics.internship.userservice.model.UserDTO;
import com.griddynamics.internship.userservice.model.UserRepository;
import com.griddynamics.internship.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @Test
    public void getAllUsers() {
        Collection<UserDTO> expected = getExpected();

        userService = new UserService(userRepository);
        List<User> mockedUsers = new ArrayList<>(Arrays.asList(
                new User(6, "Dima", "Zhmur", "dzhmur@griddynamics.com", "abcde1"),
                new User(7, "Zhenya", "Komiahina", "ykomiahina@griddynamics.com", "fghij2")
        ));
        when(userRepository.findAll()).thenReturn(mockedUsers);

        Collection<UserDTO> actual = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertThat(actual, is(expected));
    }

    private Collection<UserDTO> getExpected() {
        return new ArrayList<>(Arrays.asList(
                new UserDTO(6, "Dima", "Zhmur", "dzhmur@griddynamics.com"),
                new UserDTO(7, "Zhenya", "Komiahina", "ykomiahina@griddynamics.com")
        ));
    }
}