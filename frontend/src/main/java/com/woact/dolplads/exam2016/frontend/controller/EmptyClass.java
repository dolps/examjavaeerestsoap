package com.woact.dolplads.exam2016.frontend.controller;

import com.woact.dolplads.exam2016.backend.service.CategoryEJB;

import javax.ejb.EJB;
import javax.inject.Named;

/**
 * Created by dolplads on 29/11/2016.
 */
@Named
public class EmptyClass {
    @EJB
    private CategoryEJB categoryEJB;
}
