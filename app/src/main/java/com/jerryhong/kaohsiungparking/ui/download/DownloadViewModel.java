package com.jerryhong.kaohsiungparking.ui.download;

import com.jerryhong.kaohsiungparking.base.BaseViewModel;
import com.jerryhong.kaohsiungparking.data.DataModel;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;
import com.jerryhong.kaohsiungparking.util.CSVReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DownloadViewModel extends BaseViewModel {

    public void downloadParkingData(final File file){

        addDisposable(DataModel.getInstance().downloadParkingDataRepo()
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<ResponseBody>(){

                        @Override
                        public void onSuccess(ResponseBody responseBody) {
                            try{
                                InputStream inputStream = responseBody.byteStream();
                                OutputStream outputStream = new FileOutputStream(file);
                                ByteArrayOutputStream out = new ByteArrayOutputStream();


                                //寫入
                                byte[] buffer = new byte[256];
                                int n;
                                while ((n = inputStream.read(buffer)) >= 0) {
                                    outputStream.write(buffer, 0, n);
                                }
                                outputStream.flush();
                                //讀黨
                                CSVReader csvReader = new CSVReader(new FileReader(file));
                                String[] nextLine;
                                int count = 0;
                                StringBuilder columns = new StringBuilder();
                                StringBuilder value = new StringBuilder();
                                //跳過第一行
                                csvReader.readNext();
                                List<ParkingEntity> entityList = new ArrayList<>();
                                while ((nextLine = csvReader.readNext()) != null) {
                                    ParkingEntity entity = new ParkingEntity(nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4],nextLine[5],nextLine[6],nextLine[7],nextLine[8],nextLine[9],nextLine[10],nextLine[11]);
                                    DatabaseManager.getInstance().getParkingDAO().insert(entity);
                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    }));
    }
}
