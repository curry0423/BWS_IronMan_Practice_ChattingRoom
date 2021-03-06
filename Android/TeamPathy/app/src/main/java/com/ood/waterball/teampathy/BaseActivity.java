package com.ood.waterball.teampathy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ood.waterball.teampathy.Controllers.PageController;
import com.ood.waterball.teampathy.Fragments.MemberHomePageFragment;

public class BaseActivity extends AppCompatActivity implements ParentActivityCallBack {
    private PageController pageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onPageControllerInitiate();
    }

    private void onPageControllerInitiate(){
        pageController = new PageController(this,getSupportFragmentManager(),R.id.base_interface_fragment_content);
        pageController.changePage(new MemberHomePageFragment());
    }


    @Override
    public void changePage(Fragment fragment) {
        pageController.changePage(fragment);
    }

}
