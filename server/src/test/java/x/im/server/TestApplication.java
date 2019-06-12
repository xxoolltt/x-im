package x.im.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("test application run");
    }


    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
