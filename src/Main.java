import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

  public static void main(String[] args) {
    openZip("C:\\Games\\savegames\\save.zip", "C:\\Games\\savegames");
    GameProgress gameProgress = openProgress("C:\\Games\\savegames\\save1.dat");
    System.out.println(gameProgress);
  }

  public static void openZip(String zipPath, String dirPath) {
    try (ZipInputStream zip = new ZipInputStream(new FileInputStream(zipPath))) {
      ZipEntry entry;
      String name;
      while ((entry = zip.getNextEntry()) != null) {
        name = entry.getName();
        FileOutputStream file = new FileOutputStream(dirPath + "\\" + name);
        for (int c = zip.read(); c != -1; c = zip.read()) {
          file.write(c);
        }
        file.flush();
        zip.closeEntry();
        file.close();
        System.out.println("Извлечен файл " + name);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static GameProgress openProgress(String filePath) {
    GameProgress gameProgress = null;
    try (FileInputStream file = new FileInputStream(filePath);
         ObjectInputStream obj = new ObjectInputStream(file)) {
      gameProgress = (GameProgress) obj.readObject();
      System.out.println("Считан файл " + filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gameProgress;
  }
}
