package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path>{
    Map<String, Path> tmpMap = new HashMap<>();
    int duplicatesCount = 0;
    //Map<Path, List<Path>> mapOfDuplicates = new HashMap<>();
    List <Path> listOfDuplicates = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fp = new FileProperty(attrs.size(), file.getFileName().toString());
        int dc = duplicatesCount;
        List<Path> tmp = new ArrayList<>();

        if (!tmpMap.containsKey(fp.getName())) {
            tmpMap.put(fp.getName(), file.toAbsolutePath());
        } else {
            tmp.add(file.toAbsolutePath());
        }

        listOfDuplicates = tmp;
        listOfDuplicates.forEach(System.out::println);

        return super.visitFile(file, attrs);
    }

}
