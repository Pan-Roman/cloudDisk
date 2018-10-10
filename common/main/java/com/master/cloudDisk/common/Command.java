package com.master.cloudDisk.common;

import com.master.cloudDisk.common.Interfaces.ICommand;

import java.io.Serializable;

public abstract class Command implements ICommand, Serializable {
    public static final int MAX_OBJ_SIZE = 1024 * 1024;
    abstract public void execute();
}
