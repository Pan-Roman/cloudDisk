package com.master.cloudDisk.common.commands;

import java.nio.file.Path;

public class GetListCommand extends Command {
    private Path path;
    public GetListCommand(Path path) {
        this.path = path;
    }
}
