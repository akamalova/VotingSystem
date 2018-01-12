package ru.testAssignment.voting.web;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantControllerTest extends AbstractControllerTest{

    @Before
    public void setUp() {
        cacheManager.getCache("restaurants").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

}