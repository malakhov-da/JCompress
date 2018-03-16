package com.github.junrar;

import java.io.IOException;

public interface VolumeManager {

    public Volume nextArchive(Archive archive, Volume lastVolume) throws IOException;
}
