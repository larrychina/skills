package org.larry.springboot;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Larry on 2017/3/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//相当于  --spring.profiles.active=dev
@ActiveProfiles(value="dev")
public class Springboot02ApplicationTest {
}
