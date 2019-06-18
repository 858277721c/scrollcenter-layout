package com.sd.lib.scrollcenter_layout.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.lib.sclayout.FScrollCenterLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private FScrollCenterLayout view_center_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_center_layout = findViewById(R.id.view_center_layout);
    }

    @Override
    public void onClick(View v)
    {
        // 移动v到中心
        view_center_layout.focusTo(v);
    }
}
