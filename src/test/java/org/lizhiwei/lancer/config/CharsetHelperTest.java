package org.lizhiwei.lancer.config;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class CharsetHelperTest {
    private Logger logger = Logger.getLogger(getClass().getName());
    @Test
    public void testLoad() {
        CharsetHelper.getInstance().load(new ClassPathResourceLoader(TypeHelper.class));

        Assert.assertEquals(1,  CharsetHelper.getIdByName("UTF-8"));

        Assert.assertEquals("UTF-8",  CharsetHelper.getNameById(1));

    }
}
