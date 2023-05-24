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
            for (File s : sources) {
                zip.putNextEntry(new ZipEntry(s.getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(s))) {
                    zip.write(out.readAllBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean validateRootAndTarget(String root, File target) {
        File file = new File(root);

        if (!file.exists()) {
            throw new IllegalArgumentException("You must set EXIST ROOT DIRECTORY.");
        }

        if (!file.isDirectory()) {
            throw new IllegalArgumentException("You must set ROOT DIRECTORY, not FILE.");
        }

        if (target.exists()) {
            throw new IllegalArgumentException("A file with the same name already EXISTS.");
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();
        ArgsName name = ArgsName.of(args);
        List<File> lf = new ArrayList<>();
        String[] parametersArray = new String[]{"d", "e", "o"};
        Predicate<Path> predicate = p -> !p.toFile().getName().endsWith(name.get(parametersArray[1]));
        String root = name.get(parametersArray[0]);
        File target = new File("../job4j_design.zip");

        if (validateRootAndTarget(root, target)) {
            for (Path path : Search.search(Paths.get(root), predicate)) {
                lf.add(path.toFile());
            }

            zip.packFiles(lf, target);
            System.out.println("Archiving is complete!");
        }
    }
}
