package x.im.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IMServerApplication implements CommandLineRunner {


    @Autowired
    NettySever nettySever;

    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettySever.run();
    }
}
