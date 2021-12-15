package com.helloWorldTech.funQuest.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.SpannableString;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.shardPref.PreferencesHelper;
import com.helloWorldTech.funQuest.ui.main.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding2.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class Utils {

    private static String TAG = "Utils";
    private static Dialog dialog1;
    private static Typeface fromAsset;
    private static SpannableString spannableString;
    public static String currentPhotoPath;
    public static Dialog loadingDialog = null;

    public Utils() {


    }

    public static StringBuilder spitTheFirstChar(String str) {
        StringBuilder initials = new StringBuilder();
        for (String s : str.split(" ")) {
            initials.append(s.charAt(0));
        }
        return initials;
    }

    public static void ParseHtml(String data, TextView textView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(data));
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    //For Price DecimalFormat
    public static String format(float value) {
//        double result = Double.valueOf(value);
//        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
//        DecimalFormat df = (DecimalFormat) nf;
//        df.applyPattern(Config.DECIMAL_PLACES_FORMAT);
//        return df.format(result);
        return String.format("%.3f", value);
    }

    public static void showCustomLoadingDialog(Activity activity) {
        if (loadingDialog == null) {
            loadingDialog = new Dialog(activity);
            loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            loadingDialog.setContentView(R.layout.dialog_loading_bar);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadingDialog.show();
        }
    }

    public static void hideCustomLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            Utils.loadingDialog = null;
        }

    }

    public static String getLanguage(PreferencesHelper pref) {
        String lang = "";
        if (pref.getLang().equalsIgnoreCase(Constants.ARABIC_VALUE)) {
            lang = Constants.ARABIC_VALUE;
        } else {
            lang = Constants.ENGLISH_VALUE;
        }
        return lang;
    }

    //todo use other from base activity
    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static Bitmap getScaledBitmap(int maxWidth, Bitmap rotatedBitmap) {
        try {

            int nh = (int) (rotatedBitmap.getHeight() * (512.0 / rotatedBitmap.getWidth()));
            return Bitmap.createScaledBitmap(rotatedBitmap, maxWidth, nh, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Convert image to Base64
    public static String encodeBase64(Bitmap bitmap) {

        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] imageBytes = baos.toByteArray();


        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static void showLocationPermissionSnackbar(Context context, View container) {
        Snackbar snackbar = Snackbar.make(container, "Please Allow access to device", Snackbar.LENGTH_INDEFINITE)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", "com.deliverygate.user", null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.color_search_bg));
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();

    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @return
     * @returns Distance in Meters
     */
    public static double getDistanceOfTwoPoints(double lat1, double lon1, double lat2,
                                                double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /**
     * checkVersion of App
     *
     * @param last_version
     * @return
     */
    public static Boolean checkVersion(String last_version) {

        String versionName = BuildConfig.VERSION_NAME;

        Log.d("----------", Integer.parseInt(versionName.replace(".", "")) + "" + Integer.parseInt(last_version.replace(".", "")));

        return Integer.parseInt(versionName.replace(".", "")) >= Integer.parseInt(last_version.replace(".", ""));
        // return Integer.parseInt(versionName.replace(".","")) < Integer.parseInt(last_version.replace(".",""));

    }

    public static String formatSeconds(int seconds) {
        return getTwoDecimalsValue(seconds / 3600) + ":"
                + getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }

    /**
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppRunning(final Context context, final String packageName) {

        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static String getDate(long time) {

        String date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // modify format
        date = formatter.format(new Date(time));
        return date;

    }

    /**
     * @return
     */
    public static int generateUniqueId() {
        UUID idOne = UUID.randomUUID();
        String str = "" + idOne;
        int uid = str.hashCode();
        String filterStr = "" + uid;
        str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }

    /**
     * logout from app and delete user
     *
     * @param context
     * @param
     * @return
     *//*
    public static Dialog logout(final Context context, final DataManager dataManager) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.exit)
                .setMessage(R.string.exitConfirm)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dataManager.deleteUser();
                        //context.startActivity(LoginActivity.getStartIntent(context));


                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return alertDialog.create();

    }
*/

   /* public static void share(Context context, String productName) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", productName);
            intent.putExtra("android.intent.extra.TEXT", "\n   \n\n" + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
            context.startActivity(Intent.createChooser(intent, ("choose")));
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setCursorVisible(true);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap, int quality) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return Base64.encodeToString(stream.toByteArray(), 2);
    }

    public static boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                if (Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0) {
                    return true;
                }
                return false;
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        } else if (TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"))) {
            return false;
        } else {
            return true;
        }
    }

    public static String getDateTime() {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public static void tintMenuIcon(Context context, MenuItem menuItem, @ColorRes int i) {

        Drawable wrap = DrawableCompat.wrap(menuItem.getIcon());
        DrawableCompat.setTint(wrap, context.getResources().getColor(i));
        menuItem.setIcon(wrap);
    }

    public static void shareMyApp(Context context) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://play.google.com/store/apps/details?id=");
        stringBuilder.append(context.getPackageName());
        String stringBuilder2 = stringBuilder.toString();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/*");
        intent.putExtra("android.intent.extra.TEXT", stringBuilder2);
        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }

    public static void shareAppWithSocial(Context context, String application, String title, String description) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage(application);

        intent.putExtra(android.content.Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, description);
        intent.setType("text/plain");

        try {
            // Start the specific social application
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // The application does not exist
            Toast.makeText(context, "app have not been installed.", Toast.LENGTH_SHORT).show();
        }


    }

    public static String convertUsingStringBuilder(List<Integer> names) {
        StringBuilder namesStr = new StringBuilder();
        for (int name : names) {
            namesStr = namesStr.length() > 0 ? namesStr.append(",").append(name) : namesStr.append(name);
        }
        return namesStr.toString();
    }

    public static ArrayList<String> convertStringToList(String str) {

        ArrayList aList = new ArrayList(Arrays.asList(str.split(",")));

        for (int i = 0; i < aList.size(); i++) {
            System.out.println(" -->" + aList.get(i));
        }

        return aList;
    }

    /**
     * Open a web page of a specified URL
     *
     * @param url URL to open
     */
    public static void openWebPage(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void copyToClipboard(Context context, String text) {

        ClipData myClip;
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        myClip = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(myClip);

        Toast.makeText(context, "تم نسخ رقم المحفظة : " + text, Toast.LENGTH_SHORT).show();

    }

    public static void makeCall(Context context, String mobile) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(intent);

    }

    /**
     * to replace arabic numeric
     *
     * @param original
     * @return
     */
    public static String replaceArabicNumbers(String original) {

        return original.replaceAll("١", "1")
                .replaceAll("٢", "2")
                .replaceAll("٣", "3")
                .replaceAll("٤", "4")
                .replaceAll("٥", "5")
                .replaceAll("٦", "6")
                .replaceAll("٧", "7")
                .replaceAll("٨", "8")
                .replaceAll("٩", "9")
                .replaceAll("٠", "0");
    }

    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setCursorVisible(true);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    //region Log Methods

    /**
     * Log
     *
     * @param log
     */
    public static void log(String log) {
        if (Config.IS_DEVELOPMENT) {
            Log.d("HWDTechs-FunQuest", log);
        }
    }

    public static void errorLog(String log, Object obj) {
        try {
            Log.d("HWDTechs-FunQuest", log);
            Log.d("HWDTechs-FunQuest", "Line : " + getLineNumber());
            Log.d("HWDTechs-FunQuest", "Class : " + getClassName(obj));
        } catch (Exception ee) {
            Log.d("HWDTechs-FunQuest", "Error in psErrorLog");
        }
    }

    public static void errorLog(String log, Exception e) {
        try {
            StackTraceElement l = e.getStackTrace()[0];
            Log.d("HWDTechs-FunQuest", log);
            Log.d("HWDTechs-FunQuest", "Line : " + l.getLineNumber());
            Log.d("HWDTechs-FunQuest", "Method : " + l.getMethodName());
            Log.d("HWDTechs-FunQuest", "Class : " + l.getClassName());
        } catch (Exception ee) {
            Log.d("HWDTechs-FunQuest", "Error in psErrorLogE");
        }

    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[4].getLineNumber();
    }

    public static String getClassName(Object obj) {
        return "" + ((Object) obj).getClass();
    }

    //endregion

    public enum LoadingDirection {
        top,
        bottom,
        none
    }

    public static void closeApplication(Activity ctx) {
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        ctx.startActivity(intent);
    }

    //region Rate & Share
    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static void shareAction(Activity act) {
        try {
            // string to share
            String body = String.format(act.getString(R.string.share_text), " ", act.getString(R.string.app_name));
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, act.getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
            act.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        } catch (Exception e) {
            Toast.makeText(act, "Failed when create share data", Toast.LENGTH_LONG).show();
        }
    }

    public static void shareCode(Activity act, String code) {
        try {
            // string to share
            String body = String.format(act.getString(R.string.share_text), code, act.getString(R.string.app_name));
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, act.getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
            act.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        } catch (Exception e) {
            Toast.makeText(act, "Failed when create share data", Toast.LENGTH_LONG).show();
        }
    }


    //endregion
}

