package com.ssf.day13workshop.repo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ssf.day13workshop.model.User;

@Repository
public class ContactsRepo {
    @Autowired
    @Qualifier("ContactsRedis")
    private RedisTemplate<String, String> template;

    private static final String USER_HASH_KEY = "user id";

    public void saveUser(User user) {
        String key = USER_HASH_KEY + ":" + user.getId();
        template.opsForHash().put(key, "id", user.getId());
        template.opsForHash().put(key, "name", user.getName());
        template.opsForHash().put(key, "email", user.getEmail());
        template.opsForHash().put(key, "phoneno", user.getPhoneno());
        template.opsForHash().put(key, "dob", user.getDob().toString());
    }

    public User getUser(String id) {
        String key = USER_HASH_KEY + ":" + id;
        Map<Object, Object> userData = template.opsForHash().entries(key);
        User user = new User();
        user.setId(userData.get("id").toString());
        user.setName(userData.get("name").toString());
        user.setEmail(userData.get("email").toString());
        user.setPhoneno(userData.get("phoneno").toString());
        user.setDob(LocalDate.parse(userData.get("dob").toString()));

        return user;

    }

    public List<User> getAllUsers() {
        Set<String> userKeys = template.keys(USER_HASH_KEY + ":*");
        List<User> users = new ArrayList<>();

        for (String key : userKeys) {
            Map<Object, Object> userData = template.opsForHash().entries(key);
            User user = new User();
            user.setId(userData.get("id").toString());
            user.setName(userData.get("name").toString());
            user.setEmail(userData.get("email").toString());
            user.setPhoneno(userData.get("phoneno").toString());
            user.setDob(LocalDate.parse(userData.get("dob").toString()));
            users.add(user);
        }

        return users;
    }
}
