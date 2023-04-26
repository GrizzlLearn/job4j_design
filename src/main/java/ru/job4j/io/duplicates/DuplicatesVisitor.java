package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private Map<FileProperty, List<Path>> tmpMap = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fp = new FileProperty(attrs.size(), file.getFileName().toString());
        List<Path> tmpListPath = new ArrayList<>();

        if (!tmpMap.containsKey(fp)) {
            tmpListPath.add(file.toAbsolutePath());
            tmpMap.put(fp, tmpListPath);
        } else {
            tmpMap.get(fp).add(file.toAbsolutePath());
        }

        return super.visitFile(file, attrs);
    }

    public ArrayList<Path> printPathOfDuplicates() {
        ArrayList<Path> result = new ArrayList<>();

        tmpMap.forEach((k, v) -> {
            if (v.size() > 1) {
                result.addAll(v);
            }
        });

        return result;
    }
}
