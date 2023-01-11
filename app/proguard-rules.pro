 ## Add project specific ProGuard rules here.
## You can control the set of applied configuration files using the
## proguardFiles setting in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#
## Uncomment this to preserve the line number information for
## debugging stack traces.
##-keepattributes SourceFile,LineNumberTable
#
## If you keep the line number information, uncomment this to
## hide the original source file name.
##-renamesourcefileattribute SourceFile
#
#


#-keep class com.gocashfree.cashfreesdk.* { *; }
#-keep interface com.gocashfree.cashfreesdk.* { *; }
#-keep public class com.gocashfree.cashfreesdk.* { *; }
#
#-keep public class com.khiladiadda.wallet.AddWalletActivity { *; }
#
#-keepclassmembers,allowoptimization enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-dontwarn android.support.**
#-dontwarn android.support.design.**
#-keep public class android.support.design.R$* { *; }
#
#-keep class com.google.android.material.* { *; }
#
#-dontwarn com.google.android.material.**
#-dontnote com.google.android.material.**
#
#-dontwarn androidx.**
#-keep class androidx.* { *; }
#-keep interface androidx.* { *; }
#
##ButterKnife
## Retain generated class which implement Unbinder.
#-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
#
## Prevent obfuscation of types which use ButterKnife annotations since the simple name
## is used to reflectively look up the generated ViewBinding.
#-keep class butterknife.*
#-keepclasseswithmembernames class * { @butterknife.* <methods>; }
#-keepclasseswithmembernames class * { @butterknife.* <fields>; }
#
#
#### RxJava, RxAndroid (https://gist.github.com/kosiara/487868792fbd3214f9c9)
#-keep class rx.schedulers.Schedulers {
#    public static <methods>;
#}
#-keep class rx.schedulers.ImmediateScheduler {
#    public <methods>;
#}
#-keep class rx.schedulers.TestScheduler {
#    public <methods>;
#}
#-keep class rx.schedulers.Schedulers {
#    public static ** test();
#}
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#    long producerIndex;
#    long consumerIndex;
#}
#
#-dontwarn sun.misc.Unsafe
#
#-dontwarn org.reactivestreams.FlowAdapters
#-dontwarn org.reactivestreams.**
#-dontwarn java.util.concurrent.flow.**
#-dontwarn java.util.concurrent.**
#
#### Gson uses generic type information stored in a class file when working with fields. Proguard
## removes such information by default, so configure it to keep all of it.
#-keepattributes Signature
#
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#
##-keep class com.google.gson.stream.** { *; }
#
## Application classes that will be serialized/deserialized over Gson
## -keep class com.google.gson.examples.android.model.** { *; }
#
## Prevent proguard from stripping interface information from TypeAdapterFactory,
## JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
#
## Platform calls Class.forName on types which do not exist on Android to determine platform.
#-dontnote retrofit2.Platform
## Platform used when running on RoboVM on iOS. Will not be used at runtime.
#-dontnote retrofit2.Platform$IOS$MainThreadExecutor
## Platform used when running on Java 8 VMs. Will not be used at runtime.
#-dontwarn retrofit2.Platform$Java8
## Retain generic type information for use by reflection by converters and adapters.
#-keepattributes Signature
## Retain declared checked exceptions for use by a Proxy instance.
#-keepattributes Exceptions
#
#-keepclasseswithmembers,allowobfuscation class * {
#    @retrofit2.http.* <methods>;
#}
#
#-keepclassmembers,allowobfuscation class * {
#  @com.google.gson.annotations.SerializedName <fields>;
#}
#
##Paytm
#-keepclassmembers class com.paytm.pgsdk.PaytmWebView$PaytmJavaScriptInterface{
#public *;
#}
#-keep class net.one97.paytm.nativesdk.* { *; }
#-keep interface net.one97.paytm.nativesdk.* { *; }
#
##PayuMoney
#-dontwarn com.mixpanel.**
#-dontwarn org.apache.http.**
#-dontwarn com.android.volley.toolbox.**
#
##Firebase
## This rule will properly ProGuard all the model classes in
## the package com.yourcompany.models. Modify to fit the structure
## of your app.
##-keepclassmembers class com.khiladiadda.network.model.* {
##  *;
##}
#
##Caligraphy
#-keep class uk.co.chrisjenx.calligraphy.* { *; }
#-keep class uk.co.chrisjenx.calligraphy.*$* { *; }
#
##Glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}
#
## Uncomment for DexGuard only
##-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#
#-dontwarn org.xmlpull.v1.XmlPullParser
#-dontwarn org.xmlpull.v1.XmlSerializer
#-keep class org.xmlpull.v1.* {*;}
#
##-keepattributes *Annotation*
#-keepclassmembers class * {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## AVLoadingIndicatorView
#-keep class com.wang.avi.* { *; }
#-keep class com.wang.avi.indicators.* { *; }
#
#### OkHttp3
#-dontwarn okhttp3.**
#-dontwarn okio.**
#-dontwarn javax.annotation.**
## A resource is loaded with a relative path so the package of this class must be preserved.
#-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#
#
#-keep class android.support.v7.widget.RoundRectDrawable { *; }
#
#-keep public class android.support.v7.widget.* { *; }
#-keep public class android.support.v7.internal.widget.* { *; }
#-keep public class android.support.v7.internal.view.menu.* { *; }
#
#-keep public class * extends android.support.v4.view.ActionProvider {
#    public <init>(android.content.Context);
#}
#
#### Support v7, Design
#-keep class android.support.design.* { *; }
#-keep interface android.support.design.* { *; }
#-keep public class android.support.design.R$* { *; }

### RxJava, RxAndroid (https://gist.github.com/kosiara/487868792fbd3214f9c9)
-keep class rx.schedulers.Schedulers {
   public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
   public <methods>;
}
-keep class rx.schedulers.TestScheduler {
   public <methods>;
}
-keep class rx.schedulers.Schedulers {
   public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-dontwarn org.reactivestreams.**
-dontwarn java.util.concurrent.flow.**
-dontwarn java.util.concurrent.**

#### Gson uses generic type information stored in a class file when working with fields. Proguard
## removes such information by default, so configure it to keep all of it.
#-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Application classes that will be serialized/deserialized over Gson
# -keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepattributes Signature
## Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
#
-keepclasseswithmembers,allowobfuscation class * {
   @retrofit2.http.* <methods>;
}
#
-keepclassmembers,allowobfuscation class * {
 @com.google.gson.annotations.SerializedName <fields>;
}

-keepclassmembers class com.payu.checkoutpro.* {*;}
-keepclassmembers class com.payu.base.* {*;}
# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keepattributes *Annotation*
-keepclassmembers class * {
  @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*
-optimizations !method/inlining/*

-keepclassmembers class com.khiladiadda.network.model* {*;}

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models.
# Modify this rule to fit the structure of your app.
-keepclassmembers class com.khiladiadda.network.model* {*;}

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models.
# Modify this rule to fit the structure of your app.
-keepclassmembers class com.khiladiadda.chat.models.* {*;}

# Firebase
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }

-keep class com.khiladiadda.chat.model.ChatMessage { public private *;}

-keepclassmembers class com.easebuzz.payment.kit.**{ *; }