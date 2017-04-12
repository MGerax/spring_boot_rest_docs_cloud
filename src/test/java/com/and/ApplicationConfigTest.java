package com.and;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationConfigTest {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    public void testApplicationConfigName() {
        Assert.assertEquals("city-service", applicationConfig.getName());
    }

    @Test
    public void testApplicationVersion() {
        Assert.assertEquals(134, applicationConfig.getVersion());
    }

    @Test
    public void testApplicationImplementations() {
        Assert.assertEquals(4, applicationConfig.getImplementations().size());
        Assert.assertArrayEquals(new String[]{"controller", "repository", "domain", "configuration"},
                applicationConfig.getImplementations().toArray());
    }
}
