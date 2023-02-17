package com.meylism.reporter;

import com.meylism.ds.ScrapeResult;
import lombok.Getter;

import java.io.File;

public abstract class FileReporter {
    @Getter
    private ScrapeResult result;

    FileReporter(ScrapeResult result) {
        this.result = result;
    }

    public abstract void export(File file);
}
