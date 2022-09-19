package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }
//not working
    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }
//not working
    @Test
    public void findIdByUsername_returns_correct_Id()   {
        String username = "bob";
        int id = 1001;
        int returnedId = sut.findIdByUsername(username);
        Assert.assertEquals(id, returnedId);
    }

    @Test
    public void findall_returns_all_users() {
        List<User> returnedList = sut.findAll();
        Assert.assertEquals(2,returnedList.size());

    }

    @Test
    public void hasAccountRunsCorrectAccount() {
        Account account = sut.hasAccount(1001,2001);
        Assert.assertEquals(2001, account.getId());
    }
}
