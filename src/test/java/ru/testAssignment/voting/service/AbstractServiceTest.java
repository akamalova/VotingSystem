package ru.testAssignment.voting.service;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testAssignment.voting.Profiles;
import ru.testAssignment.voting.repository.JpaUtil;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({Profiles.REPOSITORY_IMPLEMENTATION, Profiles.ACTIVE_DB})

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class AbstractServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Autowired
    protected CacheManager cacheManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
}

