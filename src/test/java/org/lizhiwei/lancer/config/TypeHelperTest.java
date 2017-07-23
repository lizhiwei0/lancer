package org.lizhiwei.lancer.config;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.lizhiwei.lancer.model.Command;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class TypeHelperTest {
    private Logger logger = Logger.getLogger(getClass().getName());
    @Test
    public void testLoad() {
        TypeHelper.getInstance().loadTypes(new ClassPathResourceLoader(TypeHelperTest.class));

        Assert.assertEquals(1,  TypeHelper.getIdByName(Command.class.getName()));

        Assert.assertEquals(Command.class.getName(),  TypeHelper.getNameById(1));

    }
}
