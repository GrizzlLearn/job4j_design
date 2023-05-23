package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<File> sources, File target) {

    }

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
        String[] test = new String[]{"d", "e", "o"};
        Predicate<Path> predicate = p -> !p.toFile().getName().endsWith(name.get(test[1]));


        List<File> lf = new ArrayList<>();

        for (Path path : Search.search(Paths.get(name.get(test[0])), predicate)) {
            lf.add(new File(path.getFileName().toUri()));
        }

        lf.forEach(System.out::println);

        zip.packFiles(lf, new File("/Users/dvl/projects/job4j_design.zip"));


        /*System.out.println(
                Search.search(
                        Paths.get(name.get(test[0])),
                        predicate));*/

        //Search.search(ArgsName.of(args).get(args[0]), p -> p.toFile().getName().endsWith(args[1]));

        /*zip.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );*/
    }
}
