package com.github.junrar.rarfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.junrar.io.Raw;

public class MacInfoHeader extends SubBlockHeader {

    private Log logger = LogFactory.getLog(getClass());

    public static final short MacInfoHeaderSize = 8;

    private int fileType;
    private int fileCreator;

    public MacInfoHeader(SubBlockHeader sb, byte[] macHeader) {
        super(sb);
        int pos = 0;
        fileType = Raw.readIntLittleEndian(macHeader, pos);
        pos += 4;
        fileCreator = Raw.readIntLittleEndian(macHeader, pos);
    }

    /**
     * @return the fileCreator
     */
    public int getFileCreator() {
        return fileCreator;
    }

    /**
     * @param fileCreator the fileCreator to set
     */
    public void setFileCreator(int fileCreator) {
        this.fileCreator = fileCreator;
    }

    /**
     * @return the fileType
     */
    public int getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public void print() {
        super.print();
        logger.info("filetype: " + fileType);
        logger.info("creator :" + fileCreator);
    }

}
