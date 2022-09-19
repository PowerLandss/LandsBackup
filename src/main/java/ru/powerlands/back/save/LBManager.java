package ru.powerlands.back.save;

import ru.powerlands.back.LandsBackup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LBManager {

    public LBManager() {

    }
    private List<String> blacklist = LandsBackup.getMain().getConfig().getStringList("settings.blacklist");
    public void save() {
        try {
            long m = System.currentTimeMillis();
            String name = LandsBackup.date();
            System.out.println("LandsBackup: Попытка сохранить сборку от даты: " + name);
            String zipname = LandsBackup.getMain().getConfig().getString("settings.server") + name + ".zip";
            String sourceFile = type();
            FileOutputStream fos = null;
            fos = new FileOutputStream(zipname);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);
            setFileToZip(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            Path path = Paths.get("" + zipname);
            Files.copy(path, Paths.get(LandsBackup.getMain().getDataFolder().getAbsolutePath() + "/backups/" + zipname), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
            System.out.println("LandsBackup: Сборка сохранена успешно по пути: " + LandsBackup.getMain().getDataFolder().getAbsolutePath() + "/backups/" + zipname + " за " + (System.currentTimeMillis() - m) + " ms.");
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setFileToZip(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden())
            return;
        if (fileToZip.isDirectory()) {
                if (fileName.endsWith("/")) {
                    zipOut.putNextEntry(new ZipEntry(fileName));
                    zipOut.closeEntry();
                } else {
                    zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                    zipOut.closeEntry();
                }
                File[] children = fileToZip.listFiles();
                for (File childFile : children) {
                    setFileToZip(childFile, fileName + "/" + childFile.getName(), zipOut);
                }
                return;

        }
        if(!blacklist.contains(fileName)) {
            if (!fileName.contains(".zip")) {
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0)
                    zipOut.write(bytes, 0, length);
                fis.close();
            }
        }
    }
    private String typ = LandsBackup.getMain().getConfig().getString("settings.type");
    public String type() {
        if(typ.toLowerCase().equals("plugins")) {
            return "plugins";
        } else if (typ.toLowerCase().equals("all")) {
            return LandsBackup.getMain().getDataFolder().getAbsolutePath().replaceAll("plugins/LandsBackup", "");
        }
        return null;
    }
}
