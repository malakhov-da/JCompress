package com.github.jcompress.unrar.impl;

import java.io.File;
import java.io.IOException;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.Volume;
import com.github.jcompress.unrar.VolumeManager;
import com.github.jcompress.unrar.util.VolumeHelper;


public class FileVolumeManager implements VolumeManager {

    private final File firstVolume;

    public FileVolumeManager(File firstVolume) {
        this.firstVolume = firstVolume;
    }

    @Override
    public Volume nextArchive(Archive archive, Volume last)
            throws IOException {
        if (last == null) {
            return new FileVolume(archive, firstVolume);
        }

        FileVolume lastFileVolume = (FileVolume) last;
        boolean oldNumbering = !archive.getMainHeader().isNewNumbering()
                || archive.isOldFormat();
        String nextName = VolumeHelper.nextVolumeName(lastFileVolume.getFile()
                .getAbsolutePath(), oldNumbering);
        File nextVolume = new File(nextName);

        return new FileVolume(archive, nextVolume);
    }
}
