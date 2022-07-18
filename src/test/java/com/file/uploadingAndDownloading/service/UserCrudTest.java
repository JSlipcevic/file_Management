package com.file.uploadingAndDownloading.service;

import com.file.uploadingAndDownloading.TestContext;
import com.file.uploadingAndDownloading.dto.DetailedUser;
import com.file.uploadingAndDownloading.dto.User;
import com.file.uploadingAndDownloading.entity.UserEntity;
import com.file.uploadingAndDownloading.model.Gender;
import com.file.uploadingAndDownloading.model.UserPackage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.file.uploadingAndDownloading.util.FileUtil.numOfFilesInDirectory;
import static org.junit.jupiter.api.Assertions.*;


public class UserCrudTest extends TestContext {

    @Autowired
    private UserService userService;

    @Test
    void shouldGetUser() {
        //given
        DetailedUser newUser = new DetailedUser("Ante", "Marković", "Markan1234", "7267kush@",
                "markovic@gmail.com", "Croatia", "Split", Gender.MALE, UserPackage.BASIC);
        UserEntity user = new UserEntity(newUser);
        userRepository.save(user);

        //when
        UserEntity getUser = userService.getUser(user.getId());

        //then
        assertNotNull(getUser);
    }


    @Test
    void shouldCreateUser() {
        //given
        DetailedUser newUser = new DetailedUser("Ivana", "Šarić", "ivana9678", "3jui89l1",
                "ive@gmail.com", "Croatia", "Makarska", Gender.FEMALE, UserPackage.BASIC);

        //when
        User user = userService.createUser(newUser);

        //then
        assertEquals(newUser.getUserName(), user.getUserName());
        assertEquals(newUser.getCountry(), user.getCountry());
        assertEquals(newUser.getCity(), user.getCity());
        assertEquals("F", user.getGender());
        assertEquals("Basic", user.getUserPackage());
    }

    @Test
    void updateUser()  {
        //given
        DetailedUser newUser = new DetailedUser("Ante", "Marković", "Markan1234", "7267kush@",
                "markovic@gmail.com", "Croatia", "Split", Gender.MALE, UserPackage.BASIC);
        UserEntity user = new UserEntity(newUser);
        userRepository.save(user);

        //Field to change
        DetailedUser userChangeField = new DetailedUser();
        userChangeField.setCity("Mostar");
        userChangeField.setEmail("Antiša@yahoo.com");
        userChangeField.setCountry("");

        // when
        User userUpdate = userService.updateUser(user.getId(), userChangeField);
        UserEntity userFromDataBase = userService.getUser(user.getId());

        //then
        assertEquals(userChangeField.getCity(), userFromDataBase.getCity());
        assertEquals(userChangeField.getEmail(), userFromDataBase.getEmail());
        assertEquals(userChangeField.getCountry(), userFromDataBase.getCountry());
        assertEquals(user.getFirstName(), userFromDataBase.getFirstName());
        assertEquals(user.getLastName(), userFromDataBase.getLastName());
    }

    @Test
    void typeOfUserPackage()  {
        //given
        DetailedUser newUser1 = new DetailedUser("Ante", "Marković", "Markan1234", "7267kush@",
                "markovic@gmail.com", "Croatia", "Split", Gender.MALE, UserPackage.BASIC);
        UserEntity user1 = new UserEntity(newUser1);

        DetailedUser newUser2 = new DetailedUser("Marko", "Perić", "PerićMark", "kjkzuop9567",
                "perić@yahoo.com", "Croatia", "Solin", Gender.MALE,  UserPackage.MEDIUM);
        UserEntity user2 = new UserEntity(newUser2);

        DetailedUser newUser3 = new DetailedUser("Ivana", "Vučić", "Vuk95", "meran23uz",
                "ivanavucic@yahoo.com", "Croatia", "Zadar", Gender.FEMALE, UserPackage.ADVANCED);
        UserEntity user3 = new UserEntity(newUser3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //then
        assertEquals("Basic", user1.getUserPackage());
        assertEquals("Medium", user2.getUserPackage());
        assertEquals("Advanced", user3.getUserPackage());
    }

    @Test
    void userGender()  {
        //given
        DetailedUser newUser1 = new DetailedUser("Ante", "Marković", "Markan1234", "7267kush@",
                "markovic@gmail.com", "Croatia", "Split", Gender.MALE, UserPackage.BASIC);
        UserEntity user1 = new UserEntity(newUser1);

        DetailedUser newUser2 = new DetailedUser("Ivana", "Vučić", "Vuk95", "meran23uz",
                "ivanavucic@yahoo.com", "Croatia", "Zadar", Gender.FEMALE, UserPackage.ADVANCED);
        UserEntity user2 = new UserEntity(newUser2);

        userRepository.save(user1);
        userRepository.save(user2);

        //then
        assertEquals("M", user1.getGender());
        assertEquals("F", user2.getGender());
    }

    @Test
    void numOfAllowedUploadedFilesByUser()  {
        //given
        DetailedUser newUser1 = new DetailedUser("Ante", "Marković", "Markan1234", "7267kush@",
                "markovic@gmail.com", "Croatia", "Split", Gender.MALE, UserPackage.BASIC);
        UserEntity user1 = new UserEntity(newUser1);

        DetailedUser newUser2 = new DetailedUser("Marko", "Perić", "PerićMark", "kjkzuop9567",
                "perić@yahoo.com", "Croatia", "Solin", Gender.MALE, UserPackage.MEDIUM);
        UserEntity user2 = new UserEntity(newUser2);

        DetailedUser newUser3 = new DetailedUser("Ivana", "Vučić", "Vuk95", "meran23uz",
                "ivanavucic@yahoo.com", "Croatia", "Zadar", Gender.FEMALE, UserPackage.ADVANCED);
        UserEntity user3 = new UserEntity(newUser3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //when
        int numOfFilesFirstUser = numOfFilesInDirectory(user1);
        int numOfFilesSecondUser = numOfFilesInDirectory(user2);
        int numOfFilesThirdUser = numOfFilesInDirectory(user3);

        //then
        assertEquals(10, numOfFilesFirstUser);
        assertEquals(25, numOfFilesSecondUser);
        assertEquals(50, numOfFilesThirdUser);

    }
}
