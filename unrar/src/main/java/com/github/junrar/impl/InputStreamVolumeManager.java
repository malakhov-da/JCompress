package com.github.junrar.impl;

import java.io.IOException;
import java.io.InputStream;

import com.github.junrar.Archive;
import com.github.junrar.Volume;
import com.github.junrar.VolumeManager;

public class InputStreamVolumeManager implements VolumeManager {

    private final InputStream firstVolume;

    public InputStreamVolumeManager(InputStream firstVolume) {
        this.firstVolume = firstVolume;
    }

    @Override
    public Volume nextArchive(Archive archive, Volume last) throws IOException {
        return last != null ? null : new InputStreamVolume(archive, firstVolume);
    }
}
