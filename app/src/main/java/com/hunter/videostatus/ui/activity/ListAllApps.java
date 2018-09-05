package com.hunter.videostatus.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.hunter.videostatus.R;
import com.hunter.videostatus.listeners.OnAppItemClick;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

public class ListAllApps extends AppCompatActivity implements OnAppItemClick{

    private List<ResolveInfo> pkgAppsList;
    private AllAppsAdapter adapter;
    private ResolveInfo currentSelectedApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.header_color));
        }
        initListView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("App Extractor");
        getSupportActionBar().setHomeButtonEnabled(true);
        new FetchAllApps().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                new ExtractAllApps(pkgAppsList).execute();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onItemClick(ResolveInfo resolveInfo) {
        this.currentSelectedApp = resolveInfo;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        extractApp(resolveInfo,true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            extractApp(currentSelectedApp, true);
        }
    }

    private String getAppName(ResolveInfo resolveInfo, PackageManager pm) {
        try {
            String package_name = resolveInfo.activityInfo.packageName;
            String app_name = (String) pm.getApplicationLabel(pm.getApplicationInfo(package_name, PackageManager.GET_META_DATA));
            return app_name;
            //Log.i("Check", "package = <" + package_name + "> name = <" + app_name + ">");
        } catch (Exception e) {
        }
        return "";
    }

    private void extractApp(ResolveInfo resolveInfo,boolean showToast) {
        File file = new File(resolveInfo.activityInfo.applicationInfo.publicSourceDir);
        OutputStreamWriter out;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "ExtractedApks");

            if (!path.exists()) {
                path.mkdir();
            }

            path = new File(path, getAppName(resolveInfo, getPackageManager()) + ".apk");
            copy(file, path,showToast);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void copy(File src, File dst, boolean showToast) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
        if (showToast)
            Toast.makeText(this, "Extracted at " + dst.getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }


    private class FetchAllApps extends AsyncTask<Void,Void,List<ResolveInfo>>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ListAllApps.this,"Please Wait","Loading Applications");
        }

        @Override
        protected List<ResolveInfo> doInBackground(Void... voids) {
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
            Collections.sort(pkgAppsList, new ResolveInfo.DisplayNameComparator(getPackageManager()));
            return pkgAppsList;
        }

        @Override
        protected void onPostExecute(final List<ResolveInfo> resolveInfos) {
            super.onPostExecute(resolveInfos);
            if (!resolveInfos.isEmpty()) {
                adapter.setData(resolveInfos);
            }
            dialog.dismiss();
        }
    }

    private class ExtractAllApps extends AsyncTask<Void, Integer, Void> {

        private final List<ResolveInfo> list;

        public ExtractAllApps(List<ResolveInfo> list) {
            this.list = list;
        }

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ListAllApps.this);
            progressDialog.setTitle("Extracting...");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.setMax(list.size());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (ResolveInfo resolveInfo : list) {
                extractApp(resolveInfo, false);
                publishProgress(list.indexOf(resolveInfo));
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void adas) {
            super.onPostExecute(adas);

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void initListView() {
        RecyclerView recyclerView = findViewById(R.id.list_of_all_apps);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AllAppsAdapter();
        adapter.setOnItemClick(this);
        recyclerView.setAdapter(adapter);
    }
}
