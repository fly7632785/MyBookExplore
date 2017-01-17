package com.jafir.testclassinvoke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by jafir on 17/1/17.
 */
public class TestImageLoad extends AppCompatActivity {

    private String[] imgs = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484566929477&di=35e6ea372632e560ec97ffe64b9aa6b8&imgtype=0&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F150703%2F14-150F3102113Y7.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484566929477&di=dbf141a72c7b8b673f5560d64cd559b8&imgtype=0&src=http%3A%2F%2Fimg2.3lian.com%2F2014%2Ff2%2F15%2Fd%2F48.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484566929477&di=7bd0705ccf68739b66727717f4395b33&imgtype=0&src=http%3A%2F%2Fpic17.nipic.com%2F20111108%2F3484168_101404023000_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484566929477&di=fa839b0d55a2ff39d20dc2ff2e4be8c9&imgtype=0&src=http%3A%2F%2Fimg15.3lian.com%2F2015%2Fc2%2F95%2Fd%2F34.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484566929477&di=68e8af28d9e819353e538b65b5cdb74a&imgtype=0&src=http%3A%2F%2Fimg10.3lian.com%2Fc1%2Fnewpic%2F12%2F09%2F15.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586249&di=77363d537670543db46ac6648edc1d5f&imgtype=0&src=http%3A%2F%2Fwww.bz55.com%2Fuploads%2Fallimg%2F140519%2F1-140519112157.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586469&di=b5447e7178a62fd4ea1fd76cd9fc0dad&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F8c1001e93901213f6fbede0655e736d12e2e955f.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586469&di=1895d2bdc700a1b01124bde026197431&imgtype=0&src=http%3A%2F%2Fimage67.360doc.com%2FDownloadImg%2F2013%2F06%2F0214%2F37891534_6.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586468&di=c4404e3c4bfbf129cc0c82079bc8440d&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1308%2F15%2Fc5%2F24496183_1376533418348.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586468&di=5441bfc85bbda9c98112678218be89e5&imgtype=0&src=http%3A%2F%2Fimg3.iqilu.com%2Fdata%2Fattachment%2Fforum%2F201308%2F21%2F202713xby7z1yxacyxc1u5.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586467&di=b52622c291aa5454a400dfb483960fa2&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1306%2F03%2Fc0%2F21608389_1370244342425.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586464&di=04091fa1337f11e4811fbdf4f31398bd&imgtype=0&src=http%3A%2F%2Fimage60.360doc.com%2FDownloadImg%2F2013%2F05%2F0112%2F32016275_25.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586464&di=a107c73e2d2c67b562bea8673da41a3c&imgtype=0&src=http%3A%2F%2Fimg3.iqilu.com%2Fdata%2Fattachment%2Fforum%2F201308%2F21%2F201854vh8f1f1ha7ls1vih.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586457&di=e4b43eaf5fd0607a0a1b833642a995d0&imgtype=0&src=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201608%2F201608194.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484628586450&di=4af049ed83548a945ee3d6aeceb1a40d&imgtype=0&src=http%3A%2F%2Fimg15.3lian.com%2F2015%2Fa1%2F15%2Fd%2F33.jpg"

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_img_load);
        init();
    }

    private void init() {
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new MyAdapter());

    }

    class ViewHolder {
        ImageView img;
        TextView text;
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {

            return imgs[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(TestImageLoad.this, R.layout.item_test_img, null);
                holder.img = (ImageView) view.findViewById(R.id.img);
                holder.text = (TextView) view.findViewById(R.id.text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            int density = (int) getResources().getDisplayMetrics().density;
            int width = density * 100;

//            ImageLoader.getInstance(TestImageLoad.this).bindBitmap(imgs[i % imgs.length], holder.img,width,width);
            Glide.with(TestImageLoad.this).load(imgs[i % imgs.length]).into(holder.img);
            holder.text.setText(imgs[i % imgs.length]);
            return view;
        }
    }

}
