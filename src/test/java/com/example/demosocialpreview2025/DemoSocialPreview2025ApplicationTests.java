package com.example.demosocialpreview2025;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class DemoSocialPreview2025ApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void applicationStartsSuccessfully() {
        // Additional test to ensure the application can start
        // You can add more specific tests here as your application grows
    }

}
