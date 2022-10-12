package runner;

import uploader.AvatarUploader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {
   private final Path avatarDirectoryPath;

   public Runner() {
      this.avatarDirectoryPath = Paths.get(System.getProperty("user.dir") + File.separator + "avatar");
   }

   public static void main(String[] args) {
      Runner runner = new Runner();
      runner.run();
   }

   private void run() {
      List<Path> avatarList = getAvatarList();
      for (Path path : avatarList) {
         System.out.println("Filename: " + path.getFileName());
         try {
            new AvatarUploader().postForAvatar(path.toString());
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   private List<Path> getAvatarList() {
      try {
         return Files.list(avatarDirectoryPath).collect(Collectors.toList());
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
