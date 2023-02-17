package com.meylism.reporter;

import com.meylism.ds.ScrapeResult;

import java.io.File;

public abstract class FileReporter {

    public abstract void export(ScrapeResult result, File file);
}
