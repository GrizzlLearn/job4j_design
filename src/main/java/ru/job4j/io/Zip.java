package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<File> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            sources.forEach(s -> {
                try {
                    zip.putNextEntry(new ZipEntry(s.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(s))) {
                    zip.write(out.readAllBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();
        ArgsName name = ArgsName.of(args);
        List<File> lf = new ArrayList<>();
        String[] parameterNames = new String[]{"d", "e", "o"};
        Predicate<Path> predicate = p -> !p.toFile().getName().endsWith(name.get(parameterNames[1]));

        for (Path path : Search.search(Paths.get(name.get(parameterNames[0])), predicate)) {
            lf.add(path.toFile());
        }

        zip.packFiles(lf, new File("../job4j_design.zip"));
    }
}
