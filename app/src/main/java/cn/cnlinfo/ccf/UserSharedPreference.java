package cn.cnlinfo.ccf;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;

import cn.cnlinfo.ccf.entity.AccountInfo;
import cn.cnlinfo.ccf.entity.User;
import cn.cnlinfo.ccf.manager.ACache;

/**
 * Created by cj on 2016/9/8.
 * <p>
 * 存储登录信息以及版本信息
 */
public class UserSharedPreference {

    private static UserSharedPreference instance;

    private Context mContext;
    private ACache mACache;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String SHARED_PREFERENCE_NAME = "USER";

    private static final int CACHE_SECONDS = 60 * 60;
    private static final String CACHE_JWT_TOKEN_KEY = "jwt_token";
    private static final String CACHE_LATEST_VERSION_CODE_KEY = "latest_version_code";
    private static final String CACHE_LATEST_VERSION_NAME_KEY = "latest_version_name";
    private static final String USER_INFO_KEY = "userinfo";
    private static final String ACCOUNT_INFO_KEY = "accountinfo";
    private static final String CACHE_PHONE_PASSWORD_KEY = "phone_password";
    private static final String CACHE_PASSWORD_KEY = "password";

    public UserSharedPreference(Context context) {
        this.mContext = context;
        this.mACache = ACache.get(mContext);
        this.mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.mEditor = mSharedPreferences.edit();
    }

    public static synchronized UserSharedPreference getInstance() {
        if (instance == null) {
            instance = new UserSharedPreference(CCFApplication.getContext());
        }
        return instance;
    }

    public User getUser(){
        return JSONObject.parseObject(UserSharedPreference.getInstance().getUserInfo(),User.class);
    }

    public AccountInfo getAccount(){
       return  JSONObject.parseObject(UserSharedPreference.getInstance().getAccountInfo(),AccountInfo.class);
    }

    private String getAccountInfo(){
        String jsonObjectFormCache = this.getAccountInfoFormCache();
        String jsonObjectFormSharedPreferences = this.getAccountInfoFormSharedpreferences();
        return null == jsonObjectFormCache ? jsonObjectFormSharedPreferences : jsonObjectFormCache;
    }


    public void setAccountInfo(String accountInfo){
        this.setAccountInfoToSharedPreferences(accountInfo);
        this.setAccountInfoToCache(accountInfo);
    }

    private void setAccountInfoToSharedPreferences(String accountInfoToSharedPreferences){
        mEditor.putString(ACCOUNT_INFO_KEY,accountInfoToSharedPreferences);
        mEditor.commit();
    }

    private void setAccountInfoToCache(String accountInfoToCache){
        mACache.put(ACCOUNT_INFO_KEY,accountInfoToCache);
    }

    /**
     * 获取不同用户在本地缓存的步数
     * @param userCode
     * @return
     */
    public int getStep(String userCode){
        int step = 0;
         if (mACache.getAsObject(userCode)==null){
            if (mSharedPreferences.contains(userCode)){
                step = mSharedPreferences.getInt(userCode,0);
                if (step!=0){
                    setStepToCache(userCode,step);
                }
            }
         }else {
             step = (int)mACache.getAsObject(userCode);
         }
         return step;
    }


    //存储不同用户认证后每天的剩余步数,
    public void setStep(String userCode,int step){
        this.setStepToSharedPreferences(userCode,step);
        this.setStepToCache(userCode,step);
    }

    private void setStepToSharedPreferences(String userCode,int step){
        mEditor.putInt(userCode,step);
        mEditor.commit();
    }
    private void setStepToCache(String userCode,int step){
        mACache.put(userCode,step);
    }

    //存储用户信息
    public void setUserInfo(String userinfo) {
        this.setUserInfoToSharedPreferences(userinfo);
        this.setUserInfoToCache(userinfo);
    }

    private void setUserInfoToSharedPreferences(String userInfoToSharedPreferences) {
        mEditor.putString(USER_INFO_KEY, userInfoToSharedPreferences);
        mEditor.commit();
    }

    private void setUserInfoToCache(String userInfoToCache) {
        mACache.put(USER_INFO_KEY, userInfoToCache);
    }

    public String getUserInfo() {
        String jsonObjectFormCache = this.getUserInfoFormCache();
        String jsonObjectFormSharedPreferences = this.getUserInfoFormSharedpreferences();
        return null == jsonObjectFormCache ? jsonObjectFormSharedPreferences : jsonObjectFormCache;
    }


    private String getUserInfoFormSharedpreferences() {
        return mSharedPreferences.getString(USER_INFO_KEY, null);
    }

    private String getUserInfoFormCache() {
        return mACache.getAsString(USER_INFO_KEY);
    }

    private String getAccountInfoFormCache(){
        return mACache.getAsString(ACCOUNT_INFO_KEY);
    }
    private String getAccountInfoFormSharedpreferences(){
        return mSharedPreferences.getString(ACCOUNT_INFO_KEY,null);
    }


    public void setIsFirstLogin(boolean flag) {
        mEditor.putBoolean("isFirstLogin", flag);
        mEditor.commit();
    }

    public boolean getIsFirstLogin() {
        return mSharedPreferences.getBoolean("isFirstLogin", false);
    }

    /**
     * 是否已经登录
     *
     * @return boolean
     */
    public boolean hasLogined() {
        return null != this.getPhoneAndPassword() || null != this.getJwtToken();
    }


    /**
     * 退出登录
     */
    public void logout() {
        this.removeJwtToken();
        this.removePhoneAndPassword();
        this.removeUser();
    }

    /**
     * 设置用户的jwt token
     *
     * @param token
     */
    public void setJwtToken(String token) {
        this.putJwtTokenToCache(token);

        this.putJwtTokenToSharedPreference(token);
    }

    private void putJwtTokenToCache(String token) {
        mACache.put(CACHE_JWT_TOKEN_KEY, token, CACHE_SECONDS);
    }

    private void putJwtTokenToSharedPreference(String token) {
        mEditor.putString(CACHE_JWT_TOKEN_KEY, token);
        mEditor.commit();
    }

    /**
     * 获取用户的jwt token
     */
    public String getJwtToken() {
        String token = this.getJwtTokenFromCache();

        if (null == token) {
            token = this.getJwtTokenFromSharedPreference();

            if (null != token) {
                this.putJwtTokenToCache(token);
            }
        }
        return token;
    }

    private String getJwtTokenFromCache() {
        return mACache.getAsString(CACHE_JWT_TOKEN_KEY);
    }
    private String getJwtTokenFromSharedPreference() {
        if (!mSharedPreferences.contains(CACHE_JWT_TOKEN_KEY)) {
            return null;
        }
        return mSharedPreferences.getString(CACHE_JWT_TOKEN_KEY, null);
    }

    /**
     * 清除jwt token
     */
    public void removeJwtToken() {
        this.removeJwtTokenInSharedPreference();

        this.removeJwtTokenInCache();
    }

    private void removeJwtTokenInCache() {
        mACache.remove(CACHE_JWT_TOKEN_KEY);
        mACache.clear();
    }

    private void removeJwtTokenInSharedPreference() {
        mEditor.remove(CACHE_JWT_TOKEN_KEY);
        mEditor.commit();
    }


    /**
     * 清除用户资料
     */
    public void removeUser() {
        this.removeUserInSharedPreference();

        this.removeUserInCache();
    }

    private void removeUserInCache() {
        mACache.remove(USER_INFO_KEY);
    }

    private void removeUserInSharedPreference() {
        mEditor.remove(USER_INFO_KEY);
        mEditor.commit();
    }

    /**
     * 设置app最后的版本号
     *
     * @param versionCode
     */
    public void setLatestVersionCode(int versionCode) {
        this.putLatestVersionCodeToCache(versionCode);

        this.putLatestVersionCodeToSharedPreference(versionCode);
    }

    private void putLatestVersionCodeToCache(int versionCode) {
        mACache.put(CACHE_LATEST_VERSION_CODE_KEY, Integer.valueOf(versionCode), CACHE_SECONDS);
    }

    private void putLatestVersionCodeToSharedPreference(int versionCode) {
        mEditor.putInt(CACHE_LATEST_VERSION_CODE_KEY, versionCode);
        mEditor.commit();
    }

    /**
     * valid that whether given version is futher
     * than latest version code in cache
     *
     * @return boolean
     */
    public boolean isNewVersionCode(int versionCode) {
        return versionCode > this.getLatestVersionCode();
    }

    public boolean isNewVersionName(String versionName){
        return versionName.equals(getLatestVersionName());
    }

    /**
     * 获取最后记录的app版本名
     * <p>
     * return string null if no latest version code
     */
    public String getLatestVersionName() {
        String versionName = this.getLatestVersionNameFromCache();

        if (versionName==null) {
            versionName = this.getLatestVersionNameFromSharedPreference();

            if (versionName != null) {
                this.setLatestVersionName(versionName);
            }
        }

        return versionName;
    }

    public void setLatestVersionName(String versionName){
        this.putLatestVersionNameToCache(versionName);
        this.putLatestVersionNameToSharedPreference(versionName);
    }
    private void putLatestVersionNameToCache(String versionName) {
        mACache.put(CACHE_LATEST_VERSION_NAME_KEY, Integer.valueOf(versionName), CACHE_SECONDS);
    }

    private void putLatestVersionNameToSharedPreference(String versionName) {
        mEditor.putString(CACHE_LATEST_VERSION_NAME_KEY, versionName);
        mEditor.commit();
    }


    /**
     * 获取最后记录的app版本号
     * <p>
     * return int   -1 if no latest version code
     */
    public int getLatestVersionCode() {
        int versionCode = this.getLatestVersionCodeFromCache();

        if (versionCode < 0) {
            versionCode = this.getLatestVersionCodeFromSharedPreference();

            if (versionCode > 0) {
                this.setLatestVersionCode(versionCode);
            }
        }

        return versionCode;
    }

    private int getLatestVersionCodeFromCache() {
        Object object = mACache.getAsObject(CACHE_LATEST_VERSION_CODE_KEY);
        if (null == object) {
            return -1;
        }
        return ((Integer) object).intValue();
    }

    private int getLatestVersionCodeFromSharedPreference() {
        if (!mSharedPreferences.contains(CACHE_LATEST_VERSION_CODE_KEY)) {
            return -1;
        }
        return mSharedPreferences.getInt(CACHE_LATEST_VERSION_CODE_KEY, -1);
    }

    private String getLatestVersionNameFromCache() {
        String object = mACache.getAsString(CACHE_LATEST_VERSION_NAME_KEY);
        if (null == object) {
            return null;
        }
        return  object;
    }

    private String getLatestVersionNameFromSharedPreference() {
        if (!mSharedPreferences.contains(CACHE_LATEST_VERSION_NAME_KEY)) {
            return null;
        }
        return mSharedPreferences.getString(CACHE_LATEST_VERSION_NAME_KEY, null);
    }

    /**
     * 清除latest version code(清除app数据时使用)
     */
    public void removeLatestVersionCode() {
        this.removeLatestVersionCodeInSharedPreference();

        this.removeLatestVersionCodeInCache();
    }

    private void removeLatestVersionCodeInCache() {
        mACache.remove(CACHE_LATEST_VERSION_CODE_KEY);
    }

    private void removeLatestVersionCodeInSharedPreference() {
        mEditor.remove(CACHE_LATEST_VERSION_CODE_KEY);
        mEditor.commit();
    }

    /**********************************************账号密码********************************************************************/
    /**
     * 存储用户账号密码
     *
     * @param phoneNum
     * @param password
     */
    public void setPhoneAndPassword(String phoneNum, String password) {
        this.putPhoneAndPasswordToCache(phoneNum, password);
        this.putPhoneAndPasswordToSharedPreference(phoneNum, password);
    }

    private void putPhoneAndPasswordToCache(String phoneNum, String password) {
        mACache.put(CACHE_PHONE_PASSWORD_KEY, phoneNum + "/" + password, CACHE_SECONDS);
    }

    private void putPhoneAndPasswordToSharedPreference(String phoneNum, String password) {
        mEditor.putString(CACHE_PHONE_PASSWORD_KEY, phoneNum + "/" + password);
        mEditor.commit();
    }


    /**
     * 获取账号密码
     */
    public String getPhoneAndPassword() {
        String phoneAndPassword = this.getPhoneAndPasswordFromCache();

        if (null == phoneAndPassword) {
            phoneAndPassword = this.getPhoneAndPasswordFromSharedPreference();

            if (null != phoneAndPassword) {
                this.putPhoneAndPasswordToCache(phoneAndPassword.substring(0,phoneAndPassword.lastIndexOf('/')-1), phoneAndPassword.substring(phoneAndPassword.lastIndexOf('/')+1));
            }
        }
        return phoneAndPassword;
    }

    private String getPhoneAndPasswordFromCache() {
        return mACache.getAsString(CACHE_PHONE_PASSWORD_KEY);
    }

    private String getPhoneAndPasswordFromSharedPreference() {
        if (!mSharedPreferences.contains(CACHE_PHONE_PASSWORD_KEY)) {
            return null;
        }
        return mSharedPreferences.getString(CACHE_PHONE_PASSWORD_KEY, null);
    }

    /**
     * 清除账号密码
     */
    public void removePhoneAndPassword() {
        this.removePhoneAndPasswordInSharedPreference();

        this.removePhoneAndPasswordInCache();
    }

    private void removePhoneAndPasswordInCache() {
        mACache.remove(CACHE_PHONE_PASSWORD_KEY);
    }

    private void removePhoneAndPasswordInSharedPreference() {
        mEditor.remove(CACHE_PHONE_PASSWORD_KEY);
        mEditor.commit();
    }
}

