package com.prouvetech.prouvetech.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.DemuxerTrackMeta;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.common.io.SeekableByteChannel;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Uploads {

    private static final long MAX_VIDEO_SIZE = 50 * 1024 * 1024; // 50MB
    private static final double MAX_DURATION_SECONDS = 120.0; // 2 minutes

    public String saveFile(MultipartFile file) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(file.getInputStream());

        String uploadDir = "";

        if (detectedType.startsWith("video/")) {

            uploadDir = "uploads/videos/";

        } else if (detectedType.startsWith("image/")) {

            uploadDir = "uploads/logos/";

        } else if (detectedType.startsWith("application/pdf")) {

            uploadDir = "uploads/documents/";

        }

        File uploadPath = new File(uploadDir);

        // Créer le dossier s'il n'existe pas
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Sauvegarder l'image avec un nom unique
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner l'URL de l'image
        return "/" + uploadDir + fileName;
    }

    public String updateFile(MultipartFile file, String old) throws IOException {
        Tika tika = new Tika();

        String detectedType = tika.detect(file.getInputStream());

        String uploadDir = "";

        if (detectedType.startsWith("video/")) {

            uploadDir = "uploads/videos/";

        } else if (detectedType.startsWith("image/")) {

            uploadDir = "uploads/logos/";

        } else if (detectedType.startsWith("application/pdf")) {

            uploadDir = "uploads/documents/";

        }
        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Supprimer l'ancienne image si elle existe
        if (old != null && !old.isEmpty()) {
            Path oldFilePath = Paths.get(old.replace("/" + uploadDir, uploadDir));
            File oldFile = oldFilePath.toFile();
            if (oldFile.exists()) {
                oldFile.delete(); // Suppression du fichier
            }
        }

        // Sauvegarder la nouvelle image avec un nom unique
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner la nouvelle URL de l'image
        return "/" + uploadDir + fileName;
    }

    public void deleteFile(String linkFile) {

        if (linkFile == null || linkFile.isEmpty())
            return;

        // Enlever le slash au début s'il existe
        if (linkFile.startsWith("/")) {
            linkFile = linkFile.substring(1);
        }

        Path filePath = Paths.get(linkFile);
        File file = filePath.toFile();

        if (file.exists()) {
            file.delete();
        }

    }

    public ResponseMessage validateVideo(MultipartFile file) throws IOException {
        Tika tika = new Tika();

        // Vérifier que le fichier n'est pas null ou vide
        if (file == null || file.isEmpty()) {
            return new ResponseMessage(false, "Aucun fichier fourni");
        }

        // Vérifier que c'est bien une vidéo
        String detectedType = tika.detect(file.getInputStream());
        if (!detectedType.startsWith("video/")) {
            return new ResponseMessage(false, "Le fichier doit être une vidéo");
        }

        // Vérifier la taille du fichier (50MB max)
        if (file.getSize() > MAX_VIDEO_SIZE) {
            return new ResponseMessage(false, "Le fichier ne doit pas dépasser 50MB max");
        }

        // Créer un fichier temporaire pour analyser la durée
        String tempFileName = System.currentTimeMillis() + "_temp_" + file.getOriginalFilename();
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), tempFileName);

        try {
            // Copier le fichier dans un dossier temporaire
            Files.copy(file.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);

            // Vérifier la durée de la vidéo
            double duration = getVideoDuration(tempPath.toFile());
            if (duration > MAX_DURATION_SECONDS) {
                return new ResponseMessage(false, "Le fichier ne doit pas dépasser 2 minutes");
            }

            // Si tout est OK
            return new ResponseMessage(true, "Vidéo valide");

        } catch (Exception e) {
            // En cas d'erreur lors de l'analyse de la durée
            System.err.println("Erreur lors de l'analyse de la vidéo: " + e.getMessage());
            return new ResponseMessage(false, "Erreur lors de l'analyse de la vidéo");

        } finally {
            // Supprimer le fichier temporaire
            try {
                Files.deleteIfExists(tempPath);
            } catch (IOException e) {
                // Log l'erreur mais ne pas faire planter l'application
                System.err.println("Erreur lors de la suppression du fichier temporaire: " + e.getMessage());
            }
        }
    }

    private double getVideoDuration(File videoFile) throws IOException {
        try {
            // Utiliser NIOUtils pour créer un SeekableByteChannel
            SeekableByteChannel channel = NIOUtils.readableChannel(videoFile);

            MP4Demuxer demuxer = MP4Demuxer.createMP4Demuxer(channel);
            DemuxerTrack videoTrack = demuxer.getVideoTrack();

            if (videoTrack == null) {
                throw new IOException("Aucune piste vidéo trouvée dans le fichier");
            }

            // Calculer la durée en secondes
            DemuxerTrackMeta meta = videoTrack.getMeta();
            return meta.getTotalDuration();

        } catch (Exception e) {
            // Fallback: estimation basée sur la taille
            System.err.println("Erreur analyse vidéo, utilisation estimation: " + e.getMessage());
            long fileSizeInBytes = videoFile.length();
            double fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0);
            return fileSizeInMB * 7; // 1MB ≈ 7 secondes
        }
    }

}
