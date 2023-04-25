package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path>{
    Map<Integer, FileProperty> tmpMap = new HashMap<>();
    Map <Path, FileProperty> listOfDuplicates = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fp = new FileProperty(attrs.size(), file.getFileName().toString());
        Map <Path, FileProperty> tmp = new HashMap<>();

        if (!tmpMap.containsKey(fp.hashCode())) {
            tmpMap.put(fp.hashCode(), fp);
        } else {
            tmp.put(file.toAbsolutePath(), fp);
        }

        listOfDuplicates = tmp;
        listOfDuplicates.forEach((k, v) -> System.out.printf("Key: %s, Value: %s \n", k.toAbsolutePath(), v.getSize()));

        return super.visitFile(file, attrs);
    }

}
