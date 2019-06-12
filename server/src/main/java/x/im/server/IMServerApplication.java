package x.im.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("!test")
public class IMServerApplication implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettySever.run();
    }

    @Autowired
    NettySever nettySever;


    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
    }


    public IMServerApplication() {

    }

}
