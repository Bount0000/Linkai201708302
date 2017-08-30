package com.bwei.linkai201708302;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
@ViewInject(R.id.xlv) XListView xlv;
    private List<MyBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        list = new ArrayList<MyBean>();
        setImage(); 
        String url="http://v.juhe.cn/toutiao/index";
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("key","22a108244dbb8d1f49967cd74a0c144d");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                 Gson gson=new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                Bean.ResultBean result1 = bean.getResult();
                List<Bean.ResultBean.DataBean> data = result1.getData();
                for (int i = 0; i <data.size() ; i++) {
                    Bean.ResultBean.DataBean dataBean = data.get(i);
                    String title = dataBean.getTitle();
                    String pic = dataBean.getThumbnail_pic_s();
                    list.add(new MyBean(title,pic));
                }
                setDate();
            }

            private void setDate() {
                MyAdapter adapter=new MyAdapter(MainActivity.this,list);
                xlv.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void setImage() {
        DisplayImageOptions option=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(option)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
