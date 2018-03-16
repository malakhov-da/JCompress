package com.github.jcompress.unrar.impl;

import java.io.IOException;
import java.io.InputStream;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.Volume;
import com.github.jcompress.unrar.VolumeManager;

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
