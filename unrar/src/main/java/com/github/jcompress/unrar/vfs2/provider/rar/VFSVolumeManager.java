package com.github.jcompress.unrar.vfs2.provider.rar;

import java.io.IOException;

import org.apache.commons.vfs2.FileObject;

import com.github.jcompress.unrar.Archive;
import com.github.jcompress.unrar.Volume;
import com.github.jcompress.unrar.VolumeManager;
import com.github.jcompress.unrar.util.VolumeHelper;

public class VFSVolumeManager implements VolumeManager {

    private final FileObject firstVolume;

    /**
     * @param firstVolume
     */
    public VFSVolumeManager(FileObject firstVolume) {
        this.firstVolume = firstVolume;
    }

    @Override
    public Volume nextArchive(Archive archive, Volume last) throws IOException {
        if (last == null) {
            return new VFSVolume(archive, firstVolume);
        }

        VFSVolume vfsVolume = (VFSVolume) last;
        boolean oldNumbering = !archive.getMainHeader().isNewNumbering()
                || archive.isOldFormat();
        String nextName = VolumeHelper.nextVolumeName(vfsVolume.getFile()
                .getName().getBaseName(), oldNumbering);
        FileObject nextVolumeFile = firstVolume.getParent().resolveFile(
                nextName);

        return new VFSVolume(archive, nextVolumeFile);
    }
}
