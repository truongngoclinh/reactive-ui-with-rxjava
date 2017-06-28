package com.example.base.task;

import com.example.base.db.DbManager;
import com.example.base.res.ResourceManager;

import javax.inject.Inject;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class TaskResource {

    @Inject
    public ResourceManager resourceManager;

    @Inject
    public DbManager dbManager;

}
