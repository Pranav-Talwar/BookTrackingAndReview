package com.jdpj.book.controllers;

import com.jdpj.book.models.Friend;
import com.jdpj.book.models.User;
import com.jdpj.book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId) {

        User user = userService.findById(userId);

        if (user == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {

        user.setId(0);

        return userService.save(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {

        return userService.save(user);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {

        User dbUser = userService.findById(userId);

        if (dbUser == null) {
            throw new RuntimeException("user id not found - " + userId);
        }

        userService.deleteById(userId);

        return "Deleted user id - " + userId;
    }

    @GetMapping("/friends/{userId}")
    public List<Friend> getUserFriends(@PathVariable int userId) {

        User user = userService.findById(userId);

        if (user == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        List<Friend> friends = userService.getUserFriends(userId);

        if (friends == null) {
            throw new RuntimeException("User friends returned null - " + userId);
        }

        return friends;
    }

    @PostMapping("/friends/{userId}")
    public Friend addFriend(@PathVariable int userId, @RequestBody Friend friend) {
        friend.setId(0);

        return userService.saveFriend(friend);
    }

}
