package phantomshadow.example.com.kedaikeun.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import phantomshadow.example.com.kedaikeun.R;

public class HelpActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private HelpManager helpManager;
    private  int[] layouts;
    private LinearLayout dotLayout;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;

    Button buttonNext, buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        helpManager = new HelpManager(this);
        if(!helpManager.check()){
            helpManager.setFirst(false);
            Intent intent = new Intent(HelpActivity.this, MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_help);

        viewPager = (ViewPager) findViewById(R.id.vPager);
        dotLayout = (LinearLayout) findViewById(R.id.layoutTitik);
        buttonNext = (Button) findViewById(R.id.btnNext);
        buttonSkip = (Button) findViewById(R.id.btnSkip);
        layouts = new int[]{R.layout.introduce_crud,R.layout.introduce_laporan,R.layout.introduce_transaksi};
        addBottomDots(0);
        changeStatusBar();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+1);
                if(current < layouts.length){
                    viewPager.setCurrentItem(current);
                }
                else {
                    Intent intent = new Intent(HelpActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void addBottomDots(int position){
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInActive = getResources().getIntArray(R.array.dot_inactive);
        dotLayout.removeAllViews();
        for(int i = 0 ; i < dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive[position]);
            dotLayout.addView(dots[i]);

        }

        if(dots.length > 1){
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private  int getItem(int i ){
        return viewPager.getCurrentItem() + 1;
    }
   ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
       @Override
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

       }

       @Override
       public void onPageSelected(int position) {
           addBottomDots(position);
           if (position == layouts.length-1){
               buttonNext.setText("OK");
               buttonSkip.setVisibility(View.GONE);
           }
           else {
               buttonNext.setText("NEXT");
               buttonSkip.setVisibility(View.VISIBLE);
           }
       }

       @Override
       public void onPageScrollStateChanged(int state) {

       }
   };


    private void changeStatusBar(){

        if (Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            container.removeView(view);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HelpActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
