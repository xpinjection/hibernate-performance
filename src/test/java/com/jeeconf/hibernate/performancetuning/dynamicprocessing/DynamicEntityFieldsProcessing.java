package com.jeeconf.hibernate.performancetuning.dynamicprocessing;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.dynamicprocessing.entity.Client;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

/**
 * Created by Igor Dmitriev on 5/18/16
 */
@DatabaseSetup("/dynamicprocessing.xml")
public class DynamicEntityFieldsProcessing extends BaseTest {
    @Test
    @Commit
    public void dynamicUpdate() {
        // add @DynamicUpdate(true) to Client
        Client client = getSession().get(Client.class, 1);
        client.setAge(35);
    }
}
