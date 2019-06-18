# Gradle
[![](https://jitpack.io/v/zj565061763/scrollcenter-layout.svg)](https://jitpack.io/#zj565061763/scrollcenter-layout)

# Demo

```java
// 移动v到中心
mScrollCenterLayout.focusTo(v);
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    android:padding="10dp">

    <com.sd.lib.sclayout.FScrollCenterLayout
        android:id="@+id/view_center_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content">

            <TextView
                android:id="@+id/tv"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:onClick="onClick"
                android:text="动图"
                android:textColor="@color/colorAccent" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="10dp"
                android:onClick="onClick"
                android:text="拍摄"
                android:textColor="@color/colorAccent" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:onClick="onClick"
                android:text="相册"
                android:textColor="@color/colorAccent" />

        </LinearLayout>

    </com.sd.lib.sclayout.FScrollCenterLayout>

    <View
        android:layout_width="20dp"
        android:layout_height="2dp"
        android:layout_marginTop="5dp"
        android:background="@color/colorAccent" />

</LinearLayout>
```