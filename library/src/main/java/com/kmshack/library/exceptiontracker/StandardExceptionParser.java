package com.kmshack.library.exceptiontracker;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by kmshack on 16. 4. 14..
 */
public class StandardExceptionParser {
    private final TreeSet<String> zzLM = new TreeSet();

    public StandardExceptionParser(Context context, Collection<String> additionalPackages) {
        this.setIncludedPackages(context, additionalPackages);
    }

    public void setIncludedPackages(Context context, Collection<String> additionalPackages) {
        this.zzLM.clear();
        HashSet var3 = new HashSet();
        if(additionalPackages != null) {
            var3.addAll(additionalPackages);
        }

        if(context != null) {
            try {
                String var4 = context.getApplicationContext().getPackageName();
                this.zzLM.add(var4);
                PackageInfo var5 = context.getApplicationContext().getPackageManager().getPackageInfo(var4, PackageManager.GET_ACTIVITIES);
                ActivityInfo[] var6 = var5.activities;
                if(var6 != null) {
                    ActivityInfo[] var7 = var6;
                    int var8 = var6.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        ActivityInfo var10 = var7[var9];
                        var3.add(var10.packageName);
                    }
                }
            } catch (PackageManager.NameNotFoundException var11) {

            }
        }

        Iterator var12 = var3.iterator();

        while(var12.hasNext()) {
            String var13 = (String)var12.next();
            boolean var14 = true;

            for(Iterator var15 = this.zzLM.iterator(); var15.hasNext(); var14 = false) {
                String var16 = (String)var15.next();
                if(!var13.startsWith(var16)) {
                    if(var16.startsWith(var13)) {
                        this.zzLM.remove(var16);
                    }
                    break;
                }
            }

            if(var14) {
                this.zzLM.add(var13);
            }
        }

    }

    protected Throwable getCause(Throwable t) {
        Throwable var2;
        for(var2 = t; var2.getCause() != null; var2 = var2.getCause()) {
            ;
        }

        return var2;
    }

    protected StackTraceElement getBestStackTraceElement(Throwable t) {
        StackTraceElement[] var2 = t.getStackTrace();
        if(var2 != null && var2.length != 0) {
            StackTraceElement[] var3 = var2;
            int var4 = var2.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                StackTraceElement var6 = var3[var5];
                String var7 = var6.getClassName();
                Iterator var8 = this.zzLM.iterator();

                while(var8.hasNext()) {
                    String var9 = (String)var8.next();
                    if(var7.startsWith(var9)) {
                        return var6;
                    }
                }
            }

            return var2[0];
        } else {
            return null;
        }
    }

    protected String getDescription(Throwable cause, StackTraceElement element, String threadName) {
        StringBuilder var4 = new StringBuilder();
        var4.append(cause.getClass().getSimpleName());
        if(element != null) {
            String[] var5 = element.getClassName().split("\\.");
            String var6 = "unknown";
            if(var5 != null && var5.length > 0) {
                var6 = var5[var5.length - 1];
            }

            var4.append(String.format(" (@%s:%s:%s)", new Object[]{var6, element.getMethodName(), Integer.valueOf(element.getLineNumber())}));
        }

        if(threadName != null) {
            var4.append(String.format(" {%s}", new Object[]{threadName}));
        }

        return var4.toString();
    }

    public String getDescription(String threadName, Throwable t) {
        return this.getDescription(this.getCause(t), this.getBestStackTraceElement(this.getCause(t)), threadName);
    }
}
