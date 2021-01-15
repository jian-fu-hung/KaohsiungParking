package com.jerryhong.kaohsiungparking.ui.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.base.BaseActivity;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;
import com.jerryhong.kaohsiungparking.databinding.ActivityMainBinding;
import com.jerryhong.kaohsiungparking.ui.download.DownloadActivity;
import com.jerryhong.kaohsiungparking.ui.list.ListActivity;
import com.jerryhong.kaohsiungparking.ui.search.SearchActivity;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    private GoogleMap mMap;

    private static String TAG = "MainActivity";

    private LocationManager locationManager;

    private Geocoder geocoder;

    private List<Address> geocodeAddress;

    private LatLng mLatLng;

    private Disposable disposableMarker = new CompositeDisposable();

    private final static String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        DatabaseManager.getInstance().initDatabase(getApplicationContext());

        createLocationManager();
        createMap();

        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        binding.imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_list:
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_history:
                        Toast.makeText(MainActivity.this, "menu_history", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_download:
                        intent = new Intent(MainActivity.this, DownloadActivity.class);
                        startActivity(intent);
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

        viewModel.parkingList.observe(this, new Observer<List<ParkingEntity>>() {
            @Override
            public void onChanged(List<ParkingEntity> parkingEntityList) {
                if(parkingEntityList != null){

                    addDisposable(Flowable.fromIterable(parkingEntityList).delay(2, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSubscriber<ParkingEntity>(){
                                @Override
                                public void onNext(ParkingEntity parkingEntity) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(parkingEntity.Latitude), Double.parseDouble(parkingEntity.Longitude))));
                                }

                                @Override
                                public void onError(Throwable t) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            }));

//                    addDisposable(Observable.fromIterable(parkingEntityList)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeWith(new ResourceObserver<ParkingEntity>() {
//                                           @Override
//                                           public void onNext(ParkingEntity parkingEntity) {
//                                               mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(parkingEntity.Latitude), Double.parseDouble(parkingEntity.Longitude))));
//                                           }
//
//                                           @Override
//                                           public void onError(Throwable e) {
//
//                                           }
//
//                                           @Override
//                                           public void onComplete() {
//
//                                           }
//                                       }));

//                                    Observable a = Observable.fromIterable(parkingEntityList);
//                    a.subscribe(new io.reactivex.Observer<ParkingEntity>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(ParkingEntity parkingEntity) {
//
//                        }
//
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//                    addDisposable(Observable.just(parkingEntityList)
//                            .toFlowable(BackpressureStrategy.BUFFER)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .flatMap(new Function<List<ParkingEntity>, Publisher<?>>() {
//                                @Override
//                                public Publisher<?> apply(List<ParkingEntity> parkingEntityList) throws Exception {
//                                    return null;
//                                }
//                            })
//                            .map(item -> {
//                                return mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(item.Latitude), Double.parseDouble(item.Longitude))));
//                            } );

//                    Flowable<List<ParkingEntity>> test = Flowable.just(parkingEntityList).subscribeOn(new Scheduler() {
//                    })
//                    addDisposable(Observable.fromIterable(parkingEntityList)
//                                .subscribeOn(new DisposableCompletableObserver<List<ParkingEntity>>(){
//
//                                    @Override
//                                    public void onComplete() {
//
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//
//                                    }
//                                }));
                }
//                addDisposable(Observable.fromIterable(parkingEntityList)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeOn(new Scheduler() {
//                                @Override
//                                public Worker createWorker() {
//                                    return null;
//                                }
//                            }new Subscriber<List<ParkingEntity>>(){
//
//                                @Override
//                                public void onSubscribe(Subscription s) {
//
//                                }
//
//                                @Override
//                                public void onNext(List<ParkingEntity> parkingEntityList) {
//
//                                }
//
//                                @Override
//                                public void onError(Throwable t) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            }));

//                addDisposable(Flowable.just(parkingEntityList)
//                        .subscribeOn(Schedulers.computation())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<List<ParkingEntity>>() {
//                            @Override
//                            public void onSubscribe(Subscription s) {
//
//                            }
//
//                            @Override
//                            public void onNext(List<ParkingEntity> parkingEntityList) {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable t) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        }));
//                        .subscribe(new Subscriber<ParkingEntity>() {
//                            @Override
//                            public void onSubscribe(Subscription s) {
//                                Log.d(TAG, "onComplete: ");
//                            }
//
//                            @Override
//                            public void onNext(ParkingEntity parkingEntity) {
//                                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(parkingEntity.Latitude), Double.parseDouble(parkingEntity.Longitude))));
//                            }
//
//                            @Override
//                            public void onError(Throwable t) {
//                                Log.d(TAG, "onComplete: ");
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: ");
//                            }
//                        }));

//                for(ParkingEntity item : parkingEntityList){
//                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(item.Latitude), Double.parseDouble(item.Longitude))));
//                }
            }
        });
    }

    //建立googleMap
    private void createMap() {
        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (map != null) {
            map.getMapAsync(onMapReadyCallback);
        }
    }

    private void createLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            finish();
        }

        geocoder = new Geocoder(this, Locale.getDefault());

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //更新目前經緯度
                        if (isFinishing() || isDestroyed()) {
                            locationManager.removeUpdates(this);
                        } else {
                            mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        }

                        try {
                            geocodeAddress = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1);
//                            binding.tvAddress.setText(getString(R.string.Current_location, geocodeAddress.get(0).getAddressLine(0)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                });

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null)
            mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    //檢查權限
    private boolean checkPermission(String... permissions) {
        //檢查是否取得權限
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //回傳函式 googlemap建立成功
    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            //如果有開權限的話
            if (checkPermission(PERMISSIONS)) {
                //設定顯示目前位置
                mMap.setMyLocationEnabled(true);
                //把定位的button隱藏起來
                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
            }

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkPermission(PERMISSIONS)) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(mLatLng));
                    }
                }
            });

            viewModel.readDB();
        }
    };


}
