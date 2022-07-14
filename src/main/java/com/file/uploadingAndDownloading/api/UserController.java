package com.file.uploadingAndDownloading.api;

import com.file.uploadingAndDownloading.dto.DetailedUser;
import com.file.uploadingAndDownloading.dto.User;
import com.file.uploadingAndDownloading.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody DetailedUser detailedUser) {
        return userService.createUser(detailedUser);
    }

    @GetMapping("/get/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUserDto(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/update/{id}")
    public User editUser(@PathVariable Long id, @RequestBody DetailedUser updateDetailedUser) {
        return userService.updateUser(id, updateDetailedUser);
    }
}
