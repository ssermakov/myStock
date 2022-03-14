package ru.ssermakov.mystock.views;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ru.ssermakov.mystock.R;
import ru.ssermakov.mystock.controllers.NcrAddSpareController;
import ru.ssermakov.mystock.data.room.entity.Spare;
import ru.ssermakov.mystock.views.interfaces.NcrAddSpareInterface;

public class NcrAddSpareActivity extends AppCompatActivity implements View.OnClickListener, NcrAddSpareInterface {

    private static final int EXCEL_REQUEST_CODE = 10;
    private static final String TAG = "EXCEL";
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 23;
    private static XSSFWorkbook myWorkBook;
    private EditText state, pn, desc, quantity, reworkCode, location;
    Button addSpareButton, openExcelButton;
    NcrAddSpareController ncrAddSpareController;
    private HSSFWorkbook workBook;
    private ArrayList<Spare> listOfSpares = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncr_add_spare);


        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        ncrAddSpareController = new NcrAddSpareController(this);

        state = findViewById(R.id.stateEditDlgEditText);
        pn = findViewById(R.id.pnEditDlgEditText);
        desc = findViewById(R.id.nameEditDlgEditText);
        quantity = findViewById(R.id.quantityEditDlgEditText);
        reworkCode = findViewById(R.id.reworkEditDlgEditText);
        location = findViewById(R.id.locationEditDlgEditText);

        addSpareButton = findViewById(R.id.addNcrSpareButton);
        openExcelButton = findViewById(R.id.openNcrExcelButton);

        addSpareButton.setOnClickListener(this);
        openExcelButton.setOnClickListener(this);

        // requesting permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
//            Toast.makeText(this, "You have permissions", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.addNcrSpareButton) {

            String state = this.state.getText().toString();
            String pn = this.pn.getText().toString();
            String desc = this.desc.getText().toString();
            String quantity = this.quantity.getText().toString();
            String rework = this.reworkCode.getText().toString();
            String location = this.location.getText().toString();

            ncrAddSpareController.addSpareToDb(new Spare(
                    desc,
                    pn,
                    state,
                    rework,
                    quantity,
                    location
            ));
        }

        if (viewId == R.id.openNcrExcelButton) {
            Intent i = createIntentForOpenExcel();
            startActivityForResult(i, EXCEL_REQUEST_CODE);
        }
    }

    @Override
    public Intent createIntentForOpenExcel() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/vnd.ms-excel");
//      i.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    public ArrayList<Spare> createListOfSparesFromWorkBook(Context context, HSSFWorkbook workBook) throws ExecutionException, InterruptedException {
        return ncrAddSpareController.getSpares(workBook);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        try {
            if (workBook != null) {
                setListOfSpares();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EXCEL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String filePath = getPathFromUri(data);
                setWorkBookFromExcel(filePath);
//                setListOfSpares();
            }
        }
    }

    private void setListOfSpares() throws ExecutionException, InterruptedException {
        this.listOfSpares = createListOfSparesFromWorkBook(this, workBook);
    }

    private void setWorkBookFromExcel(String filePath) {
        this.workBook = getWorkBookFromExcel(this, filePath);
    }

    private String getPathFromUri(Intent data) {
        Uri selectedImage = data.getData();
        String filePath;
        filePath = getPath(this, selectedImage);
        return filePath;
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        return getDataColumn(context, contentUri, null, null);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());

    }

    private static HSSFWorkbook getWorkBookFromExcel(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
        } else {
            try {
                // Creating Input Stream
                File file = new File(filename);
                FileInputStream inputStream = new FileInputStream(file);

                // Create a POIFSFileSystem object
                POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);

                // Create a workbook using the File System
                return new HSSFWorkbook(fileSystem);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

//    private class CreateListOfSparesTask extends AsyncTask<HSSFWorkbook, Void, ArrayList<Spare>> {
//
//        @Override
//        protected ArrayList<Spare> doInBackground(HSSFWorkbook... hssfWorkbooks) {
//            HSSFSheet mySheet = hssfWorkbooks[0].getSheetAt(0);
//            Iterator rowIterator = mySheet.rowIterator();
//            ArrayList<Spare> spares = new ArrayList<>();
//            while (rowIterator.hasNext()) {
//                HSSFRow row = (HSSFRow) rowIterator.next();
//                Iterator cellIterator = row.cellIterator();
//                int cellCounter = 0;
//                Spare spare = new Spare();
//                while (cellIterator.hasNext()) {
//                    HSSFCell cell = (HSSFCell) cellIterator.next();
//                    if (cell.getCellType() == CellType.NUMERIC) {
//                        cell.setCellType(CellType.STRING);
//                    }
//                    Log.d(TAG, "Cell value: " + cell.toString());
//                    if (cellCounter == 0) {
//                        String partNumber = validatePartNumber(cell.toString());
//                        spare.setPartNumber(partNumber);
//                    }
//                    if (cellCounter == 1) {
//                        String name = cell.toString();
//                        spare.setName(name);
//                    }
//                    if (cellCounter == 2) {
//                        String state = cell.toString();
//                        spare.setState(state);
//                    }
//                    if (cellCounter == 3) {
//                        String quantity = cell.toString();
//                        spare.setQuantity(quantity);
//                    }
//                    if (cellCounter == 4) {
//                        String returnCode = cell.toString();
//                        spare.setReturnCode(returnCode);
//                    }
//                    if (!cellIterator.hasNext()) {
//                        spare.setLocation("garage");
//                        spares.add(spare);
//                    }
//                    cellCounter++;
//                }
//            }
//            return spares;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Spare> spares) {
//            super.onPostExecute(spares);
//            Toast.makeText(NcrAddSpareActivity.this, "List od spares created succesfully", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
}
