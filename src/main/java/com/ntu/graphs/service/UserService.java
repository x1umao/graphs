package com.ntu.graphs.service;

import com.ntu.graphs.entity.User;
import com.ntu.graphs.util.HmacUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserService {
    private final Map<String, String[]> database;
    private final String key;

    public UserService() {
        database = new HashMap<>();
        key = "WKWSCI";
        long seed = System.currentTimeMillis();//以系统时间为种子
        Random random = new Random(seed);

        List<String> passwords = Arrays.asList("LCK2021WKWSCI", "123456", "123456", "123456", "123456");
        List<String> usernames = Arrays.asList("LCK2021", "admin2", "admin3", "admin4", "admin5");

        for (int i = 0; i < 5; i++) {
            String username = usernames.get(i);
            String randKey = String.valueOf(random.nextInt(10000) + 1);
            String frontPassword = HmacUtil.encode(passwords.get(i), username + randKey);
            String backPassword = HmacUtil.encode(frontPassword, key + randKey);
            database.put(username, new String[]{randKey, backPassword});
        }
    }

    public int verifyUser(User user, HttpServletRequest request) {
        String password = user.getPassword();
        String username = user.getUsername();
        //check empty string
        if (username.length() == 0 || password.length() == 0) {
            return 0;
        }
        //对password hmac
        password = HmacUtil.encode(password, key + getKey(username));
        String pwd = database.get(username)[1];
        if (password.equals(pwd)) {
            //keep session
            request.getSession().setAttribute("user", user);
            //set session id in the cookie
            return 1;// successful login
        }
        return 0;
    }

    public int logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return 1;//successful logout
    }

    public String getKey(String username) {
        return database.get(username)[0];
    }
}
