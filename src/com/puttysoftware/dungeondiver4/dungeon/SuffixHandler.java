package com.puttysoftware.dungeondiver4.dungeon;

import java.io.IOException;

import org.retropipes.diane.fileio.XDataReader;
import org.retropipes.diane.fileio.XDataWriter;

import com.puttysoftware.dungeondiver4.DungeonDiver4;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        DungeonDiver4.getApplication().getGameManager().loadGameHook(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        DungeonDiver4.getApplication().getGameManager().saveGameHook(writer);
    }
}
