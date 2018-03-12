package cn.cnlinfo.ccf.province_city_zone.data;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cn.cnlinfo.ccf.province_city_zone.entity.Province;
import cn.cnlinfo.ccf.province_city_zone.util.XmlParserHandler;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public class ChooseArea {

    //获取省的数据列表
    public static List getDatas(Context context) {
        List <Province>list = null;
        AssetManager asset = context.getAssets();
            try {
                InputStream input = asset.open("Provinces_Cities_Districts.xml");
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser parser = spf.newSAXParser();
                XmlParserHandler handler = new XmlParserHandler();
                parser.parse(input, handler);
                input.close();
                list = handler.getProvinceDataList();
            }catch (Exception e){
                e.printStackTrace();
            }
        return list;
    }

}
