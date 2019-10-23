package init;

import config.MailConfig;
import database.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tool.HtmlReader;

@Order(1)
@Component
public class Start implements CommandLineRunner {
    @Autowired
    DataSet ds;
    @Autowired
    HtmlReader hr;

    @Override
    public void run(String... args) throws Exception {
        ds.init();
        hr.saveJobAndCompany();
    }
}
