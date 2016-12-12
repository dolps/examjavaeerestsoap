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
    public void testCanCrud() throws Exception {
        int size = categoryEJB.findAll().size();
        Category category = new Category("text");
        category = categoryEJB.create(category);

        assertTrue(category.getId() != null);
        assertEquals(size + 1, categoryEJB.findAll().size());

        category = categoryEJB.findById(category.getId());
        assertEquals("text", category.getName());

        category.setName("text2");
        category = categoryEJB.update(category);
        category = categoryEJB.findById(category.getId());
        assertEquals("text2", category.getName());

        categoryEJB.remove(category);
        assertEquals(size, categoryEJB.findAll().size());
    }

}