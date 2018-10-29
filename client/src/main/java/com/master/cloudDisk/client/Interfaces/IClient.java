package com.master.cloudDisk.client.Interfaces;

import com.master.cloudDisk.client.ClientPathControl;
import com.master.cloudDisk.common.ServerAnswers;

public interface IClient {
    public void onGotMessage(ServerAnswers msg);

    public static ClientPathControl getPathControl() {
        return null;
    }
}
