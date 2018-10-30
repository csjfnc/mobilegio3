package com.visium.fieldservice.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.visium.fieldservice.BuildConfig;
import com.visium.fieldservice.FieldService;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineServiceOrderResponse;
import com.visium.fieldservice.controller.UserController;
import com.visium.fieldservice.entity.ServiceOrder;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.preference.PreferenceHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class FileUtils {

    private static final String PATH_DOWNLOAD_OS_API = "/OfflineMode/GetStructureOs?IdOrdemDeServico=";
    private static final String TOKEN_HEADER = "Token";
    private static final Gson GSON = new GsonBuilder().create();
    private static final String version = String.valueOf(BuildConfig.VERSION_CODE);
    //private static final String version = "137";

    public static void downloadServiceOrder(long orderId) {

        String url = String.format("%s%s%s", FieldService.get().getString(R.string.fieldservice_server_api), PATH_DOWNLOAD_OS_API, orderId);
        String fileName = FieldService.get().getString(R.string.download_file_name, orderId);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.addRequestHeader(TOKEN_HEADER, UserController.get().getUserProfile().getAuthToken().get());
        request.addRequestHeader("IMEI", "358502078953166");
        request.setDescription(String.valueOf(orderId));
        request.setTitle(fileName);

        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        File f = Environment.getExternalStoragePublicDirectory("/visium");

        // V 138
        File ver = new File(version);
        File visium = new File(f, ver.getName());

        //Veirifca se pasta Visium exsite, se nao ela é criada
        if(!f.exists()){
            f.mkdir();
        }
        //Verifca se a pasta com o nome da versao exite, se nao ela é criada
        if(!visium.exists()){
            visium.mkdir();
        }

        File f1 = Environment.getExternalStoragePublicDirectory("/visium/"+ver.getName());
        request.setDestinationInExternalPublicDir(f.getName(), ver.getName()+"/"+fileName);

       // File destinoBkp = Environment.getExternalStoragePublicDirectory("/bkp_visium");



        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) FieldService.get().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        /*try {
            copyFile(f1, destinoBkp);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

     /*   File file1 = getFile(orderId);
        File f2 = Environment.getExternalStoragePublicDirectory("/bkp");
        if(!f2.exists()){
            f2.mkdirs();
        }
        try {
            copyFile(file1, f2);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

  /*  public static void copyFile(File source, File destination) throws IOException {
        if (destination.exists())
            destination.delete();
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
        }
    }*/

    public static OfflineDownloadResponse retrieve(long orderId) throws IOException {

        FileReader reader = new FileReader(FileUtils.getFile(orderId));
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(reader);
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        reader.close();
        return GSON.fromJson(sb.toString(), OfflineDownloadResponse.class);
    }

    public static List<ServiceOrder> retrieveAll() {

        List<ServiceOrder> orders = new ArrayList<>();
        Set<String> ids = PreferenceHelper.getOfflineIds();
        Set<String> idsToRemove = new HashSet<>();

        synchronized (ids) {
            for (String id : ids) {
                try {
                    orders.add(retrieve(Long.valueOf(id)).getServiceOrder());

                } catch (FileNotFoundException fe) {
                    idsToRemove.add(id);
                } catch (IOException e) {
                    LogUtils.error(FileUtils.class, e);
                }
            }

            ids.removeAll(idsToRemove);
            PreferenceHelper.setOfflineIds(ids);
        }

        return orders;
    }

    public static boolean saveOfflineDownload(OfflineDownloadResponse downloaded) {
        boolean success;
        try {
            FileUtils.write(downloaded);
            success = true;
        } catch (IOException e) {
            LogUtils.error(FileUtils.class, e);
            success = false;
        }
        return success;
    }

    public static boolean saveServiceOrder(ServiceOrderDetails serviceOrderDetails) {
        boolean success;
        try {
            OfflineDownloadResponse downloaded = FileUtils.retrieve(serviceOrderDetails.getId());
            OfflineServiceOrderResponse serviceOrder = downloaded.getOfflineServiceOrder();
            serviceOrder.setFinishDateTime(serviceOrderDetails.getFinishDateTime());
            serviceOrder.setStatus(serviceOrderDetails.getFinishDateTime() != null ? 1 : 0);
            success = saveOfflineDownload(downloaded);
        } catch (IOException e) {
            LogUtils.error(FileUtils.class, e);
            success = false;
        }
        return success;
    }

    private static void write(OfflineDownloadResponse downloaded) throws IOException {

        String fileName = FieldService.get().getString(R.string.download_file_name,
                downloaded.getOfflineServiceOrder().getId());

        File dir = Environment.getExternalStoragePublicDirectory(FieldService.get().getString(R.string.download_folder)+"/"+version);

        File file = new File(dir, fileName);

        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(GSON.toJson(downloaded));

        //Bakcup
        /*
        if(!downloaded.getPosts().isEmpty()){
            File bkp_dir = Environment.getExternalStoragePublicDirectory(FieldService.get().getString(R.string.download_folder));
            File bkp_file = new File(bkp_dir, fileName);
            FileWriter bkp_fw = new FileWriter(bkp_file, false);
            BufferedWriter bkp_bw = new BufferedWriter(bkp_fw);
            bkp_bw.write(GSON.toJson(downloaded));
        }*/

        bw.close();
    }

    public static boolean serviceOrderFileExists(long orderId) {
        return getFile(orderId).exists();
    }

    public static boolean deleteServiceOrderFile(long orderId) {
        return getFile(orderId).delete();
    }

    public static File getFile(long orderId) {
        String fileName = FieldService.get().getString(R.string.download_file_name, orderId);

        return new File(String.format("%s%s", Environment.getExternalStorageDirectory(),
                FieldService.get().getString(R.string.download_folder)+"/"+version), fileName);
    }

    public static void deleteAll() {
        LogUtils.log("DELETE ALL CALLED");
        File dir = Environment.getExternalStoragePublicDirectory(FieldService.get().getString(R.string.download_folder)+"/"+version);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }
}