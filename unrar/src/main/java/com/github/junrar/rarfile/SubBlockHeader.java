package com.github.junrar.rarfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.junrar.io.Raw;

public class SubBlockHeader extends BlockHeader {

    private Log logger = LogFactory.getLog(getClass());

    public static final short SubBlockHeaderSize = 3;

    private short subType;
    private byte level;

    public SubBlockHeader(SubBlockHeader sb) {
        super(sb);
        subType = sb.getSubType().getSubblocktype();
        level = sb.getLevel();
    }

    public SubBlockHeader(BlockHeader bh, byte[] subblock) {
        super(bh);
        int position = 0;
        subType = Raw.readShortLittleEndian(subblock, position);
        position += 2;
        level |= subblock[position] & 0xff;
    }

    /**
     * @return
     */
    public byte getLevel() {
        return level;
    }

    /**
     * @return
     */
    public SubBlockHeaderType getSubType() {
        return SubBlockHeaderType.findSubblockHeaderType(subType);
    }

    public void print() {
        super.print();
        logger.info("subtype: " + getSubType());
        logger.info("level: " + level);
    }
}
