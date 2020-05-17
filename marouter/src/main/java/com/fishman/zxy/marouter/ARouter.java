package com.fishman.zxy.marouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class ARouter {
    private String packagename="com.fishman.zxy.util";
    private static ARouter aRouter;
    private Map<String,Class<? extends Activity>> maps;
    private Context context;
    private ARouter(){
        maps=new HashMap<>();
    }
    public static ARouter getInstance(){
        if(aRouter==null){
            aRouter=new ARouter();
        }
        return aRouter;
    }
    public void init(Context context){
        this.context=context;
        List<String> classNames=getClassName(packagename);
        for (String className:classNames) {
            try {
                Class<?>aClass=Class.forName(className);
                //判断这个类是不是IRout 子类
                if(IRout.class.isAssignableFrom(aClass)){
                    //通过接口获取实例并调用方法
                    IRout iRout= (IRout) aClass.newInstance();
                    iRout.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getClassName(String packagename) {
         List<String> calssList=new ArrayList<>();
         String path=null;
        try {
            //根据包管理器获取应用信息，获取apk的完整路径
            path=context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
            //根据apk路径获取apk的dex文件
            DexFile dexFile=new DexFile(path);
            //获取dex文件中的所有class
            Enumeration enumeration=dexFile.entries();
            while (enumeration.hasMoreElements()){
                String name= (String) enumeration.nextElement();
                //判断只要包名包含了我们的工具包就把所有的类全部添加
                if(name.contains(packagename)){
                    calssList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calssList;
    }

    /**
     * 添加activity到容器
     * @param key
     * @param activity
     */
    public void AddActivity(String key,  Class<? extends Activity> activity){
      if(key!=null&&activity!=null&&!maps.containsKey(key)){
          maps.put(key,activity);
      }
    }
    /**
     * 跳转方法
     * @param key
     * @param bundle
     */
    public void GoToActivity(String key, Bundle bundle){
        Class<? extends Activity> ActyaClass = maps.get(key);
        if(ActyaClass!=null){
            Intent intent=new Intent();
            if(bundle!=null){
                intent.putExtras(bundle);
            }
            intent.setClass(context,ActyaClass);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent);
        }
    }
}
