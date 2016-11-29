package com.woact.dolplads.exam2016.backend.service;

import com.woact.dolplads.exam2016.backend.entity.*;
import com.woact.dolplads.exam2016.backend.testUtils.ArquillianTestHelper;
import com.woact.dolplads.exam2016.backend.testUtils.DeleterEJB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 29/11/2016.
 */

public class CategoryEJBTest extends ArquillianTestHelper {
    @EJB
    private DeleterEJB deleterEJB;
    @EJB
    private CategoryEJB categoryEJB;

    @Before
    @After
    public void setUp() throws Exception {
        deleterEJB.deleteEntities(Category.class);
    }

    @Test
    public void createCategory() throws Exception {
        Category c = new Category("text");
        c = categoryEJB.createCategory(c);

        assertTrue(c.getId() != null);
    }

    @Test
    public void removeCategory() throws Exception {
        Category c = new Category("text2");
        c = categoryEJB.createCategory(c);
        assertTrue(c.getId() != null);

        categoryEJB.removeCategory(c);
        assertTrue(categoryEJB.findById(c.getId()) == null);
    }

}