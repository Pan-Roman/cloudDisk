package com.master.cloudDisk.common.commands;

import java.nio.file.Path;

public class DeletePathCommand extends Command {
    private Path path;
    public DeletePathCommand(Path path) {
        this.path = path;
    }
}